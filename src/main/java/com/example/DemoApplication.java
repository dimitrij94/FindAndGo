package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.Array;

@SpringBootApplication
@ComponentScan("com.example.configuration")
public class DemoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    public static <T extends Iterable> T[] addToArray(T[] first, T second) {
        int fLen = first.length;
        //noinspection unchecked
        T[]newArr = (T[]) Array.newInstance(first.getClass().getComponentType(),fLen+1);
        System.arraycopy(first,0,newArr,0,fLen);
        newArr[fLen+1]=second;
        return newArr;
    }

    public static <T extends Iterable> T[] addAllToArray(T[] first, T[] second) {
        int firstLen = first.length;
        int secondLen = second.length;

        if (firstLen == 0) return second;
        if (secondLen == 0) return first;
        //noinspection unchecked
        T[] newArr = (T[]) Array.newInstance(first.getClass().getComponentType(), firstLen + secondLen);
        System.arraycopy(first, 0, newArr, 0, firstLen);
        System.arraycopy(second, 0, newArr, firstLen, secondLen);
        return newArr;
    }
}
