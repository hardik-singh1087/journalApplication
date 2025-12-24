package com.hardiksingh.journalApplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class MongoConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        // Now it pulls from the property/environment variable
        return new SimpleMongoClientDatabaseFactory(mongoUri);
    }

    @Bean
    public MongoMappingContext mongoMappingContext() {
        MongoMappingContext mappingContext = new MongoMappingContext();
        // This is the line that enables @Indexed and @CompoundIndex annotations
        mappingContext.setAutoIndexCreation(true);
        return mappingContext;
    }

    @Bean
    public PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

}
