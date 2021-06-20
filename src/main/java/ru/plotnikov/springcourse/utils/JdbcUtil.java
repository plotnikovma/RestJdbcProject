package ru.plotnikov.springcourse.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

/**
 * утилитный класс получения соединения с БД для JdbcDriver и JdbcTemplate
 *
 * @author mplotnikov
 * @since 20.06.2021
 */
@Component
public class JdbcUtil
{
    private static final  String URL_DB = "${url.db}";
    private static final  String USER_DB = "${user.db}";
    private static final String PASS_DB = "${pass.db}";
    private static final String JDBC_DRIVER = "${jdbc.driver}";

    private final Connection connection;
    private final JdbcTemplate jdbcTemplate;

    public JdbcUtil(@Value(JDBC_DRIVER) String jdbcDriver,
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
