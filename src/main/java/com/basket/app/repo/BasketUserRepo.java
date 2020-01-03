package com.basket.app.repo;

import com.basket.app.pojo.BasketUser;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Ordering;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
@Repository
public class BasketUserRepo  {
    private static final String TABLE ="basketuser";
    protected Mapper<BasketUser> mapper;
    protected Session session;


    public BasketUserRepo(MappingManager mappingManager ) {
        this.mapper = mappingManager.mapper(BasketUser.class);
        this.session = mappingManager.getSession();

        System.out.println("Session created "+session);
    }



    public BasketUser save(BasketUser obj) {
        System.out.println("Inside save");

        mapper.save(obj);

        System.out.println("Completed save");
        return obj;
    }


    public List<BasketUser> findAll() {
        final ResultSet result = session.execute(select().all().from(TABLE));
        return mapper.map(result).all();
    }

    public BasketUser findByUserName(String userName) {
      //  Ordering order = QueryBuilder.desc( "id" );

        System.out.println("session");
        System.out.println(session);
        System.out.println("session.execute(select().all().from(TABLE)");
        System.out.println(session.execute(select().all().from(TABLE)));

        final ResultSet result = session.execute(select().all().from(TABLE).where(eq("name", userName)));//.orderBy(order));
        return mapper.map(result).one();
    }
}
