package com.openprice.parser.helper;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openprice.common.StringCommon;
import com.openprice.common.TextResourceUtils;
import com.openprice.parser.categorypredictor.SimpleCategoryPredictor;
import com.openprice.store.data.ProductData;

/**
 * merge deals from flyers
 */
public class MergeDealsFromFlyers {

    private static final SimpleCategoryPredictor categoryPredictor = SimpleCategoryPredictor.fromConfig();

    public static void main(String[] args) throws Exception{
        final String byQuantityFile = "/Users/dongcui/Downloads/safewayflyers/simple_deals_extracted_byQuantity.txt";
        final List<String> byQuantity = TextResourceUtils.loadStringArrayFromAbolutePath(Paths.get(byQuantityFile));
        System.out.println("byQuantity.size="+byQuantity.size());
        final List<ProductData> catByQuantity = fromByQuantity(byQuantity);
        catByQuantity.forEach(p->System.out.println(p.getNaturalName()+","+p.getPrice()+ ", "+p.getProductCategory()));
        writeDataToJsonFile(catByQuantity, "/Users/dongcui/Downloads/safewayflyers/simple_deals_extracted_byQuantity.json");

        System.out.println("@@@@@End of catalog sold by quantity\n\n");

        final String byKgFile = "/Users/dongcui/Downloads/safewayflyers/simple_deals_extracted_byKg.txt";
        final List<String> byKg = TextResourceUtils.loadStringArrayFromAbolutePath(Paths.get(byKgFile));
        System.out.println("byKg.size="+byKg.size());
        final List<ProductData> catByKg = fromByKg(byKg);
        catByKg.forEach(p->System.out.println(p.getNaturalName()+","+p.getPrice() + ", "+p.getProductCategory()));
        writeDataToJsonFile(catByKg, "/Users/dongcui/Downloads/safewayflyers/simple_deals_extracted_byKg.json");
    }

    public static List<ProductData> fromByQuantity(final List<String> namePrices){
        return namePrices
        .stream()
        .map(line -> {
            String[] words2 = line.split("\\t");
            if(words2.length != 2){
                throw new RuntimeException("words2 = "+words2.length+" for line ="+line);
            }
            return naturalNameAndPrice(words2[1], words2[0]);
        })
        .collect(Collectors.toList());
    }

    public static List<ProductData> fromByKg(final List<String> namePrices){
        return namePrices
        .stream()
        .map(line -> {
            String[] words2 = line.split("\\t");
            if(words2.length != 2){
                throw new RuntimeException("words2 = "+words2.length+" for line ="+line);
            }
            return naturalNameAndPrice(words2[1], words2[0].trim()+"/kg");
        })
        .collect(Collectors.toList());
    }

    public static ProductData naturalNameAndPrice(final String naturalName, final String price){
        final String category = categoryPredictor.mostMatchingCategory(naturalName);
        return new ProductData(
                naturalName.trim(),
                StringCommon.EMPTY,
                price.trim(),
                naturalName.trim(),
                StringCommon.EMPTY,
                category);
    }

    public static String writeDataToJsonFile(final List<ProductData> data, final String file)
            throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        //        mapper.writeValue(new File(file), data);
        final String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        writeString(jsonInString, file);
        return jsonInString;
    }

    public static void writeString(final String strToWrite, final String fileName)
            throws IOException, Exception{
        FileWriter writer=new FileWriter(fileName);
        writer.write(strToWrite);
        writer.close();
    }

}
