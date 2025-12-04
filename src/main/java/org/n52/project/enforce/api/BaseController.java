package org.n52.project.enforce.api;

import org.n52.project.enforce.cs4.playas.db.repository.CS4PlayasDataRepository;
import org.n52.project.enforce.db.repository.DataRepository;
import org.n52.project.enforce.util.Utils;


public abstract class BaseController {

    protected DataRepository dataRepository;
    
    protected CS4PlayasDataRepository cs4PlayasDataRepository;
    
    protected Utils utils;

    public BaseController(DataRepository dataRepository, Utils utils, CS4PlayasDataRepository cs4PlayasDataRepository) {
        this.dataRepository = dataRepository;
        this.cs4PlayasDataRepository = cs4PlayasDataRepository;
        this.utils = utils;
    }

}
