# searchenginetask
-------------------
### 1. Building
To build JAR package use following command:
```
mvn clean package
```

### 2. Usage
Application provides its own help information as a console output, like so:
```
usage: simplesearchengine
-d,--document <arg>   Document to be indexed
-q,--query <arg>      Term that will be searched
-t,--test             Loads test documents
```
To run the application with default documents provided along with the task use the following:
```
java -jar searchenginetask-0.1.0.jar -t -q brown
```
You can also provide your own txt files as follows:
```
java -jar searchenginetask-0.1.0.jar -d ~/document1.txt -d ~/document2.txt -q fox
```