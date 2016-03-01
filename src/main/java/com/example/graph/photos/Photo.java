package com.example.graph.photos;

import com.example.json_views.UserJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * Created by Dmitrij on 25.02.2016.
 */
@NodeEntity
public class Photo  {
    @GraphId
    protected Long id;

    @Indexed
    protected String name;

    @JsonView(UserJsonView.class)
    protected String path;

    protected int width;

    protected int heigth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }
}
