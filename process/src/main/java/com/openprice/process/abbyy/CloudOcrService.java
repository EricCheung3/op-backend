package com.openprice.process.abbyy;

import com.openprice.ocr.api.ImageProcessResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloudOcrService {

    private final Client restClient = new Client();

    public CloudOcrService(final String applicationId, final String password) {
        restClient.serverUrl = "http://cloud.ocrsdk.com";
        restClient.applicationId = applicationId;
        restClient.password = password;
    }

    public ImageProcessResult getImageResultFromCloud(final String imageFilePath) {
        ProcessingSettings settings = new ProcessingSettings();
        settings.setLanguage("English");
        settings.setOutputFormat(ProcessingSettings.OutputFormat.txt);

        try {
            // upload image file
            Task task = restClient.processImage(imageFilePath, settings);
            log.info("Start cloud ocr task {} for image {}.", task.getId(), imageFilePath);

            // wait for completion
            while (task.isTaskActive()) {
                Thread.sleep(1000);
                log.debug("Waiting for OCR complete..");
                task = restClient.getTaskStatus(task.getId());
            }

            // check status
            if (task.getStatus() == Task.TaskStatus.Completed) {
                log.debug("Downloading OCR result..");
                String result = restClient.downloadResult(task);
                log.debug("Result from ABBYY Cloud SDK is \n"+result);
                restClient.deleteTask(task.getId());
                return new ImageProcessResult(true, result, null);
            } else if (task.getStatus() == Task.TaskStatus.NotEnoughCredits) {
                log.warn("SEVERE: Not enough credits in ABBYY account to process document. "
                        + "Please add more pages to your application's account.");
                return new ImageProcessResult(false, null, "No credits");
            } else {
                log.warn("ABBYY: Cloud OCR SDK Task '{}' failed, the status is {}.", task.getId(), task.getStatus().toString());
                return new ImageProcessResult(false, null, "Failed task " + task.getStatus().toString());
            }

        } catch (Exception ex) {
            log.error("SEVERE: ABBYY: Got exception while calling Cloud OCR SDK.", ex);
            return new ImageProcessResult(false, null, ex.getMessage());
        }
    }
}
