package ru.plotnikov.springcourse.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * Пока что наполняем для шаблонизатора thymeleaf
 * реализуем WebMvcConfigurer для того, чтобы вместо стандартного шаблонизатора использовать thymeleaf
 *
 * @author mplotnikov
 * @since 15.06.2021
 */
@Configuration
@PropertySource(value = {"classpath:properties.properties"}, ignoreResourceNotFound=true)
@ComponentScan("ru.plotnikov.springcourse")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer
{
    public static final  String URL_DB = "${url.db}";
    public static final  String USER_DB = "${user.db}";
    public static final String PASS_DB = "${pass.db}";
    public static final String JDBC_DRIVER = "${jdbc.driver}";

    private final String jdbcDriver;
    private final String urlDb;
    private final String userDb;
    private final String passDb;
    private final ApplicationContext applicationContext;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext,
            @Value(JDBC_DRIVER) String jdbcDriver,
            @Value(URL_DB) String urlDb,
            @Value(USER_DB) String userDb,
            @Value(USER_DB) String passDb)
    {
        this.applicationContext = applicationContext;
        this.jdbcDriver = jdbcDriver;
        this.urlDb = urlDb;
        this.userDb = userDb;
        this.passDb = passDb;
    }

    /**
     * Передаем шаблонизатору контекст приложения, путь со всеми представлениями и формат представлений
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    /**
     * используем для того чтобы вместо стандартного шаблонизатора использовать thymeleaf
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }
}
