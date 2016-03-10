package com.openprice.parser.date;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.openprice.parser.date.ml.DateTokenFeatures;
import com.openprice.parser.date.ml.DateTokenType;
import com.openprice.parser.date.ml.RandomString;

import lombok.extern.slf4j.Slf4j;


/**
 * write training data for token prediction
 */
@Slf4j
public class WriteTrainingForTokenPrediction {

    final private static String SPLIT = ",";

    //splitters to generate 1 101:1.2 102:0.03
    final private static String SPLIT_LIB_SVM_FEATURE = " ";
    final private static String SPLIT_LIB_SVM_VALUE = ":";

    //class labels
    private final static int POSITIVE_LABEL = 1;
    private final static int NEGTIVE_LABEL = 0;

    final private static String DIR = "/Users/dongcui/Downloads/dateML/train_data/";

    public static void main(String[] args) throws Exception{

        final List<String> dayLines = daysData();
        dayLines.forEach(s->System.out.println(s));
        System.out.println("training data for Days");
        System.out.println("training data for Months");
        writeToFile(dayLines, DIR + "days_train.csv");


        final List<String> monthLines = monthsData();
        System.out.println("training data for Months");
        monthLines.forEach(s->System.out.println(s));
        writeToFile(monthLines, DIR + "months_train.csv");

        final List<String> yearLines = yearsData();
        System.out.println("training data for Years");
        yearLines.forEach(s->System.out.println(s));
        writeToFile(yearLines, DIR + "years_train.csv");

        final List<String> unpredLines = unpredictableData();
        System.out.println("unpredictable data");
        unpredLines.forEach(s->System.out.println(s));
        writeToFile(unpredLines, DIR + "unpredictable_train.csv");

        //write for binary prediction
        final List<String> monthsData_Binary = monthsDataForBinaryXGBoost();
        log.debug("monthsData_Binary.size="+monthsData_Binary.size());
        writeToFile(monthsData_Binary, DIR + "months_bin_train.csv");

        final List<String> nonpredData_Binary = unpredictableDataForBinaryXGBoost();
        log.debug("nonpredData_Binary.size="+nonpredData_Binary.size());
        writeToFile(nonpredData_Binary, DIR + "unpredictable_bin_train.csv");
    }

    public static List<String> unpredictableData() {
        //random strings between length 1 to 10
        final Random random = new Random();
        final int dataSize = 100;
        final List<String> result = new ArrayList<>();
        for(int i=0; i < dataSize; i++){
            final RandomString randomString = new RandomString(random.nextInt(10)+1);
            final String str = randomString.nextString();
            final DateTokenFeatures features = DateTokenFeatures.fromString(str);
            if(features.getMostSimMonthLiteralScore()>0.3 ||
                    (str.length()==2 && Character.isDigit(str.charAt(0)) && Character.isDigit(str.charAt(1)) ) ){
                log.debug("!!!!!not adding "+ str);
                continue;
            }
            result.add(formatLine(str, DateTokenType.Unpredictable));
        }
        return result;
    }

    //TODO this repeats unpredictableData
    public static List<String> unpredictableDataForBinaryXGBoost() {
        //random strings between length 1 to 10
        final Random random = new Random();
        final int dataSize = 100;
        final List<String> result = new ArrayList<>();
        for(int i=0; i < dataSize; i++){
            final RandomString randomString = new RandomString(random.nextInt(10)+1);
            final String str = randomString.nextString();
            final DateTokenFeatures features = DateTokenFeatures.fromString(str);
            if(features.getMostSimMonthLiteralScore() > 0.3 ||
                    (str.length()==2 && Character.isDigit(str.charAt(0)) && Character.isDigit(str.charAt(1)) ) ){
                log.debug("!!!!!not adding "+ str);
                continue;
            }
            result.add(formatLineBinary(str, NEGTIVE_LABEL));
        }
        return result;
    }

    public static String formatLine(final String str, final DateTokenType type) {
        final DateTokenFeatures features = DateTokenFeatures.fromString(str);
        return  str + SPLIT +
                type.getIntVal()+ SPLIT +
                features.toString(SPLIT);
    }

    public static String formatLineBinary(final String str, final int label) {
        final DateTokenFeatures features = DateTokenFeatures.fromString(str);
        return  label + SPLIT_LIB_SVM_FEATURE + features.toString(SPLIT_LIB_SVM_FEATURE, SPLIT_LIB_SVM_VALUE);
    }

    public static List<String> daysData() {
        final List<String> dayLines = new ArrayList<>();
        for(int i = 1; i <= 31; i++){
            dayLines.add(formatLine(i+"", DateTokenType.Day));
        }
        return dayLines;
    }

    public static List<String> monthsDataForBinaryXGBoost() {
        final List<String> monthLines = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            monthLines.add(formatLineBinary(i+"", POSITIVE_LABEL));
        }
        monthLines.addAll(literalMonthsDataBinaryXGBoost());
        return monthLines;
    }

    public static List<String> monthsData() {
        final List<String> monthLines = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            monthLines.add(formatLine(i+"", DateTokenType.Month));
        }
        monthLines.addAll(literalMonthsData());
        return monthLines;
    }

    public static List<String> literalMonthsData() {
        final List<String> monthLines = new ArrayList<>();
        new MonthLiterals()
        .monthLiterals()
        .stream()
        .forEach(s -> monthLines.add(formatLine(s, DateTokenType.Month)));
        return monthLines;
    }

    public static List<String> literalMonthsDataBinaryXGBoost() {
        final List<String> monthLines = new ArrayList<>();
        new MonthLiterals()
        .monthLiterals()
        .stream()
        .forEach(s -> monthLines.add(formatLineBinary(s, POSITIVE_LABEL)));
        return monthLines;
    }

    public static List<String> yearsData() {
        final List<String> lines = new ArrayList<>();
        for(int i = 2010; i <= 2016; i++){
            lines.add(formatLine(i+"", DateTokenType.Year));
        }
        for(int i = 10; i <= 16; i++){
            lines.add(formatLine(i+"", DateTokenType.Year));
        }
        return lines;
    }

    public static void writeToFile(final List<String> content, final String name) throws IOException {
        final Writer writer = new FileWriter(name);
        final StringBuilder str = new StringBuilder();
        for(int i=0; i < content.size()-1; i++ ){
            str.append(content.get(i) + "\n");
        }
        str.append(content.get(content.size()-1));//last line has no '\n'
        writer.write(str.toString());
        writer.close();
    }
}
