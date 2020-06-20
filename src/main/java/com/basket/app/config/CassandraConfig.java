package com.basket.app.config;

import com.basket.app.pojo.BasketUser;
import com.basket.app.pojo.BatchRequest;
import com.basket.app.pojo.Category;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.datastax.driver.core.schemabuilder.SchemaBuilder.createKeyspace;
import static com.datastax.driver.mapping.NamingConventions.LOWER_CAMEL_CASE;
import static com.datastax.driver.mapping.NamingConventions.LOWER_SNAKE_CASE;

@Configuration
@Profile("local")
public class CassandraConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraConfig.class);

    @Bean
    public Cluster cluster(
            @Value("${cassandra.host}") String host,
            @Value("${cassandra.cluster.name}") String clusterName,
            @Value("${cassandra.port}") int port) {
        Cluster cluster = Cluster.builder()
                .addContactPoint(host)
                .withPort(port)
                .withClusterName(clusterName)
                                .build();
            cluster.getConfiguration().getCodecRegistry().register(new CustomCategoryCodec( DataType.varchar(),  Category.class));

            return cluster;

    }





    @Bean
    public Session session(Cluster cluster, @Value("${cassandra.keyspace:basket}") String keyspace)
            throws IOException {
        final Session session = cluster.connect();
        session.execute("USE " + keyspace);
        //setupKeyspace(session, keyspace);
        return session;
    }

    private void setupKeyspace(Session session, String keyspace) throws IOException {
        final Map<String, Object> replication = new HashMap<>();
        replication.put("class", "SimpleStrategy");
        replication.put("replication_factor", 1);
        session.execute(createKeyspace(keyspace).ifNotExists().with().replication(replication));
        session.execute("USE " + keyspace);
        //    String[] statements = split(IOUtils.toString(getClass().getResourceAsStream("/cql/setup.cql")), ";");
        //    Arrays.stream(statements).map(statement -> normalizeSpace(statement) + ";").forEach(session::execute);
    }

    @Bean
    public MappingManager mappingManager(Session session) {
        final PropertyMapper propertyMapper =
                new DefaultPropertyMapper()
                        .setNamingStrategy(new DefaultNamingStrategy(LOWER_CAMEL_CASE, LOWER_SNAKE_CASE));

        final MappingConfiguration configuration =
                MappingConfiguration.builder().withPropertyMapper(propertyMapper).build();
        return new MappingManager(session, configuration);
    }


    @Bean
    public Class getClassObj() throws JsonProcessingException {
        BatchRequest batchRequest = new BatchRequest();
        batchRequest.setId(UUID.randomUUID());
        batchRequest.setCategory(Category.ENGINEERING);
        batchRequest.setRequirement("Req1");
        batchRequest.setUserName("user1");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(batchRequest);
        LOGGER.info(json);
        return BatchRequest.class;
    }



}

//CREATE KEYSPACE basket WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};

