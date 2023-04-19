FROM eclipse-temurin:17.0.6_10-jre-alpine
ENV APP_FILE_RUNNER bootstrap-1.0-SNAPSHOT-runner.jar
ENV APP_HOME /usr/apps
EXPOSE 8080
COPY bootstrap/target/$APP_FILE_RUNNER $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE_RUNNER"]
