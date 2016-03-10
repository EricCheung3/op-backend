package com.openprice.common;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.core.io.Resource;

import com.google.common.io.ByteStreams;

public class ImageResourceUtils {

    public static String loadImageAsBase64String(final Resource resource) throws IOException {
        try (final InputStream is = resource.getInputStream()) {
            final byte[] content = ByteStreams.toByteArray(is);
            return new String(Base64.getEncoder().encode(content));
        }
    }

    public static byte[] resizeJpgImage(final byte[] content) {
        byte[] resizedContent = content; // default to original content

        try (final InputStream is = new ByteArrayInputStream(content)) {
            final BufferedImage inputImage = ImageIO.read(is);
            if (inputImage.getWidth() > 512) {
                final float scale = inputImage.getWidth() / 512.0f;
                final int width = (int)(inputImage.getWidth() / scale);
                final int height = (int)(inputImage.getHeight() / scale);
                final BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());
                final Graphics2D g2d = outputImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED); // prefer speed
                g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE); // disable dithering
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(inputImage, 0, 0, width, height, null);
                g2d.dispose();

                try (final ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                    ImageIO.write(outputImage, "JPG", os);
                    resizedContent = os.toByteArray();
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException("Cannot resize receipt image: "+ ex.getMessage());
        }

        return resizedContent;
    }
}
