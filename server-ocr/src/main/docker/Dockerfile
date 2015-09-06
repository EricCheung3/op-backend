FROM openprice/ocr
MAINTAINER Yuan Ji

# download and install JDK 1.8
 # set shell variables for java installation
ENV java_version 1.8.0_45
ENV filename jdk-8u45-linux-x64.tar.gz
ENV downloadlink http://download.oracle.com/otn-pub/java/jdk/8u45-b14/$filename
 # download java, accepting the license agreement
RUN wget --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" -O /tmp/$filename $downloadlink
 # unpack java
RUN mkdir /opt/java-oracle && tar -zxf /tmp/$filename -C /opt/java-oracle/
ENV JAVA_HOME /opt/java-oracle/jdk$java_version
ENV PATH $JAVA_HOME/bin:$PATH
 # configure symbolic links for the java and javac executables
RUN update-alternatives --install /usr/bin/java java $JAVA_HOME/bin/java 20000 && update-alternatives --install /usr/bin/javac javac $JAVA_HOME/bin/javac 20000

VOLUME [ "/groundtruth/images" ]
WORKDIR /groundtruth

ADD ocr-server.jar ocr-server.jar
ADD OP_OCR OP_OCR

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","ocr-server.jar"]
