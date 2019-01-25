package com.white5703.akyuu.dao.converter;

import java.util.Arrays;
import java.util.List;
import org.greenrobot.greendao.converter.PropertyConverter;

public class StringListConverter implements PropertyConverter<List<String>, String> {

    private static final String sSPLIT = "#SPLIT#";

    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if (databaseValue != null) {
            return Arrays.asList(databaseValue.split(sSPLIT));
        }
        return null;
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        if (entityProperty != null) {
            int size = entityProperty.size();
            if (size == 0) {
                return "";
            }
            StringBuilder builder = new StringBuilder();
            builder.append(entityProperty.get(0));
            for (int i = 0; i < size; i++) {
                builder.append(sSPLIT).append(entityProperty.get(i));
            }
            return builder.toString();
        }
        return null;
    }
}
