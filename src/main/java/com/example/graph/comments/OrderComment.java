package com.example.graph.comments;

import com.example.graph.PlaceUserOrder;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Created by Dmitrij on 21.02.2016.
 */
@NodeEntity
public class OrderComment {
    @GraphId
    Long id;
    String comment;
    int rating;

    @RelatedTo(type = "WAS_RATED", direction = Direction.INCOMING)
    PlaceUserOrder order;

    public OrderComment(String comment, int rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public PlaceUserOrder getOrder() {
        return order;
    }

    public void setOrder(PlaceUserOrder order) {
        this.order = order;
    }
}
