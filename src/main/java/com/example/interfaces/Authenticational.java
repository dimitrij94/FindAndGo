package com.example.interfaces;

/**
 * Created by Dmitrij on 02.01.2016.
 */
public interface Authenticational {
    long getId();
    String getEmail();
    String getName();
    String getPassword();
    boolean isEnabled();
    String getAuthority();
}
