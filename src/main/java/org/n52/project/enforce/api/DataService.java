package org.n52.project.enforce.api;

import java.io.Serializable;

import org.n52.project.enforce.db.repository.DataRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DataService {

    DataRepository dataRepository;

    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/data",
            produces = { "application/json" })
    public ResponseEntity<Serializable> getData(@RequestParam(
            value = "limit",
            required = false) Integer limit) {
        try {
            if (limit == null) {
                return ResponseEntity.ok(dataRepository.getGeoJson());
            } else {
                return ResponseEntity.ok(dataRepository.getGeoJsonWithLimit(limit));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

}
