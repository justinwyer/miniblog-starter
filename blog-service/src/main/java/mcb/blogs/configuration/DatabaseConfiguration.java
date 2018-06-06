package mcb.blogs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {
    @Bean
    public DataSource provideJdbcTemplate() {
        var dataSource = new DriverManagerDataSource("jdbc:h2:~/blog", "sa", "");
        return dataSource;
    }
}
