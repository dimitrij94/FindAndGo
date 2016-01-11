package com.example.constants.image.sizes;

/**
 * Created by Dmitrij on 22.12.2015.
 */
public enum EmployeeImageSizes implements ImageSize{
    EMPLOYEE_PAGE_MAIN(800,600,"800"),
    EMPLOYEE_PAGE_SMALL(300,200,"300");
    int width;
    int height;
    int index;
    String name;

    EmployeeImageSizes(int width,int height,String name){
        this.width=width;
        this.height=height;
        this.name=name;
        this.index=Math.round((width / height)*10);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
