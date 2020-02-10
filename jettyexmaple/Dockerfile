FROM maven
WORKDIR /opt
RUN wget https://download.java.net/java/early_access/loom/3/openjdk-15-loom+3-20_linux-x64_bin.tar.gz
RUN tar xvf  openjdk-15-loom+3-20_linux-x64_bin.tar.gz
ENV PATH /opt/jdk-15/bin:$PATH

COPY src .

CMD ["java", "--enable-preview", "--source 15", "main/java/WebServer.java"]

