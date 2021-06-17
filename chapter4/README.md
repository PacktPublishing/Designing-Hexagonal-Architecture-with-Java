# Chapter 5 - The Life Cycle of Driving and Driven Operations
Following are the instructions to compile and run the code samples.

You need to run these commands from within the chapter 4 directory:

**To compile**
```
mvn clean package
```

**To test the REST adapter**
```
java -jar target/chapter4-1.0-SNAPSHOT-jar-with-dependencies.jar rest
curl -vv "http://localhost:8080/network/add?routerId=ca23800e-9b5a-11eb-a8b3-0242ac130003&address=40.0.0.0&name=Finance&cidr=8"
```

**To test the CLI adapter**
```
java -jar target/chapter4-1.0-SNAPSHOT-jar-with-dependencies.jar
```
