package com.openprice.parser.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.openprice.store.MetadataLoader;
import com.openprice.store.StoreMetadata;

@Configuration
@EnableAutoConfiguration
@ComponentScan({ "com.openprice.parser"})
public class StoreParserTestApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(StoreParserTestApplication.class, args);
    }

    @Bean
    public StoreMetadata storeMetadate() {
        return MetadataLoader.loadMetadata();
    }
}
