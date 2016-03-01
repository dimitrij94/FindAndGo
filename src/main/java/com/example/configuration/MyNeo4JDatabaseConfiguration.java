package com.example.configuration;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Dmitrij on 20.02.2016.
 */
@Configuration
@Order(0)
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@ComponentScan(basePackages = {"com.example.graph.**, com.example.neo_services", "com.example.graph_repositories"})
@EnableNeo4jRepositories(basePackages = {"com.example.graph_repositories"})
public class MyNeo4JDatabaseConfiguration extends Neo4jConfiguration {

    MyNeo4JDatabaseConfiguration() {
        setBasePackage("com.example.graph");
    }

    @Bean
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase("target/KeyStyleNeo4jDB.db");
    }

}
