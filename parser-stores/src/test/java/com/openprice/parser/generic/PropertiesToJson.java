package com.openprice.parser.generic;

import java.util.Properties;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PropertiesToJson {


    @Test
    public void test() throws Exception{
        Properties prop=new Properties();
        prop.load(PropertiesToJson.class.getResourceAsStream("/config/EdoJapan/Edojapan1/headerConfig.properties"));
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(prop));
    }
}
