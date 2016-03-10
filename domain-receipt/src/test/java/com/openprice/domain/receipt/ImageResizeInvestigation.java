package com.openprice.domain.receipt;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.io.ByteStreams;
import com.openprice.common.ImageResourceUtils;

public class ImageResizeInvestigation {

    public static void main(String[] args) throws Exception {
        Resource imageResource = new ClassPathResource("BostonPizza.JPG");
        byte[] content = ByteStreams.toByteArray(imageResource.getInputStream());
        byte[] resizedContent = ImageResourceUtils.resizeJpgImage(content);

        File file = new File("bp.jpg");
        try (final OutputStream out = new BufferedOutputStream(new FileOutputStream(file)))
        {
            out.write(resizedContent);
        } catch (IOException ex) {
            throw new RuntimeException("System Error! Cannot save image.", ex);
        }

        System.out.println("Origianl Size = "+ content.length);
        System.out.println("Resized Image Size = "+ resizedContent.length);
        String base64 = new String(Base64.getEncoder().encode(resizedContent));
        System.out.println("Base64 Size = "+ base64.length());
    }
}
