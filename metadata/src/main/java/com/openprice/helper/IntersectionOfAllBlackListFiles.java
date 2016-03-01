package com.openprice.helper;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import com.openprice.common.TextResourceUtils;

/**
 * this prints the intersection of all the not-catalog-name *.txt file
 */
public class IntersectionOfAllBlackListFiles {

    public static void main(String[] args) throws Exception{

        final URL url = IntersectionOfAllBlackListFiles.class.getResource("/");
        File dir = new File((url.toURI()));
        System.out.println("dir.listFiles().size="+dir.listFiles().toString());
        int countFiles = 0;
        for (File file : dir.listFiles()) {
            if(file.listFiles()==null)
                continue;
            final String fileName = file+"/not-catalog-item-names.txt";

            try{
                final List<String> orig = TextResourceUtils.loadStringArrayFromAbolutePath(Paths.get(fileName));
                if(orig!=null){
                    System.out.println("orig.size="+orig.size());
                    final List<String> list = ConvertTextToJson.removeCommentEmptyLines(orig);
                    System.out.println("list.size="+list.size());
                    countFiles++;
                }
            }catch(Exception e){
                continue;
            }
        }
        System.out.println("countFiles="+countFiles);
    }
}
