package com.controlpanel;

import com.controlpanel.dao.TemperatureDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TempDeviceController {
    private static final Logger log = LoggerFactory.getLogger(TempDeviceController.class);
    @Autowired
    private TemperatureDao temperatureDao;


    @RequestMapping(method = RequestMethod.POST, value = "/temp")
    public TemperatureMetric postTemp(@RequestParam(value="mac") String mac,
                                      @RequestParam(value="temp") float temp){
        TemperatureMetric temperatureMetric = new TemperatureMetric(mac, temp);
        temperatureDao.add(temperatureMetric);
        log.info(temperatureDao.getAll().toString());
        return temperatureMetric;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/temp")
    public ArrayNode postTemp(){
        List<TemperatureMetric> tempList = temperatureDao.getAll();
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        tempList.forEach(temp -> {
            ObjectNode objecNode = mapper.createObjectNode();
            objecNode.put("x", temp.getDate().getTime());
            objecNode.put("y", temp.getTemp());
            arrayNode.add(objecNode);
        });
        return arrayNode;
    }


}
