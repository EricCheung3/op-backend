package com.openprice.parser.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextResourceUtils {

    public static String loadTextResource(final Resource resource) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024)) {
            return FileCopyUtils.copyToString(br);
        } catch (IOException e) {
            log.error("Load resource error", e);
            throw new RuntimeException("Cannot load resource from "+resource.getFilename());
        }

    }
    public static void loadFromTextResource(Resource resource, Consumer<String> lineConsumer) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024)) {
            String line;
            while ((line = br.readLine()) != null) {
                lineConsumer.accept(line);
            }
        } catch (IOException e) {
            log.error("Load resource error", e);
            throw new RuntimeException("Cannot load resource from "+resource.getFilename());
        }
    }

    public static void loadFromInputStream(final InputStream is, Consumer<String> lineConsumer) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is), 1024)) {
            String line;
            while ((line = br.readLine()) != null) {
                lineConsumer.accept(line);
            }
        } catch (IOException e) {
            log.error("Load text error", e);
            throw new RuntimeException("Cannot load text from inputstream!");
        }
    }

    public static List<String> loadStringArray(Resource resource) {
        final List<String> result = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(resource, (line)-> result.add(line));
        return result;
    }

    public static List<String> loadStringArray(final InputStream is){
        final List<String> list =new ArrayList<>();
        loadFromInputStream(is, line->list.add(line));
        return list;
    }

    public static List<String> loadStringArray(final String resourceFileName){
        return loadStringArray(TextResourceUtils.class.getResourceAsStream(resourceFileName));
    }

    public static List<String> loadStringArrayFromAbolutePath(final Path absoluteFileName)
            throws Exception{
        return TextResourceUtils.loadStringArray(new FileInputStream(absoluteFileName.toString()));
    }

}


