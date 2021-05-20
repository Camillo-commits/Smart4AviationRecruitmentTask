package com.k.o.smart4aviation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.vaadin.artur.helpers.LaunchUtil;
@SpringBootApplication
public class Smart4aviationApplication {

    public static void main(String[] args) {
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(Smart4aviationApplication.class, args));
    }

}
