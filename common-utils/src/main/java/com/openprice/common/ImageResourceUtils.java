package com.openprice.common;

import java.io.IOException;
import java.util.Base64;

import org.springframework.core.io.Resource;

import com.google.common.io.ByteStreams;

public class ImageResourceUtils {

    public static String loadImageAsBase64String(final Resource resource) throws IOException {
        final byte[] content = ByteStreams.toByteArray(resource.getInputStream());
        return new String(Base64.getEncoder().encode(content));
    }
}
