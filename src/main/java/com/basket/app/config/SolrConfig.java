package com.basket.app.config;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfig {

    @Bean
    public SolrClient client()
    {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                "user","bitnami");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client1 = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        HttpSolrClient solrClient = new HttpSolrClient("http://192.168.64.2/solr/teacherData/",client1);
        return solrClient;
      //  return new HttpSolrClient.Builder("http://192.168.64.2/solr/teacherData",client1).build();
    }

}
