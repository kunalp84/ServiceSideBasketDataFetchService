package com.basket.app.repo;

import com.basket.app.pojo.BasketUser;
import com.basket.app.pojo.OtpStorage;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

@Repository
public class OtpRepo {
    private static final String TABLE ="otpuser";
    protected Mapper<OtpStorage> mapper;
    protected Session session;

    private static final Logger LOGGER = LoggerFactory.getLogger(OtpRepo.class);

    public OtpRepo(MappingManager mappingManager ) {
        this.mapper = mappingManager.mapper(OtpStorage.class);
        this.session = mappingManager.getSession();

       LOGGER.info("Session created "+session);
    }



    public OtpStorage save(OtpStorage obj) {
        LOGGER.info("Inside save");

        mapper.save(obj);

        LOGGER.info("Completed save");
        return obj;
    }


   /* public List<OtpStorage> findAll() {
        final ResultSet result = session.execute(select().all().from(TABLE));
        return mapper.map(result).all();
    } */

    public OtpStorage findByUserName(String userName) {
      //  Ordering order = QueryBuilder.desc( "id" );

        LOGGER.info("session");
        LOGGER.info(""+session);
        LOGGER.info("session.execute(select().all().from(TABLE)");
        LOGGER.info(""+session.execute(select().all().from(TABLE)));

        final ResultSet result = session.execute(select().all().from(TABLE).where(eq("name", userName)));//.orderBy(order));
        return mapper.map(result).one();
    }
}
