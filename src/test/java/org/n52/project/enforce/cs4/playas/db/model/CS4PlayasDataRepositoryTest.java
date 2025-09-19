package org.n52.project.enforce.cs4.playas.db.model;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.n52.project.enforce.cs4.playas.db.repository.CS4PlayasDataRepository;
import org.n52.project.enforce.cs4.playas.db.repository.CS4PlayasObservedPropertyRepository;
import org.n52.project.enforce.db.repository.RepositoryTest;
import org.n52.project.enforce.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

public class CS4PlayasDataRepositoryTest extends RepositoryTest {

    @Autowired
    CS4PlayasDataRepository cs4PlayasDataRepository;

    @Autowired
    CS4PlayasObservedPropertyRepository observedPropertyRepository;
    
    @Autowired
    Utils utils;

    @Test
    void testAddData() {
        Integer id = 235;
        CS4PlayasData data = new CS4PlayasData();
        data.setId(id);
        cs4PlayasDataRepository.save(data);
        CS4PlayasObservedProperty dataReference = new CS4PlayasObservedProperty(66, "ddsgdagag");
        observedPropertyRepository.save(dataReference);
//        CS4PlayasObservedPropertyCountPK observedPropertyCountPk = new CS4PlayasObservedPropertyCountPK(data.getId(), 66);
//        CS4PlayasObservedPropertyCount observedPropertyCount = new CS4PlayasObservedPropertyCount(observedPropertyCountPk);
//        observedPropertyCount.setCount(2025);
//        cs4PlayasObservedPropertyCountRepository.save(observedPropertyCount);
        data.putObservedPropertyCount(dataReference, 7);
//        data.putObservedPropertyCount(dataReference, observedPropertyCount);
        cs4PlayasDataRepository.saveAndFlush(data);
    }
    
    @Test
    void testReadata() {
        try {
            utils.readExcelFile(new FileInputStream(new File("D:\\52n\\Projekte\\ENFORCE\\Playas-test.xlsx")));
            assertTrue(cs4PlayasDataRepository.count() > 0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
