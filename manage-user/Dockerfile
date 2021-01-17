FROM openjdk:8-jdk-slim
MAINTAINER Nhat Nam <nguyentrungnhatnam@gmail.com>
ADD target/manage-user-1.0-SNAPSHOT.jar manage-user.jar
ENTRYPOINT ["java", "-jar", "/manage-user.jar"]
EXPOSE 8001