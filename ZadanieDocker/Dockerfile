FROM ubuntu:20.04

RUN apt-get update

RUN apt-get -y install tzdata

ENV TZ="Europe/Warsaw"

RUN apt-get -y install software-properties-common

RUN add-apt-repository -y ppa:deadsnakes/ppa

RUN apt-get update

RUN apt-get -y install python3.8

RUN apt-get -y install openjdk-8-jdk

RUN apt-get -y install curl

RUN apt-get -y install zip unzip 

RUN apt-get -y install sqlite3

RUN export SDKMAN_DIR="/usr/local/sdkman" && curl -s "https://get.sdkman.io" | bash

RUN /bin/bash -c 'export SDKMAN_DIR="/usr/local/sdkman" && source /usr/local/sdkman/bin/sdkman-init.sh; sdk install kotlin'

RUN /bin/bash -c 'export SDKMAN_DIR="/usr/local/sdkman" && source /usr/local/sdkman/bin/sdkman-init.sh; sdk install gradle'

RUN useradd -m janek

USER janek

ENV KOTLIN_HOME=/usr/local/sdkman/candidates/kotlin/current/

ENV GRADLE_HOME=/usr/local/sdkman/candidates/gradle/current/

ENV PATH=$PATH:$KOTLIN_HOME/bin

ENV PATH=$PATH:$GRADLE_HOME/bin

WORKDIR /home/janek/Hello_world

RUN gradle init --type kotlin-application

COPY build.gradle.kts app/build.gradle.kts

RUN gradle build;

CMD gradle run; sleep infinity