package com.example.graph_repositories.order;

import com.example.graph.PlaceUserOrder;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created by Dmitrij on 21.02.2016.
 */
public interface OrderRepository extends CrudRepository<PlaceUserOrder, String> {

    @Query("MATCH (user:PlaceUser {name:\"testUser\"})-[:MEMBER_OF]->(place:Place {name:\"testPlace\"}) " +
            "MATCH (place)-[:PROVIDES]->(menu:PlaceMenuService{name:\"testMenu\"})" +
            "MATCH (place)-[:HIRES]->(employee:PlaceEmployee{name:\"testEmployee\"})" +
            "MATCH (menu)-[:CAN_INCLUDE]->(services:AdditionalService{name:\"testAdditionalService\"})" +
            "WITH menu,place,employee,user,services;" +
            "CREATE (o:PlaceUserOrder {time:timestamp()})" +
            "CREATE (employee)-[:PREFORMS]->(o)" +
            "CREATE (o)-[:ORDERED]->(menu)" +
            "FOREACH (s IN services | CREATE (o)-[:INCLUDES]->(s))" +
            "CREATE (user)-[:ORDERS]-(o);")
    void saveOrder(String placeName,
                   String userName,
                   String employeeName,
                   String menuName,
                   Collection<String> additionalServicesNames,
                   int year, int month, int day);

}
