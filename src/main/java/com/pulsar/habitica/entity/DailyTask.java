package com.pulsar.habitica.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DailyTask {

    private Integer id;
    private String heading;
    private String description;
    private Complexity complexity;
    private LocalDate deadline;
    private Boolean status;
    private Integer series;
    private Integer userId;
}
