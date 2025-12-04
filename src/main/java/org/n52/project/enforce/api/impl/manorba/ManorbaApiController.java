package org.n52.project.enforce.api.impl.manorba;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

import org.n52.project.enforce.api.BaseController;
import org.n52.project.enforce.api.ManorbaApi;
import org.n52.project.enforce.cs4.playas.db.repository.CS4PlayasDataRepository;
import org.n52.project.enforce.db.repository.DataRepository;
import org.n52.project.enforce.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.annotation.Generated;
import jakarta.validation.Valid;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-04T07:49:15.855249900+01:00[Europe/Berlin]", comments = "Generator version: 7.13.0")
@Controller
@RequestMapping("${openapi.eNFORCEDataAccess.base-path:}")
public class ManorbaApiController extends BaseController implements ManorbaApi {

    @Autowired
    public ManorbaApiController(DataRepository dataRepository, Utils utils,
            CS4PlayasDataRepository cs4PlayasDataRepository) {
        super(dataRepository, utils, cs4PlayasDataRepository);
    }

    @Override
    public ResponseEntity<Serializable> addCs4ManorbaDataAsBody(@Valid String body) {
        try {
            utils.readExcelFile(body);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Serializable> getCs4ManorbaData(@Valid BigDecimal limit) {
        try {
            return ResponseEntity.ok(cs4PlayasDataRepository.getGeoJson());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
