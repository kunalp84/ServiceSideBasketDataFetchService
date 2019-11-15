package com.basket.app.solr;

import com.basket.app.pojo.BatchRequest;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SolarRepo {
    @Autowired
    private SolrClient client;

    public void pushteacherEnquiryData(BatchRequest request) throws IOException, SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("requestid", request.getId().toString());
        doc.addField("userName", request.getUserName());
        doc.addField("subjectName", request.getSubjectName());
        doc.addField("requirement", request.getRequirement());

        client.add(doc);
        client.commit();

    }


}
