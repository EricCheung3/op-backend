export SPRING_PROFILES_ACTIVE=dev,docker
export LOGGING_FILE_FOLDER=/groundtruth/logs
export OCR_SERVER_NAME=ocr1

java -jar ocr-server.jar &