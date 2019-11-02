package com.basket.app.repo;

import com.basket.app.pojo.BatchRequest;
import com.basket.app.pojo.Category;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Ordering;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.extras.codecs.enums.EnumNameCodec;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

@Repository
public class BatchRequestRepo extends BaseRepo<BatchRequest> {

    private static final String TABLE ="batch_request";



    public BatchRequestRepo(MappingManager mappingManager, Class<BatchRequest> type) {
        super(mappingManager, type);
    }

    public BatchRequest find(String userName, UUID id) {
        return mapper.get(userName, id);
    }

    public List<BatchRequest> findAll() {
        final ResultSet result = session.execute(select().all().from(TABLE));
        return mapper.map(result).all();
    }

    public List<BatchRequest> findAllByUserName(String userName) {
        Ordering order = QueryBuilder.desc( "id" );

        final ResultSet result = session.execute(select().all().from(TABLE).where(eq("user_name", userName)).orderBy(order));
        return mapper.map(result).all();
    }

    public void delete(String userName, UUID id) {
        mapper.delete( userName,  id);
    }

}
