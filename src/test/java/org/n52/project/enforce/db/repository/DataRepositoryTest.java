package org.n52.project.enforce.db.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.n52.project.enforce.db.model.Data;
import org.springframework.beans.factory.annotation.Autowired;

public class DataRepositoryTest extends RepositoryTest {

    @Autowired
    private DataRepository dataRepository;    

    GeometryFactory factory = new GeometryFactory();
    
    Random random = new Random();

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
        assertTrue(geoJson.contains(data1.getId().toString()));
        assertTrue(geoJson.contains(data2.getId().toString()));
        assertTrue(geoJson.contains(data3.getId().toString()));
        assertTrue(geoJson.contains(""+ data3.getLocation().getCoordinate().x));
    }
    
    private Data createRandomData() {
        int userId = random.nextInt(100);
        Data data = new Data(UUID.randomUUID());
        data.setUserId(userId);
        data.setLocation(factory.createPoint(new Coordinate(random.nextDouble(50.0), random.nextDouble(8.0))));
        return data;
    }

}
