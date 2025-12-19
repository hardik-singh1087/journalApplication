package com.hardiksingh.journalApplication.dbConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(
                "mongodb://localhost:27017/journalDB"
        );
    }

    @Bean
    public MongoMappingContext mongoMappingContext() {
        MongoMappingContext mappingContext = new MongoMappingContext();
        // This is the line that enables @Indexed and @CompoundIndex annotations
        mappingContext.setAutoIndexCreation(true);
        return mappingContext;
    }
}
