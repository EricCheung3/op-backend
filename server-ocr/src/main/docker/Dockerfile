FROM ubuntu:15.10
MAINTAINER Haipeng (Paul) <solittlework.li@gmail.com>
RUN apt-get update
RUN apt-get install -y wget curl git
RUN apt-get install -y build-essential
RUN apt-get install -y libleptonica-dev
RUN apt-get install -y tesseract-ocr-dev
#install all of the tesseract language packages
RUN apt-get install -y tesseract-ocr-all
RUN apt-get install -y make cmake git libgtk2.0-dev pkg-config python python-dev python-numpy libavcodec-dev libavformat-dev libswscale-dev libjpeg-dev libpng-dev libtiff-dev
RUN  rm /bin/sh \
&& ln /bin/bash /bin/sh
WORKDIR  cd ~/Downloads
RUN  git clone https://github.com/itseez/opencv \
&& mv opencv /opt
WORKDIR /opt/opencv
RUN git checkout 2.4.10.1 \
&& mkdir build
WORKDIR /opt/opencv/build
RUN cmake -j4 -D CMAKE_BUILD_TYPE=RELEASE -D CMAKE_INSTALL_PREFIX=/usr/local ..
RUN  make -j4 \
&& make -j4 install
RUN ldconfig
RUN ln /dev/null /dev/raw1394 #remove the compile error of opencv

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
