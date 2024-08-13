package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.Reward;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class RewardDaoImpl implements RewardDao {

    private static final String FIND_ALL_SQL = "SELECT * FROM task.reward";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM task.reward WHERE id = ?";
    private static final String SAVE_SQL = """
            INSERT INTO task.reward (heading, description, cost, user_id)
            VALUES (?, ?, ?, ?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE task.reward
            SET heading = ?,
            description = ?,
            cost = ?
            WHERE id = ?
            """;
    private static final String DELETE_BY_ID_SQL = "DELETE FROM task.reward WHERE id = ?";
    private static final RewardDaoImpl INSTANCE = new RewardDaoImpl();

    private RewardDaoImpl() {}

    @Override
    public List<Reward> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = statement.executeQuery();
            List<Reward> rewards = new ArrayList<>();
            while (resultSet.next()) {
                Reward reward = buildReward(resultSet);
                rewards.add(reward);
            }
            return rewards;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Reward> findById(Integer id) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);

            var resultSet = statement.executeQuery();
            Reward reward = null;
            if (resultSet.next()) {
                reward = buildReward(resultSet);
            }
            return Optional.ofNullable(reward);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reward save(Reward entity) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            setRewardParameters(statement, entity);
            statement.setInt(4, entity.getUserId());
            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            keys.next();
            entity.setId(keys.getInt("id"));
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reward update(Reward entity) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(UPDATE_SQL)) {
            setRewardParameters(statement, entity);
            statement.setInt(4, entity.getId());
            statement.executeUpdate();

            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            statement.setInt(1, id);
            int status = statement.executeUpdate();
            return status == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Reward entity) {
        return false;
    }

    @Override
    public List<Reward> findByHeading(String heading) {
        return null;
    }

    @Override
    public List<Reward> findAllByUserId(Integer userId) {
        return null;
    }

    public Reward buildReward(ResultSet resultSet) throws SQLException {
        return Reward.builder()
                .id(resultSet.getInt("id"))
                .heading(resultSet.getString("heading"))
                .description(resultSet.getString("description"))
                .cost(resultSet.getBigDecimal("cost"))
                .userId(resultSet.getInt("user_id"))
                .build();
    }

    public void setRewardParameters(PreparedStatement statement, Reward entity) throws SQLException {
        statement.setString(1, entity.getHeading());
        statement.setString(2, entity.getDescription());
        statement.setBigDecimal(3, entity.getCost());
    }

    public static RewardDaoImpl getInstance() {
        return INSTANCE;
    }
}
