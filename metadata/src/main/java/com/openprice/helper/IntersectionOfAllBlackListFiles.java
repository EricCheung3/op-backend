package com.openprice.helper;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.openprice.common.TextResourceUtils;

/**
 * this prints the intersection of all the not-catalog-name *.txt file
 * After printing, I copied the content to sharedConfiguratrationFileS**
 * and then deleted all the files in
 */
public class IntersectionOfAllBlackListFiles {

    public static void main(String[] args) throws Exception{

        final URL url = IntersectionOfAllBlackListFiles.class.getResource("/");
        File dir = new File((url.toURI()));
//        System.out.println("dir.listFiles().size="+dir.listFiles().toString());
        int countFiles = 0;
        final Set<String> all = new HashSet<String>();
        final Set<String> intersect = new HashSet<String>();
        for (File file : dir.listFiles()) {
            if(file.listFiles()==null)
                continue;
            final String fileName = file+"/not-catalog-item-names.txt";
            try{
                final List<String> orig = TextResourceUtils.loadStringArrayFromAbolutePath(Paths.get(fileName));
                if(orig!=null){
//                    System.out.println("orig.size="+orig.size());
                    final List<String> list = ConvertTextToJson.removeCommentEmptyLines(orig);
//                    System.out.println("list.size="+list.size());
                    countFiles++;
                    all.addAll(list);
                    if(countFiles==1){
                        intersect.addAll(list);
                    }else if(list.size() > 10){
                        intersect.retainAll(list);
                    }else{
                        System.out.println(""+file.toString());
                    }
                }
            }catch(Exception e){
                System.out.println(""+file.toString());
                continue;
            }
        }
//        System.out.println("countFiles="+countFiles);
//        all.forEach(str -> System.out.println(str));
//        System.out.println("all.size="+all.size());
//        System.out.println("intersect.size="+intersect.size());
//        intersect.forEach(str -> System.out.println(str));
    }
}
