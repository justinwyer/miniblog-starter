package mcb.blogs.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.requery.Persistable;
import io.requery.cache.WeakEntityCache;
import io.requery.jackson.EntityMapper;
import io.requery.meta.EntityModel;
import io.requery.sql.*;
import mcb.blogs.Models;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Executors;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public ObjectMapper objectMapper(@Autowired EntityDataStore entityDataStore) {
        return new EntityMapper(Models.BLOG, entityDataStore);
    }

    @Bean
    public EntityDataStore<Persistable> provideDataStore() {
        ConnectionProvider connectionProvider = () -> DriverManager.getConnection("jdbc:h2:~/blog", "sa", "");
        io.requery.sql.Configuration configuration = new ConfigurationBuilder(connectionProvider, Models.BLOG)
                .useDefaultLogging()
                .setEntityCache(new WeakEntityCache())
                .setWriteExecutor(Executors.newSingleThreadExecutor())
                .build();

        SchemaModifier tables = new SchemaModifier(configuration);
        tables.createTables(TableCreationMode.DROP_CREATE);
        return new EntityDataStore<>(configuration);
    }
}
