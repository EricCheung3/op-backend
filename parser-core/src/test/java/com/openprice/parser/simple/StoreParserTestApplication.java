package com.openprice.parser.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({ "com.openprice.parser"})
public class StoreParserTestApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(StoreParserTestApplication.class, args);
    }

}
