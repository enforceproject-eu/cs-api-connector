package org.n52.project.enforce.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
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

	private GeometryFactory geometryFactory = new GeometryFactory();

	private static Logger LOG = LoggerFactory.getLogger(Utils.class);
	
	public Utils(DataRepository dataRepository) {
		this.dataRepository = dataRepository;
	}

	public boolean ckeckIdIsInDb(UUID id) {
		Optional<Data> possibleExistingDbData = dataRepository.findById(id);
		if(possibleExistingDbData.isPresent()) {
			return true;
		}
		return false;
	}

	public void createNewData(JsonNode jsonNode) {
		JsonNode time = jsonNode.path("time_observed_at");
		JsonNode locationNode = jsonNode.path("location");
		JsonNode speciesGuess = jsonNode.path("species_guess");
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
		dbData.setObservedDatetime(OffsetDateTime.parse(time.asText()));
		dataRepository.saveAndFlush(dbData);
	}
	
	public UUID getId(JsonNode node) {
		JsonNode uuid = node.path("uuid");
		return UUID.fromString(uuid.asText());
	}

}
