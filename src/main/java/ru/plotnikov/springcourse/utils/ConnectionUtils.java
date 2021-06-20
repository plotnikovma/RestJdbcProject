package ru.plotnikov.springcourse.utils;

import static ru.plotnikov.springcourse.config.SpringConfig.JDBC_DRIVER;
import static ru.plotnikov.springcourse.config.SpringConfig.PASS_DB;
import static ru.plotnikov.springcourse.config.SpringConfig.URL_DB;
import static ru.plotnikov.springcourse.config.SpringConfig.USER_DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author mplotnikov
 * @since 20.06.2021
 */
@Component
public class ConnectionUtils
{
    private final Connection connection;
    private final JdbcTemplate jdbcTemplate;

    public ConnectionUtils(@Value(JDBC_DRIVER) String jdbcDriver,
            @Value(URL_DB) String urlDb,
            @Value(USER_DB) String userDb,
            @Value(PASS_DB) String passDb)
    {
        this.jdbcTemplate = createJdbcTemplate(jdbcDriver, urlDb, userDb, passDb);
        this.connection = createConnection(jdbcDriver, urlDb, userDb, passDb);
    }

    private static JdbcTemplate createJdbcTemplate(String jdbcDriver, String urlDb, String userDb, String passDb)
    {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setUrl(urlDb);
        dataSource.setUsername(userDb);
        dataSource.setPassword(passDb);

        return new JdbcTemplate(dataSource);
    }

    /**
     * Создаем подключение к БД
     * @param urlDb адрес БД
     * @param userDb логин
     * @param passDb пароль
     * @return connection
     */
    private static Connection createConnection(String jdbcDriver, String urlDb, String userDb, String passDb)
    {
        try
        {
            Class.forName(jdbcDriver);
            return DriverManager.getConnection(urlDb, userDb, passDb);
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Connection getConnection()
    {
        return connection;
    }

    public JdbcTemplate getJdbcTemplate()
    {
        return jdbcTemplate;
    }
}
