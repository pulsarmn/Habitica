package com.pulsar.habitica.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Reward {

    private Integer id;
    private String heading;
    private String description;
    private BigDecimal cost;
    private Integer userId;
}
