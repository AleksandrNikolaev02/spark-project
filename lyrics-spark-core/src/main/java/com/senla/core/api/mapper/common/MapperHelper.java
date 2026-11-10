package com.senla.core.api.mapper.common;

import com.senla.core.api.dto.ArtistCountDto;
import org.apache.spark.sql.Row;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapperHelper {
    private final Map<Class<?>, MappingFunction<?>> mappers = new ConcurrentHashMap<>();

    {
        mappers.put(ArtistCountDto.class, new RowToArtistCountDtoMapper());
    }

    public <E> E map(Row row, Class<E> entityClass) {
        MappingFunction<?> mapper = getFunction(entityClass);

        return entityClass.cast(mapper.map(row));
    }

    public <E> MappingFunction<E> getFunction(Class<E> entityClass) {
        MappingFunction<?> mapper = mappers.get(entityClass);

        if (mapper == null) {
            throw new IllegalArgumentException("Mapper not found for type: " + entityClass);
        }

        return (MappingFunction<E>) mapper;
    }
}
