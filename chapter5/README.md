# Chapter 5 - The Nature of Driving and Driven Operations
Following are the instructions to compile and run the code samples.

Before running this application, be sure to download, extract and run Kafka:
```
tar -xzf kafka_2.13-2.8.0.tgz
cd kafka_2.13-2.8.0
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```

You need to run these commands from within the chapter 5 directory:

**To compile**
```
mvn clean package
```

**To test the REST adapter with WebSocket**
```
java -jar target/topology-inventory-1.0-SNAPSHOT-jar-with-dependencies.jar rest
curl -vv "http://localhost:8080/network/add?routerId=ca23800e-9b5a-11eb-a8b3-0242ac130003&address=40.0.0.0&name=Finance&cidr=8"
```

**To test the CLI adapter**
```
java -jar target/topology-inventory-1.0-SNAPSHOT-jar-with-dependencies.jar
```
To run the front-and application just open the index.html from the web directory.