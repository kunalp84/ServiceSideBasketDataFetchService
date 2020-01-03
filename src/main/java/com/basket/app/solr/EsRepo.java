package com.basket.app.solr;
//https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-document-index.html
import com.basket.app.pojo.BasketUser;
import com.basket.app.pojo.BatchRequest;
import com.basket.app.pojo.USERTYPE;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.apache.lucene.analysis.CharArrayMap.emptyMap;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
@Component
public class EsRepo {




    public void pushteacherEnquiryData(BatchRequest request) throws IOException, SolrServerException {

        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";


      /*  Client client = new PreBuiltTransportClient(
                Settings.builder().put("client.transport.sniff", true)
                        .put("cluster.name","elasticsearch").build())
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("0.0.0.0"), 9300));

        IndexResponse response = client.prepareIndex("twitter", "_doc", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
                .get();*/
    }


    public void pushteacherEnquiryDataUsingRest(BatchRequest batchRequest) throws IOException, SolrServerException {

        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";


        final Map<String, Object> source = new LinkedHashMap<>();
        source.put("title", "Elasticsearch: The Definitive Guide. ...");
        source.put("categories",
                new Map[] {
                        singletonMap("name", "analytics"),
                        singletonMap("name", "search"),
                        singletonMap("name", "database store")
                }
        );
        source.put("publisher", "O'Reilly");
        source.put("description", "Whether you need full-text search or ...");
        source.put("published_date", "2015-02-07");
        source.put("isbn", "978-1449358549");
        source.put("rating", 4);


        final HttpEntity payload = new NStringEntity(new ObjectMapper().writeValueAsString(source),
                ContentType.APPLICATION_JSON);

/*
        transportClient.performRequestAsync(
                HttpPut.METHOD_NAME,
                "catalog/books/978-1449358549",
                emptyMap(),
                payload,
                new ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                       System.out.println("The document has been indexed successfully");
                    }

                    @Override
                    public void onFailure(Exception ex) {
                        System.out.println("The document has been not been indexed"+ ex.toString());
                    }
                });*/
    }


    public void postBatchRequestOnElasticServer(BatchRequest batchRequest)
    {

      final CredentialsProvider credentialsProvider =new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("username", "password"));
        RestClientBuilder builder =RestClient.builder(new HttpHost("0.0.0.0", 9200, "http")).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
  RestHighLevelClient client = new RestHighLevelClient(builder);





        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("userName", batchRequest.getUserName());
        jsonMap.put("requirement", batchRequest.getRequirement());
        jsonMap.put("subjectName", batchRequest.getSubjectName());
        jsonMap.put("category", batchRequest.getCategory().toString());
        jsonMap.put("id",batchRequest.getId().toString());
        IndexRequest indexRequest = new IndexRequest("requirement").type("batch")
                .id(batchRequest.getId().toString()).source(jsonMap);
                indexRequest.create(true);

        ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println("onResponse called");
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("on Failure called"+e);

            }
        };
       // client.indexAsync(indexRequest, RequestOptions.DEFAULT, listener);
        try {
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(indexRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void postBasketUserOnElasticServer(BasketUser basketUser, USERTYPE typeOfUser)
    {

        final CredentialsProvider credentialsProvider =new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("username", "password"));
        RestClientBuilder builder =RestClient.builder(new HttpHost("0.0.0.0", 9200, "http")).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        RestHighLevelClient client = new RestHighLevelClient(builder);





        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", basketUser.getName());
        jsonMap.put("category", basketUser.getCategory());
        jsonMap.put("emailId", basketUser.getEmailId());
        jsonMap.put("mobile", basketUser.getMobile());
        jsonMap.put("mode",basketUser.getMode());
        jsonMap.put("location",basketUser.getLocation());
        jsonMap.put("freeTextRequirement",basketUser.getFreeTextRequirement());
        jsonMap.put("industrialExperience",basketUser.getIndustrialExperience());
        jsonMap.put("teachingExperience",basketUser.getTeachingExperience());
        jsonMap.put("userType",basketUser.getUserType());
        jsonMap.put("institute",basketUser.getInstitute());

        jsonMap.put("mode",basketUser.getMode());
        jsonMap.put("subject",basketUser.getSubject());

System.out.println("THE JSON MAP" + basketUser.getMobile());
        System.out.println(jsonMap);
        IndexRequest indexRequest = new IndexRequest(typeOfUser.toString().toLowerCase()).type(typeOfUser.toString())
              .id(basketUser.getName()).source(jsonMap);


            UpdateRequest updateRequest = new UpdateRequest().index(typeOfUser.toString().toLowerCase()     ) .type(typeOfUser.toString()).id(basketUser.getName()).fetchSource(true).   upsert(indexRequest);
       // indexRequest.create(true);
        updateRequest.doc(indexRequest);

        try {
          client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
       // System.out.println(response);
        ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println(" USER INSERT onResponse called");
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println(" USER INSERT on Failure called"+e);

            }
        };
        // client.indexAsync(indexRequest, RequestOptions.DEFAULT, listener);


        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
