package com.basket.app.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.http.client.config.RequestConfig.Builder;


@Configuration
public class ElasticServerConfig {
    @Value("${es.hostname}")
    private String hostname;

    @Value("${es.port}")
    private String port;

    @Value("${es.scheme}")
    private String scheme;

    @Value("${es.userName}")
    private String userName;

    @Value("${es.password}")
    private String password;

   /*     @Bean(destroyMethod = "close")
      public  RestClient transportClient() {
            return RestClient
                    .builder(new HttpHost("localhost", 9200))
                    .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                        @Override
                        public Builder customizeRequestConfig(RequestConfig.Builder builder) {
                            return builder
                                    .setConnectTimeout(1000)
                                    .setSocketTimeout(5000);
                        }
                    })
                    .build();
        }*/


    @Bean
    public RestHighLevelClient restHighLevelClient(){
        final CredentialsProvider credentialsProvider =new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(userName, password));
        //RestClientBuilder builder =RestClient.builder(new HttpHost("0.0.0.0", 9200, "http")).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        RestClientBuilder builder =RestClient.builder(new HttpHost(hostname, Integer.parseInt(port), scheme)).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }
    }

