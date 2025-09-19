package org.n52.project.enforce.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.n52.project.enforce.cs4.playas.db.repository.CS4PlayasDataRepository;
import org.n52.project.enforce.db.repository.DataRepository;
import org.n52.project.enforce.util.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DataService {

    private DataRepository dataRepository;
    
    private CS4PlayasDataRepository cs4PlayasDataRepository;
    
    private Utils utils;

    public DataService(DataRepository dataRepository, Utils utils, CS4PlayasDataRepository cs4PlayasDataRepository) {
        this.dataRepository = dataRepository;
        this.cs4PlayasDataRepository = cs4PlayasDataRepository;
        this.utils = utils;
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

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/cs4_playas_data",
            consumes = { "application/enforce-cs4-playas-data" })
    public ResponseEntity<Serializable> postCs4PlayasData(@RequestBody(
            required = true) InputStream cs4PlayasData) {
        try {
            utils.readExcelFile(cs4PlayasData);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/cs4_playas_data",
            produces = { "application/json" })
    public ResponseEntity<Serializable> getCs4PlayasData() {
        try {
            return ResponseEntity.ok(cs4PlayasDataRepository.getGeoJson());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
