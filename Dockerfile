FROM openjdk:8

# Install maven
#RUN apt-get update
#RUN apt-get install -y maven

WORKDIR /expeval-rest

#ADD JAVA_HOME
#ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/bin/
ENV PATH="${JAVA_HOME}:${PATH}"

# Prepare by downloading dependencies
ADD expeval-rest/target/ .
#RUN ["mvn", "dependency:resolve"]
#RUN ["mvn", "verify"]

# compile and package into a fat jar
#RUN ["mvn", "package"]

EXPOSE 8080
CMD ["java", "-jar", "expeval-rest-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
