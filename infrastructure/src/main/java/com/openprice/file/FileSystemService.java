package com.openprice.file;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileSystemService {

    public static final String RECEIPTS_FOLDER_NAME = "receipts";

    private final FileSystem fileSystem;
    private final FileFolderSettings fileFolderSettings;

    public FileSystemService(final FileFolderSettings fileFolderSettings) {
        this.fileFolderSettings = fileFolderSettings;

        if (fileFolderSettings.isVirtual()) {
            fileSystem = Jimfs.newFileSystem(Configuration.unix());

            // create image root folder and receipt image folder
            try{
                Files.createDirectory(getImageRootFolder());
            } catch (IOException ex) {
                throw new RuntimeException("Cannot create root folder: "+fileFolderSettings.getImageRootFolder(), ex);
            }
            try{
                Files.createDirectory(getReceiptImageFolder());
            } catch (IOException ex) {
                throw new RuntimeException("Cannot create root folder: "+fileFolderSettings.getImageRootFolder(), ex);
            }
        } else {
            fileSystem = FileSystems.getDefault();

            // check image root folder
            final Path imageRootFolder = fileSystem.getPath(fileFolderSettings.getImageRootFolder());
            if (!Files.exists(imageRootFolder, LinkOption.NOFOLLOW_LINKS)) {
                throw new RuntimeException("No image root folder: "+fileFolderSettings.getImageRootFolder());
            }
        }
    }

    public Path getImageRootFolder() {
        return fileSystem.getPath(fileFolderSettings.getImageRootFolder());
    }

    public Path getReceiptImageFolder() {
        return fileSystem.getPath(fileFolderSettings.getImageRootFolder(), RECEIPTS_FOLDER_NAME);
    }

    public Path getReceiptImageSubFolder(String subFolderName) {
        final Path subFolder = fileSystem.getPath(fileFolderSettings.getImageRootFolder(), RECEIPTS_FOLDER_NAME, subFolderName);
        if (Files.notExists(subFolder)) {
            try {
                Files.createDirectory(subFolder);
            } catch (IOException ex) {
                log.error("Cannot create receipt image sub folder {}, please check file system!", subFolderName);
                throw new RuntimeException("System Error! Cannot create receipt image sub folder."); //TODO design exceptions
            }
        }
        return subFolder;
    }

    public String getPathSeparator() {
        return fileSystem.getSeparator();
    }

    public Path saveReceiptImage(final String subFolderName, final String fileName, final byte[] content) {
        final Path imageFile = getReceiptImageSubFolder(subFolderName).resolve(fileName);
        try (final OutputStream out = new BufferedOutputStream(Files.newOutputStream(imageFile, StandardOpenOption.CREATE_NEW)))
        {
            out.write(content);
        } catch (IOException ex) {
            throw new RuntimeException("System Error! Cannot save image.", ex);
        }
        return imageFile;
    }
}
