package org.n52.project.enforce.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.n52.project.enforce.cs4.playas.db.model.CS4PlayasData;
import org.n52.project.enforce.cs4.playas.db.model.CS4PlayasObservedProperty;
import org.n52.project.enforce.cs4.playas.db.repository.CS4PlayasDataRepository;
import org.n52.project.enforce.cs4.playas.db.repository.CS4PlayasObservedPropertyRepository;
import org.n52.project.enforce.db.model.Data;
import org.n52.project.enforce.db.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class Utils {

    private DataRepository dataRepository;

    private CS4PlayasDataRepository cs4PlayasDataRepository;

    private CS4PlayasObservedPropertyRepository cs4PlayasobservedPropertyRepository;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");

    GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    private static Logger LOG = LoggerFactory.getLogger(Utils.class);

    public Utils(DataRepository dataRepository, CS4PlayasDataRepository cs4PlayasDataRepository,
            CS4PlayasObservedPropertyRepository cs4PlayasobservedPropertyRepository) {
        this.dataRepository = dataRepository;
        this.cs4PlayasDataRepository = cs4PlayasDataRepository;
        this.cs4PlayasobservedPropertyRepository = cs4PlayasobservedPropertyRepository;
    }

    public boolean ckeckIdIsInDb(UUID id) {
        Optional<Data> possibleExistingDbData = dataRepository.findById(id);
        if (possibleExistingDbData.isPresent()) {
            return true;
        }
        return false;
    }

    public void createNewData(JsonNode jsonNode) {
        JsonNode time = jsonNode.path("time_observed_at");
        JsonNode locationNode = jsonNode.path("location");
        JsonNode speciesGuess = jsonNode.path("species_guess");
        JsonNode updatedAt = jsonNode.path("updated_at");
        JsonNode userId = jsonNode.path("user").path("id");
        ArrayNode identifications = (ArrayNode) jsonNode.path("identifications");
        JsonNode photoUrl = identifications.get(0).path("taxon").path("default_photo").path("url");
        String[] locationStringArray = locationNode.asText().split(",");
        double lat = Double.parseDouble(locationStringArray[0]);
        double lon = Double.parseDouble(locationStringArray[1]);
        Point location = geometryFactory.createPoint(new Coordinate(lon, lat));
        Data dbData = new Data(getId(jsonNode));
        dbData.setUserId(userId.asInt());
        try {
            dbData.setMediaUrl(new URL(photoUrl.asText()));
        } catch (MalformedURLException e) {
            LOG.error(e.getMessage());
        }
        dbData.setLocation(location);
        dbData.setSpeciesName(speciesGuess.asText());
        try {
            dbData.setObservedDatetime(OffsetDateTime.parse(time.asText()));
            dbData.setUpdatedDatetime(OffsetDateTime.parse(updatedAt.asText()));
        } catch (Exception e) {
            LOG.info("Error while parsing dates.", e.getMessage());
        }
        dataRepository.saveAndFlush(dbData);
    }

    public UUID getId(JsonNode node) {
        JsonNode uuid = node.path("uuid");
        return UUID.fromString(uuid.asText());
    }

    public void updateData(JsonNode jsonNode) {
        JsonNode updatedAt = jsonNode.path("updated_at");
        OffsetDateTime newUpdatedAt = OffsetDateTime.parse(updatedAt.asText());
        UUID id = getId(jsonNode);
        Optional<Data> dbDataOpt = dataRepository.findById(id);
        if (dbDataOpt.isPresent()) {
            Data dbData = dbDataOpt.get();
            OffsetDateTime dbUpdatedAt = dbData.getUpdatedDatetime();
            if (newUpdatedAt.isAfter(dbUpdatedAt)) {
                dbData.setUpdatedDatetime(newUpdatedAt);
                dataRepository.save(dbData);
                LOG.info(String.format("Data was with id %s was updated.", id.toString()));
            }
        }
    }

    public void readExcelFile(String excelFileAsString) throws IOException {
        readExcelFile(new ByteArrayInputStream(excelFileAsString.getBytes()));
    }
    
    public void readExcelFile(InputStream inputstream) throws IOException {
        File tmpExcelFile = File.createTempFile("excel", ".xlsx");
        IOUtils.copy(inputstream, new FileOutputStream(tmpExcelFile));
        Workbook workbook = new XSSFWorkbook(new FileInputStream(tmpExcelFile));
        Sheet sheet = workbook.getSheetAt(0);
        Row firstRow = sheet.getRow(0);
        short columnCount = firstRow.getLastCellNum();
        Map<Integer, String> propertyTypeMap = new HashMap<>();
        for (int k = 15; k < 92; k++) {
            Row row = sheet.getRow(k);
            Cell cell = row.getCell(1);
            switch (cell.getStringCellValue()) {
            case "Plastic", "Paper / Cardboard", "Wood (machined)", "Metal", "Glass", "Sanitary Waste", "Medical Waste", "Others":
                break;
            default:
                String cellValue = cell.getStringCellValue();
                propertyTypeMap.put(k, cellValue);
                Optional<CS4PlayasObservedProperty> potentialObservedPeoperty =
                        cs4PlayasobservedPropertyRepository.findById(k);
                if (potentialObservedPeoperty.isPresent()) {
                    break;
                }
                CS4PlayasObservedProperty observedProperty = new CS4PlayasObservedProperty(k, cellValue);
                cs4PlayasobservedPropertyRepository.saveAndFlush(observedProperty);
            }
        }
        CS4PlayasData data = null;
        Row row = null;
        Cell cell = null;
        for (int j = 2; j < columnCount; j++) {
            data = new CS4PlayasData();
            for (int k = 0; k < 15; k++) {
                row = sheet.getRow(k);
                cell = row.getCell(j);
                switch (k) {
                case 0:
                    data.setLocationName(cell.getStringCellValue());
                    break;
                case 1:
                    data.setId((int) cell.getNumericCellValue());
                    break;
                case 2:
                    data.setEntity(cell.getStringCellValue());
                    break;
                case 3:
                    try {
                        data.setCleaningDate(dateFormat.parse(cell.getStringCellValue()));
                    } catch (ParseException e) {
                        LOG.error(e.getMessage());
                    }
                    break;
                case 4:
                    data.setCountry(cell.getStringCellValue());
                    break;
                case 5:
                    data.setCommunity(cell.getStringCellValue());
                    break;
                case 6:
                    data.setProvince(cell.getStringCellValue());
                    break;
                case 7:
                    data.setMunicipality(cell.getStringCellValue());
                    break;
                case 8:
                    data.setCollectionType(cell.getStringCellValue());
                    break;
                case 9:
                    data.setProject(cell.getStringCellValue());
                    break;
                case 10:
                    data.setMonitoringLengthMeters((int) cell.getNumericCellValue());
                    break;
                case 11:
                    data.setInitialCoordinates(this.createPoint(cell.getStringCellValue()));
                    break;
                case 12:
                    data.setFinalCoordinates(this.createPoint(cell.getStringCellValue()));
                    break;
                case 13:
                    data.setTotalItems((int) cell.getNumericCellValue());
                    break;
                default:
                    break;
                }
            }
            String propertyName = null;
            for (int k = 15; k < 91; k++) {
                row = sheet.getRow(k);
                cell = row.getCell(j);
                propertyName = propertyTypeMap.get(k);
                if (propertyName == null) {
                    continue;
                }

                Optional<CS4PlayasObservedProperty> potentialObservedPeoperty =
                        cs4PlayasobservedPropertyRepository.findById(k);
                data.putObservedPropertyCount(potentialObservedPeoperty.get(), (int)cell.getNumericCellValue());
            }
            row = sheet.getRow(91);
            cell = row.getCell(j);
            data.setRemarks(cell.getStringCellValue());
            cs4PlayasDataRepository.saveAndFlush(data);
            LOG.info(String.format("Data with id %d added to cs4 data.", data.getId()));
        }
        workbook.close();
    }

    private Point createPoint(String pointAsString) {
        if (pointAsString != null) {
            String[] coordinateArray = pointAsString.split(",");
            if (coordinateArray.length == 2) {
                String latStrg = coordinateArray[1];
                String lngStrg = coordinateArray[0];
                if ((latStrg != null && !latStrg.isEmpty()) && (lngStrg != null && !lngStrg.isEmpty())) {
                    double lat = Double.parseDouble(latStrg);
                    double lng = Double.parseDouble(lngStrg);
                    return geometryFactory.createPoint(new Coordinate(lat, lng));
                }
            }
        }
        return geometryFactory.createPoint(new Coordinate(0, 0));
    }

}
