package com.openprice.helper;

import java.io.File;
import java.net.URL;

/**
 * not very precise becaus it contains "com"
 */
public class HowManyStores {
    public static void main(String[] args) throws Exception{

        final URL url = IntersectionOfAllBlackListFiles.class.getResource("/");
        File dir = new File((url.toURI()));
        int countStores = 0;
        for (File file : dir.listFiles()) {
            if(file.isDirectory()){
                System.out.println(file.getName());
                countStores++;
            }
        }
        System.out.println("We have "+ countStores + " parsers.");
    }
}
