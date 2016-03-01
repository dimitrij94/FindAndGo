package com.example.graph_repositories.user;

import com.example.graph.user.PlaceUser;
import com.example.pojo.query_results.Authenticational;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitrij on 21.02.2016.
 */
public interface UserRepository extends CrudRepository<PlaceUser, String> {
    PlaceUser findByUserName(String userName);

    PlaceUser findByEmail(String email);

    @Query("MATCH (n:Person{name:{name}})" +
            "RETURN n;")
    Authenticational authenticate(String name);

    @Query("MERGE (place:Place{name:{0}})<-[r:MEMBER_OF]-(u:PlaceUser {name:{1}})")
    void addMembership(String placeName, String userName);

    @Query("MATCH (n:Person)" +
            "WHERE n.userName = {name} OR n.email == {email}" +
            "RETURN COUNT(n)")
    int findByNameOrEmail(String name, String email);
}
