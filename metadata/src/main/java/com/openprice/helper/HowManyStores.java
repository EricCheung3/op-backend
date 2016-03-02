package com.openprice.helper;

import java.io.File;
import java.net.URL;

public class HowManyStores {
    public static void main(String[] args) throws Exception{

        final URL url = IntersectionOfAllBlackListFiles.class.getResource("/");
        File dir = new File((url.toURI()));
        int countStores = 0;
        for (File file : dir.listFiles()) {
            if(file.isDirectory())
                countStores++;
        }
        System.out.println("We have "+ countStores + " parsers.");
    }
}
