# Chapter 15 - Good Designing Practices for your Hexagonal Application
Following are the instructions to test the code samples.

You need to run these commands from within the chapter 15 directory:

**To generate a Docker image with Uber Jar**
```
$ mvn clean package
$ docker build . -t topology-inventory
```
**To generate a Docker image with native executable**
```
$ mvn clean package -Pnative -Dquarkus.native.container-build=true -Dnative-image.xmx=6g
$ docker build . -t topology-inventory-native -f Dockerfile-native
```
**To start the container with Uber Jar**
```
docker run -p 5555:8080 topology-inventory
```
**To start the container with native executable**
```
docker run -p 5555:8080 topology-inventory-native
```
**To access the Dockerized application**
```
http://localhost:5555/q/swagger-ui/
```