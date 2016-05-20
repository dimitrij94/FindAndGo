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

    public Photo name(String name){
        this.name = name;
        return this;
    }
    public Photo path(String path){
        this.path= path;
        return this;
    }

    public Photo width(int width){
        this.width= width;
        return this;
    }
    public Photo heigth(int heigth){
        this.heigth = heigth;
        return this;
    }


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        return id.equals(photo.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
