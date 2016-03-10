package com.openprice.domain.receipt;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.file.FileSystemService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReceiptUploadService {

    private final ReceiptRepository receiptRepository;
    private final ReceiptImageRepository receiptImageRepository;
    private final FileSystemService fileSystemService;

    @Inject
    public ReceiptUploadService(final ReceiptRepository receiptRepository,
                                final ReceiptImageRepository receiptImageRepository,
                                final FileSystemService fileSystemService) {
        this.receiptRepository = receiptRepository;
        this.receiptImageRepository = receiptImageRepository;
        this.fileSystemService = fileSystemService;
    }

    public ReceiptImage uploadImageForNewReceipt(final UserAccount user, final String base64Data) {
        byte[] content = null;
        try {
            content = Base64.getDecoder().decode(base64Data);
        } catch (IllegalArgumentException ex) {
            log.error("Not a valid base64 encoded data!", ex);
            throw new RuntimeException("Not a valid image content.");
        }
        final Receipt receipt = receiptRepository.save(new Receipt(user));
        return saveImage(receipt, content);
    }

    public ReceiptImage uploadImageForNewReceipt(final UserAccount user, final MultipartFile file) {
        byte[] content = null;
        try {
            content = file.getBytes();
        } catch (IOException ex) {
            log.error("No content of receipt image to save!");
            throw new RuntimeException("No image content.");
        }

        final Receipt receipt = receiptRepository.save(new Receipt(user));
        return saveImage(receipt, content);
    }

    public ReceiptImage appendImageToReceipt(final Receipt receipt, final String base64Data) {
        byte[] content = null;
        try {
            content = Base64.getDecoder().decode(base64Data);
        } catch (IllegalArgumentException ex) {
            log.error("Not a valid base64 encoded data!", ex);
            throw new RuntimeException("Not a valid image content.");
        }

        final ReceiptImage image = saveImage(receipt, content);
        receiptRepository.save(receipt);
        return image;
    }

    public ReceiptImage appendImageToReceipt(final Receipt receipt, final MultipartFile file) {
        byte[] content = null;
        try {
            content = file.getBytes();
        } catch (IOException ex) {
            log.error("No content of receipt image to save!");
            throw new RuntimeException("No image content.");
        }

        final ReceiptImage image = saveImage(receipt, content);

        receiptRepository.save(receipt);
        return image;
    }

    public String getImageFilePath(final ReceiptImage image) {
        final Receipt receipt = image.getReceipt();
        final UserAccount user = receipt.getUser();
        return user.getId() + fileSystemService.getPathSeparator() + image.getFileName();
    }

    public Path getImageFile(final ReceiptImage image) {
        final Receipt receipt = image.getReceipt();
        final UserAccount user = receipt.getUser();
        final Path imageFolder = fileSystemService.getReceiptImageSubFolder(user.getId());
        return imageFolder.resolve(image.getFileName());
    }

    private ReceiptImage saveImage(final Receipt receipt, final byte[] content) {
        final ReceiptImage image = receipt.createImage();
        final Path imageFile = fileSystemService.saveReceiptImage(receipt.getUser().getId(), image.getFileName(), content);
        image.setStatus(ProcessStatusType.UPLOADED);
        image.setBase64(resizeAndReturnBase64(content));
        log.debug("Save uploaded receipt image to {}.", imageFile);
        return receiptImageRepository.save(image);
    }

    private String resizeAndReturnBase64(final byte[] content) {
        byte[] resizedContent = content; // default to original content

        try (final InputStream is = new ByteArrayInputStream(content)) {
            final BufferedImage inputImage = ImageIO.read(is);
            if (inputImage.getWidth() > 1024) {
                final float scale = inputImage.getWidth() / 1024;
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
            log.error("Cannot resize receipt image: {}", ex.getMessage());
        }

        return new String(Base64.getEncoder().encode(resizedContent));
    }
}
