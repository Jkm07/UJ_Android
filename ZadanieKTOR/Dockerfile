FROM ubuntu:20.04

RUN apt update

RUN apt -y install openjdk-17-jdk

RUN apt -y install curl

RUN curl -s https://ngrok-agent.s3.amazonaws.com/ngrok.asc | tee /etc/apt/trusted.gpg.d/ngrok.asc >/dev/null && echo "deb https://ngrok-agent.s3.amazonaws.com buster main" | tee /etc/apt/sources.list.d/ngrok.list && apt update && apt install ngrok

WORKDIR /ZadanieKTOR

CMD ./gradlew run;