package com.controlpanel.dao;

import com.controlpanel.ElasticConnector;
import com.controlpanel.TempDeviceController;
import com.controlpanel.TemperatureMetric;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TemperatureDao {
    private static final Logger log = LoggerFactory.getLogger(TempDeviceController.class);
    private Client client;

    @Autowired
    public TemperatureDao(ElasticConnector connector) {
        this.client = connector.getClient();
    }

    public void add(TemperatureMetric tempMetric) {
        try {
            String json = new ObjectMapper().writeValueAsString(tempMetric);
            IndexResponse response = client.prepareIndex("room", "temp")
                    .setSource(json, XContentType.JSON)
                    .get();
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

    }

    public List<TemperatureMetric> getAll() {
        List<TemperatureMetric> tempList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        SearchResponse response = client.prepareSearch("room")
                .setTypes("temp")
                .addSort("date", SortOrder.DESC)
                .setSize(100).get();
            for (SearchHit hit : response.getHits()) {
                try {
                    tempList.add(objectMapper.readValue(hit.getSourceAsString(), TemperatureMetric.class));
                } catch (IOException e){
                    log.error(e.getMessage());
                }
            }
        return tempList;
    }
}
