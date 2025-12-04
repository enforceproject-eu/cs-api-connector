package org.n52.project.enforce.api.impl.minka;

import java.io.Serializable;
import java.math.BigDecimal;

import org.n52.project.enforce.api.BaseController;
import org.n52.project.enforce.api.MinkaApi;
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
public class MinkaApiController extends BaseController implements MinkaApi {

    @Autowired
    public MinkaApiController(DataRepository dataRepository, Utils utils,
            CS4PlayasDataRepository cs4PlayasDataRepository) {
        super(dataRepository, utils, cs4PlayasDataRepository);
    }

    @Override
    public ResponseEntity<Serializable> getCs4MinkaData(@Valid BigDecimal limit) {
        try {
            if (limit == null) {
                return ResponseEntity.ok(dataRepository.getGeoJson());
            } else {
                return ResponseEntity.ok(dataRepository.getGeoJsonWithLimit(limit.intValue()));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}
