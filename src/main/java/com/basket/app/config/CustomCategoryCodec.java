package com.basket.app.config;

import com.basket.app.pojo.Category;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.exceptions.InvalidTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class CustomCategoryCodec extends TypeCodec<Category> {
    protected CustomCategoryCodec(DataType cqlType, Class<Category> javaClass) {
        super(cqlType, javaClass);
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBasicAuthenticationEntryPoint.class);

    @Override
    public ByteBuffer serialize(Category category, ProtocolVersion protocolVersion) throws InvalidTypeException {
        LOGGER.info("SERIALIZE*****"+category.toString());
        try {
            return   ByteBuffer.wrap(format(category).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Category deserialize(ByteBuffer byteBuffer, ProtocolVersion protocolVersion) throws InvalidTypeException {
        LOGGER.info("deserialize*****"+byteBuffer);
        LOGGER.info("deserialize asCharBuffer*****"+byteBuffer.asCharBuffer().toString());
        String converted = null;
        try {
            converted = new String(byteBuffer.array(), "UTF-8");
           LOGGER.info("deserialize converted*****"+converted   );

            return parse(converted);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Category parse(String s) throws InvalidTypeException {
        LOGGER.info("PARSE "+s);
        return Category.valueOf(s);
    }

    @Override
    public String format(Category category) throws InvalidTypeException {
        LOGGER.info("FORMAT "+category.toString());

        return category.toString();
    }
}
