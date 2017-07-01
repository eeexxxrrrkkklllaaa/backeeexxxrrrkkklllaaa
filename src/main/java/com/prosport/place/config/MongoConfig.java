package com.prosport.place.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static java.util.Collections.singletonList;

/**
 * @author Vlad Milyutin
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.prosport.place.repository")
@PropertySource("classpath:/place.properties")
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${db.host}")
    private String host;
    @Value("${db.port}")
    private int port;
    @Value("${db.name}")
    private String db;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    @Override
    protected String getDatabaseName() {
        return db;
    }

    @Override
    public Mongo mongo() throws Exception {
	    return new MongoClient( singletonList(new ServerAddress(host, port)),
                singletonList(MongoCredential.createCredential(username, db, password.toCharArray())));
    }
}
