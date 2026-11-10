package com.senla.core.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;

// month;country;code;value;netto;quantity;region;district;direction_eng;measure_eng;load_date
// 01/2016;IT;6204695000;131;1;7;46000;01;IM;ShT;2024-07-01T00:00:00.000+03:00
@Setter
@Getter
public class Data implements Serializable {
    private String month;
    private String country;
    private String code;
    private Float value;
    private String netto;
    private String quantity;
    private String region;
    private String district;
    private String direction_eng;
    private String measure_eng;
    private OffsetDateTime load_date;

    @Override
    public String toString() {
        return "Data{" +
                "month='" + month + '\'' +
                ", country='" + country + '\'' +
                ", code='" + code + '\'' +
                ", value='" + value + '\'' +
                ", netto='" + netto + '\'' +
                ", quantity='" + quantity + '\'' +
                ", region='" + region + '\'' +
                ", district='" + district + '\'' +
                ", direction_eng='" + direction_eng + '\'' +
                ", measure_eng='" + measure_eng + '\'' +
                ", load_date='" + load_date + '\'' +
                '}';
    }

}
