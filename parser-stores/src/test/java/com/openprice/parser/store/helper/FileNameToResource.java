package com.openprice.parser.store.helper;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/* a helper.
 * give a list of file pathths, generate the resource for testing.
 *
 */
public class FileNameToResource {

    private final static String LOCAL_HEADER = "/Users/dongcui/Documents/op-backend/parser-stores/target/test-classes";
    private final static String RESOURCE_HEADER = "@Value(\"classpath:";
    private final static String RESOURCE_TAIL = "\")";
    private final static String RECEIPT_HEADER="private Resource ";
    private final static String FILE_NAME_HEADER = "receipt_";
    private final static String RECEIPT_TAIL=";";

    public static void main(String[] args) throws Exception{
        final String chainTestRoot = "NoFrills";
        final String testFileRoot = "/testFiles/"+chainTestRoot;

        final List<String> fileNames = getAllFileNames(testFileRoot);
        final List<String> all = getAllResources(fileNames);
        final List<String> testCodes = getAllTestCodes(chainTestRoot.toLowerCase(), fileNames);

        all.forEach(file->System.out.println(file));
//        System.out.println("\n");
        testCodes.forEach(c->System.out.println(c));
    }

    public static String extractDigitsFromReceiptName(final String receiptFileName){
        String fileNameWithExt =  Paths.get(receiptFileName).getFileName().toString();
//        System.out.println("fileNameWithExt="+fileNameWithExt);
        String fileNameWithOutExt = removeAllExtensions(fileNameWithExt);
        return fileNameWithOutExt;
    }

    public static String removeAllExtensions(final String fileNameWithExt){
        final String[] words = fileNameWithExt.split("\\.");
        return words[0];
    }

    public static String getOnlyFileName(final String absoluteFilePath){
        if(!absoluteFilePath.startsWith(LOCAL_HEADER))
            throw new RuntimeException("absoluteFilePath:"+absoluteFilePath+" should contain "+LOCAL_HEADER);
        return absoluteFilePath.substring(LOCAL_HEADER.length());
    }

    public static String toResource(final String fileName){
        return RESOURCE_HEADER
                + fileName
                +RESOURCE_TAIL
                + "\n"
                + RECEIPT_HEADER
                + FILE_NAME_HEADER
                + extractDigitsFromReceiptName(fileName)
                + RECEIPT_TAIL
                +"\n";
    }

    public static List<String> getAllFileNames(final String testFileRoot) throws Exception{
        final URL url = FileNameToResource.class.getResource(testFileRoot);
        List<String> all=new ArrayList<>();
        if(url==null){
            throw new Exception(testFileRoot+" does not exist.");
        }else{
            File dir = new File(url.toURI());
            for (File file : dir.listFiles()) {
                if(file.toString().endsWith(".txt")){
                    all.add(getOnlyFileName(file.toString()));
                }
                if(file.listFiles()==null)
                    continue;
                for (File f2 : file.listFiles()){
                    if(f2.toString().endsWith(".txt")){
                        all.add(getOnlyFileName(f2.toString()));
                    }
                }
            }
        }
        return all;
    }

    public static List<String> getAllResources(final List<String> fileNames){
        final List<String> allResources = new ArrayList<>();
        fileNames.forEach(file->allResources.add(toResource(file)));
        return allResources;
    }

    public static List<String> getAllTestCodes(final String chainCode, final List<String> fileNames){
        final List<String> allCodes = new ArrayList<>();
        fileNames.forEach(file->allCodes.add(getTestCode(chainCode, extractDigitsFromReceiptName(file))));
        return allCodes;
    }

    public static String getTestCode(final String chainCode, final String receiptName){
        final String indent = "    ";
        final String code = "@Test" + "\n"
                + "public void " +  FILE_NAME_HEADER+receiptName + "()  throws Exception {" +"\n"
                + indent +"final List<String> receiptLines = new ArrayList<>();" + "\n"
                + indent +"TextResourceUtils.loadFromTextResource(" + FILE_NAME_HEADER+receiptName + ", (line)-> receiptLines.add(line));" +"\n"
                + indent +"assertTrue(receiptLines.size() > 0);" +"\n"
                + indent +"ParsedReceipt receipt = simpleParser.parseLines(receiptLines);" + "\n"
                + indent +"printResult(receipt);" + "\n"
                + indent + "assertEquals(\""+chainCode + "\", receipt.getChainCode());" + "\n"
                + indent +"Iterator<ParsedItem> iterator = receipt.getItems().iterator();" + "\n"
                + indent +"Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();" + "\n\n"
                +"}" + "\n";
        return code;
    }
}


