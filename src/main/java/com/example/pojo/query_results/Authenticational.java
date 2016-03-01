package com.example.pojo.query_results;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * Created by Dmitrij on 29.02.2016.
 */
@QueryResult
public class Authenticational {

    private String name;
    private String password;
    boolean enabled;
    private String authority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
