package com.basket.app.repo;

import com.basket.app.controller.BasicAuthenticationController;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

public abstract class BaseRepo<T> {
    protected Mapper<T> mapper;
    protected Session session;
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRepo.class);
    public BaseRepo(){

    }

    public BaseRepo(MappingManager mappingManager , Class<T> type) {
        this.mapper = mappingManager.mapper(type);
        this.session = mappingManager.getSession();

        LOGGER.info("Session created "+session);
    }



    public T save(T obj) {
        LOGGER.info("Inside save");

        mapper.save(obj);

        LOGGER.info("Completed save");
        return obj;
    }

}
