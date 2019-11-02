package com.basket.app.repo;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import java.util.List;

public abstract class BaseRepo<T> {
    protected Mapper<T> mapper;
    protected Session session;

    public BaseRepo(){

    }

    public BaseRepo(MappingManager mappingManager , Class<T> type) {
        this.mapper = mappingManager.mapper(type);
        this.session = mappingManager.getSession();
    }



    public T save(T obj) {
        System.out.println("Inside save");

        mapper.save(obj);

        System.out.println("Completed save");
        return obj;
    }

}
