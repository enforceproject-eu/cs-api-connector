package org.n52.project.enforce.db.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.n52.project.enforce.db.model.Data;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DataRepositoryTest extends RepositoryTest {

    @Autowired
    private DataRepository dataRepository;    

    GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
    
    Random random = new Random();

    DecimalFormat df = new DecimalFormat("#.000000000");
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    @Test
    void testFindByServiceRequestId() {
        int userId = 44;
        Data data = new Data(UUID.randomUUID());
        data.setUserId(userId);
        dataRepository.save(data);
        List<Data> dataFromDb = dataRepository.findAll();
        assertEquals(dataFromDb.get(0).getUserId(), userId);
    }

    @Test
    void testGetGeoJson() {
        Data data1 = createRandomData();
        Data data2 = createRandomData();
        Data data3 = createRandomData();
        dataRepository.save(data1);
        dataRepository.save(data2);
        dataRepository.saveAndFlush(data3);
        String geoJson = dataRepository.getGeoJson();
        assertNotNull(geoJson);
        try {
            JsonNode geojsonNode = objectMapper.readTree(geoJson);
            assertTrue(geojsonNode instanceof ObjectNode);
            ObjectNode geojsonObjectNode = (ObjectNode)geojsonNode;
            assertEquals(geojsonObjectNode.get("type").asText(), "FeatureCollection");
            JsonNode featuresNode = geojsonObjectNode.get("features");
            assertTrue(featuresNode instanceof ArrayNode);
            ArrayNode featuresArrayNode = (ArrayNode)featuresNode;
            assertEquals(featuresArrayNode.size(), 3);
            for (int i = 0; i < featuresArrayNode.size(); i++) {
                JsonNode featureNode = featuresArrayNode.get(i);
                assertTrue(featureNode instanceof ObjectNode);
                ObjectNode featureObjectNode = (ObjectNode)featureNode;
                String id = featureObjectNode.get("id").asText();
                String data1Id = data1.getId().toString();
                if(id.equals(data1Id)) {
                    JsonNode geometryNode = featureObjectNode.get("geometry");
                    assertTrue(geometryNode instanceof ObjectNode);
                    ObjectNode geometryObjectNode = (ObjectNode) geometryNode;
                    JsonNode coordinatesNode = geometryObjectNode.get("coordinates");
                    assertTrue(coordinatesNode instanceof ArrayNode);
                    ArrayNode coordinatesArrayNode = (ArrayNode) coordinatesNode;                    
                    assertEquals(df.format(coordinatesArrayNode.get(0).asDouble()), df.format(data1.getLocation().getCoordinate().x));
                    break;
                }
            }
            
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
        assertTrue(geoJson.contains(data1.getId().toString()));
        assertTrue(geoJson.contains(data2.getId().toString()));
        assertTrue(geoJson.contains(data3.getId().toString()));
    }
    
    private Data createRandomData() {
        int userId = random.nextInt(100);
        Data data = new Data(UUID.randomUUID());
        data.setUserId(userId);
        double x = random.nextDouble(50d);
        double y = random.nextDouble(8d);
        data.setLocation(factory.createPoint(new Coordinate(x, y)));
        return data;
    }

}
