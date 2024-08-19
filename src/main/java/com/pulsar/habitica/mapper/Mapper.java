package com.pulsar.habitica.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
