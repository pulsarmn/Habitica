package com.pulsar.habitica.dao;

import com.pulsar.habitica.entity.Reward;
import com.pulsar.habitica.util.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pulsar.habitica.dao.table.RewardTable.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class RewardDaoImpl implements RewardDao {

    private static final String FIND_ALL_SQL = "SELECT * FROM %s"
            .formatted(FULL_TABLE_NAME);
    private static final String FIND_BY_ID_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, ID_COLUMN);
    private static final String SAVE_SQL = "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)"
            .formatted(FULL_TABLE_NAME,
                    HEADING_COLUMN,
                    DESCRIPTION_COLUMN,
                    COST_COLUMN,
                    USER_ID_COLUMN);
    private static final String UPDATE_SQL = "UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?"
            .formatted(FULL_TABLE_NAME,
                    HEADING_COLUMN,
                    DESCRIPTION_COLUMN,
                    COST_COLUMN,
                    ID_COLUMN);
    private static final String DELETE_BY_ID_SQL = "DELETE FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, ID_COLUMN);
    private static final String FIND_BY_HEADING_SQL = "SELECT * FROM %s WHERE LOWER(%s) LIKE CONCAT('%', ?, '%')"
            .replaceFirst("%s", FULL_TABLE_NAME).replaceFirst("%s", HEADING_COLUMN);
    private static final String FIND_ALL_BY_USER_ID_SQL = "SELECT * FROM %s WHERE %s = ?"
            .formatted(FULL_TABLE_NAME, USER_ID_COLUMN);
    private static final RewardDaoImpl INSTANCE = new RewardDaoImpl();

    private RewardDaoImpl() {}

    @Override
    public List<Reward> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            return getRewardsList(statement);
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
            entity.setId(keys.getInt(ID_COLUMN));
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
        return deleteById(entity.getId());
    }

    @Override
    public List<Reward> findByHeading(String heading) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_BY_HEADING_SQL)) {
            statement.setString(1, heading.toLowerCase());
            return getRewardsList(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reward> findAllByUserId(Integer userId) {
        try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_ALL_BY_USER_ID_SQL)) {
            statement.setInt(1, userId);
            return getRewardsList(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Reward> getRewardsList(PreparedStatement statement) throws SQLException {
        var resultSet = statement.executeQuery();
        List<Reward> rewards = new ArrayList<>();
        while (resultSet.next()) {
            Reward reward = buildReward(resultSet);
            rewards.add(reward);
        }
        return rewards;
    }

    private Reward buildReward(ResultSet resultSet) throws SQLException {
        return Reward.builder()
                .id(resultSet.getInt(ID_COLUMN))
                .heading(resultSet.getString(HEADING_COLUMN))
                .description(resultSet.getString(DESCRIPTION_COLUMN))
                .cost(resultSet.getBigDecimal(COST_COLUMN))
                .userId(resultSet.getInt(USER_ID_COLUMN))
                .build();
    }

    private void setRewardParameters(PreparedStatement statement, Reward entity) throws SQLException {
        statement.setString(1, entity.getHeading());
        statement.setString(2, entity.getDescription());
        statement.setBigDecimal(3, entity.getCost());
    }

    public static RewardDaoImpl getInstance() {
        return INSTANCE;
    }
}