package com.pulsar.habitica.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Habit {

    private Integer id;
    private String heading;
    private String description;
    private Complexity complexity;
    private Integer goodSeries;
    private Integer badSeries;
    private Integer userId;
}
