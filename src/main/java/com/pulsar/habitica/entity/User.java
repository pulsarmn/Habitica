package com.pulsar.habitica.entity;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {

    private Integer id;
    private String email;
    private String password;
    private String nickname;
    private LocalDate createdAt;

}