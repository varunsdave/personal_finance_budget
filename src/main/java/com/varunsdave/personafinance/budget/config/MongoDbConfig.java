package com.varunsdave.personafinance.budget.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import static org.bson.codecs.configuration.CodecRegistries.fromCodecs;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
public class MongoDbConfig {

    @Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port}")
    private int mongoPort;

    @Bean
    public MongoClient mongoClient() {

        MongoClientOptions.Builder mongoClientOptions = MongoClientOptions.builder();
        mongoClientOptions.codecRegistry(fromRegistries(fromCodecs(new UuidCodec(UuidRepresentation.STANDARD)),
                MongoClient.getDefaultCodecRegistry())).build();
        MongoClient mongoClient = new MongoClient(new ServerAddress(mongoHost, mongoPort), mongoClientOptions.build());
        return mongoClient;
    }

    @Bean
    public SimpleMongoDbFactory factory(final MongoClient mongoClient) {
        return new SimpleMongoDbFactory(mongoClient, "budget_tracking");
    }
}
