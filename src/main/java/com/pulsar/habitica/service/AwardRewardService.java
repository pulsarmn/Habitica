package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.Dao;
import com.pulsar.habitica.dao.task.DailyTaskDaoImpl;
import com.pulsar.habitica.dao.task.HabitDaoImpl;
import com.pulsar.habitica.dao.task.TaskDaoImpl;
import com.pulsar.habitica.entity.task.Complexity;
import com.pulsar.habitica.entity.task.TaskBase;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

public class AwardRewardService {

    private Dao<Integer, ? extends TaskBase> dao;

    public BigDecimal getRewardCost(String type, int id) {
        loadDao(type);
        var task = dao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("The element with id " + id + " was not found!"));
        BigDecimal cost = task.getComplexity().getCost();
        return cost;
    }

    private void loadDao(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("illegal type - " + type);
        }else if (type.equals("task")) {
            dao = TaskDaoImpl.getInstance();
        }else if (type.equals("daily-task")) {
            dao = DailyTaskDaoImpl.getInstance();
        }else if (type.equals("habit")) {
            dao = HabitDaoImpl.getInstance();
        }
    }
}
