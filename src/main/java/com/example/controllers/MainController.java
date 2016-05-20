package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Dmitrij on 23.12.2015.
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public String getIndexPage() {
        return "static/app/index.html";
    }

    @RequestMapping("/templates/{name}/{component}")
    public String getPage(@PathVariable("name") String name,
                          @PathVariable("component") String component) {
        return "/static/app/"+name + "/" + component+".html";
    }


}
