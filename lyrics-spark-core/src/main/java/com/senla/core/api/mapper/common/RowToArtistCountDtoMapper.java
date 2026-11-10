package com.senla.core.api.mapper.common;

import com.senla.core.api.dto.ArtistCountDto;
import org.apache.spark.sql.Row;

public class RowToArtistCountDtoMapper implements MappingFunction<ArtistCountDto> {
    @Override
    public ArtistCountDto map(Row data) {
        return new ArtistCountDto(data.getString(0), data.getLong(1));
    }

    @Override
    public Class<ArtistCountDto> getOutputClass() {
        return ArtistCountDto.class;
    }
}
