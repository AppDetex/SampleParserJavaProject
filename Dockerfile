FROM openjdk:7

RUN apt-get update && \
apt-get install -y build-essential && \
apt-get install -y maven && \
curl -o /usr/local/bin/jq http://stedolan.github.io/jq/download/linux64/jq && \
chmod +x /usr/local/bin/jq
