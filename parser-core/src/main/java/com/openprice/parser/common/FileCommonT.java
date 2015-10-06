package com.openprice.parser.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileCommonT {


    //this is for the Resource folder
    public static List<List<String>>
    getAllTextFilesForResourceFolder(final String branchRootOfTestFiles) throws Exception{
        final URL url = FileCommonT.class.getResource(branchRootOfTestFiles);
        List<List<String>> all=new ArrayList<List<String>>();
        if(url==null){
            throw new Exception(branchRootOfTestFiles+" does not exist.");
        }else{
            File dir = new File(url.toURI());
            for (File file : dir.listFiles()) {
                if(file.toString().endsWith(".txt")){
                    List<String> lines=FileCommonT.readLines(file);
                    all.add(lines);
                }
            }
        }
        return all;
    }

    //this is for a local directory
    public static List<List<String>>
    getAllTextFilesForLocalDir(final String folder) throws Exception{
        final Path url = Paths.get(folder);
        List<List<String>> all=new ArrayList<List<String>>();
        if(url==null){
            throw new Exception(folder+" does not exist.");
        }else{
            File dir = new File(url.toString());
            for (File file : dir.listFiles()) {
                if(file.toString().endsWith(".txt")){
                    List<String> lines=FileCommonT.readLines(file);
                    all.add(lines);
                }
            }
        }
        return all;
    }

    //this is for a local directory: get all the file names.
    //TODO: write a class with getAllTextFilesForLocalDir?
    public static List<String>
    getAllTextFileNamesForLocalDir(final String folder) throws Exception{
        final Path url = Paths.get(folder);
        List<String> all=new ArrayList<String>();
        if(url==null){
            throw new Exception(folder+" does not exist.");
        }else{
            File dir = new File(url.toString());
            for (File file : dir.listFiles()) {
                if(file.toString().endsWith(".txt")){
                    all.add(file.toString());
                }
            }
        }
        return all;
    }

    //this is for a local directory: get all the file names.
    //TODO: write a class with getAllTextFilesForLocalDir?
    public static Map<String, List<String>>
    getAllTextFileNamesForLocalDirInMap(final String folder) throws Exception{
        Map<String, List<String>> fileNameToContent = new HashMap<String, List<String>>();
        final Path url = Paths.get(folder);
        if(url==null){
            throw new Exception(folder+" does not exist.");
        }else{
            File dir = new File(url.toString());
            for (File file : dir.listFiles()) {
                if(file.toString().endsWith(".txt")){
                    List<String> content=FileCommonT.readLines(file);
                    fileNameToContent.put(file.toPath().getFileName().toString(), content);
                }
            }
        }
        return fileNameToContent;
    }

    /**
     * write string into a file name
     * @param  strToWrite  the String to write
     * @param  fileName    [description]
     * @throws IOException [description]
     * @throws Exception   [description]
     */
    public static void writeJson(final String strToWrite, final String fileName)
            throws IOException, Exception{
        FileWriter writer=new FileWriter(fileName);
        writer.write(strToWrite);
        writer.close();
    }

    /* convert Json to friendly print format */
    public static String niceJsonString(final Object object){
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(object);
    }

    public static List<String> readLinesFromString(String content){
        BufferedReader br;
        List<String> list=null;
        try{
            br=new BufferedReader(new StringReader(content));
            list=readBufferedReader(br);
        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }

    //skipping original lines
    public static List<String>  readBufferedReader(BufferedReader br) throws IOException{
        List<String> list=new ArrayList<String>();
        String line=br.readLine();
        while(line!=null){
            //System.out.println(line);
            if(!line.trim().isEmpty() && !line.trim().equals("\n"))
                list.add(line);
            line=br.readLine();
        }
        br.close();
        return list;
    }


    //not skipping empty lines
    public static List<String>  readBufferedReaderOriginal(BufferedReader br) throws IOException{
        List<String> list=new ArrayList<String>();
        String line=br.readLine();
        while(line!=null){
            list.add(line);
            line=br.readLine();
        }
        br.close();
        return list;
    }


    public static List<String> readLines(File file){
        return readLines(file.getAbsolutePath());
    }

    public static List<String> readLines(String file){
        List<String> list=null;
        BufferedReader br=null;
        list= new ArrayList<String>();

        try{
            br=new BufferedReader(new FileReader(file));
            //        	br=new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            list=readBufferedReader(br);
        }catch(Exception e){
            e.printStackTrace();
        }
        /*finally{
            br.close();
        }*/
        return list;
    }

    // public void writeLines(final String file, final List<String> list){
    //     BufferedWriter bw=null;
    //     try{
    //         br=new BufferedWriter(new FileWriter(file));
    //         writeBufferedWriter(br, list);
    //     }catch(Exception e){
    //         e.printStackTrace();
    //     }
    //     /*finally{
    //         br.close();
    //     }*/
    // }

    public static void writeString(final String file, final String content){
        try{
            PrintWriter out=new PrintWriter(file);
            out.print(content);
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    /**
     * get the file name in URI format
     * @param  dir      directory
     * @param  fileName file name
     * @return          uri file name
     */
    public static String fileNameURI(final String dir, final String fileName){
        return StringCommon.formatPath(dir)+fileName;
    }

    /**
     * read file using getResourceAsStream, skipping empty lines
     * @param  dir      the directory URI
     * @param  fileName the file name
     * @return          a list of lines in the file
     */
    public static List<String> readLinesFromResourceFileSkipEmpty(final String fileNameWithPath){
        BufferedReader br=null;
        List<String> list= new ArrayList<String>();
        try{
            InputStream in=FileCommonT.class.getResourceAsStream(fileNameWithPath);
            if(in==null){
                throw new NullPointerException("getResourceAsStream returns null");
            }
            br=new BufferedReader(new InputStreamReader(in));
            list=readBufferedReader(br);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("file reading error: file="+fileNameWithPath);
            System.exit(1);
        }
        /*finally{
            br.close();
        }*/
        return list;
    }

    public static List<String> readLinesFromResourceSkipEmpty(final String dir, final String fileName){
        return readLinesFromResourceFileSkipEmpty(fileNameURI(dir, fileName));
    }


    public static List<String> readOriginalLinesFromResourcePath(final String fileWithPath){
        BufferedReader br=null;
        List<String> list=new ArrayList<String>();
        try{
            InputStream in=FileCommonT.class.getResourceAsStream(fileWithPath);
            if(in==null){
                throw new NullPointerException("getResourceAsStream returns null");
            }
            br=new BufferedReader(new InputStreamReader(in));
            list=readBufferedReaderOriginal(br);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("file reading error: file="+fileWithPath);
            System.exit(1);
        }
        /*finally{
            br.close();
        }*/
        return list;
    }

    //get original line format; not skipping empty lines
    public static List<String> readOriginalLinesFromResource(final String dir, final String fileName){
        return readOriginalLinesFromResourcePath(fileNameURI(dir, fileName));
    }


    public static boolean createFolder(String file){
        File theDir=new File(file);
        boolean success=false;
        if(!theDir.exists()){
            System.out.println("creating a directory"+theDir);
            try{
                theDir.mkdir();
                success=true;
            }catch (SecurityException se){
                se.printStackTrace();
            }
            if(success){
                System.out.println("folder successfully created");
            }
        }
        return success;
    }
}
