package org.n52.project.enforce.api;

import java.io.Serializable;

import org.n52.project.enforce.db.repository.DataRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DataService {

    DataRepository dataRepository;

    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/data")
    public ResponseEntity<Serializable> getData() {
        try {
            return ResponseEntity.ok(dataRepository.getGeoJson());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

}
