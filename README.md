kvdb_android_java_lib
=====================

The java library interface for kvdb android backend

The java library is built to controlling the kvdb backend development in erlang environment for accessing to the data via a single 'non-blocking' socket.

Please, ignore the src code, the precompiled jar is now built. If you need compile with another java version or environment just pull the creation of a Makefile.

add jar to project
==================

To use the library just copy the jar file under dist directory of this project to your android project (it could be made on any IDE as: NetBeans, Eclipse, etc..).

The precompiled jar is built on java version 1.6 and the default package is: com.zgbjgg.kvdb_android.


step by step: connecting and enjoying
=====================================

**KVDBSuite class**
__________

This class manages the initialization of the kvdb backend for using into an application. The class contains methods to start the beam process as a daemon in the device.
```java
            // Start KVDB backend
            KVDBSuite suite = new KVDBSuite();
            homePath = this.setHomePath(this);
            this.readAsset(homePath, this);
            suite.unzip(homePath, "");
            suite.init(homePath, false);
```
The above code snippet must be set before of all, and the methods setHomePath and readAsset must be declared into your java ```MainActivity``` class. The methods can be copied from the ```code-snippet``` file in this project. For include the kvdb android backend we recommend compile all src erlang code and compress as zip, then in your android project create a folder called ```assets``` and place the zip under this directory.

> NOTE: the method ```setHomePath``` and ```readAsset``` receives a ```MainActivity``` as a parameter, is for that reason in the example we pass ```this``` as an argument. The ```zip4j library``` must be include manually as dependencie on your project!

**KVDBSocket Class**
__________

This class manages the connection to the kvdb backend.
To connect to the kvdb android backend just creates an object from the class 'KVDBSocket' like this:
```java
	KVDBSocket conn = new KVDBSocket();
	conn.getConnection();
```
Now the object 'conn' can be used to write or read data from/to the backend.


**KVDBTasks Class**
_________

This class writes or read under the connection to the kvdb backend.
To write or read data is simply, just create an object from the class 'KVDBTasks';
```java
	KVDBTasks sentence = new KVDBTasks();
```
Now 'sentence' object can be used like this:
To write: 
```java	
	sentence.write(conn, packet);
```
To read:
```java	
	String read = sentence.read(conn);
```
In the example above, first write the 'packet' to the connection and after read the response, which one is delivered as a single string (it could be encoded as json, xml or plain text).


**KVDBPacket Class**
__________

This class creates a valid packet before send on the active connection.
To make a valid packet first creates an instance of the class 'KVDBPacket':
```java
	KVDBPacket packet = new KVDBPacket();
```
Then just use 'packet' object to encode:
```java
	String packet = packet.encode("GET", "/test", "Key");
```
The example above could get the value on any table on the backend with the key=Key.


> In the ```Sample.java``` class there is an example of how use the backend connection and the actions related to the backend as storing new data, fetching data, updating data and deleting data. The module_test in the kvdb_android project is responsible for tracking the data and the table that is used on this example is ```your_table``` is also defined on the kvdb_android project under priv directory in the file ```kvdb_backend_tables``` 


sample Main Activity
====================

The ```SampleMainActivity``` class shows how to implement the backend into an application (apk). Here can you see a screenshot of the app running in a device

![alt text](https://github.com/zgbjgg/kvdb_android_java_lib/raw/master/screenshot/screenshot.jpg "Test kvdb")


Troubleshooting
===============

Remember close the connection when you cannot use anymore, because the backend keep alive any connections and it will be out of heap or crashes come in, even in the application (apk):

```java
	conn.closeConnection();
```
If you experiment the error ```EACCESS (permission denied)``` then add the next line to your android manifest xml file:
```xml
	<uses-permission android:name="android.permission.INTERNET"/> 
```
Ping me if you need a precompiled erlang app kvdb_android for your application :)


Author
======

Jorge Garrido <zgbjgg@gmail.com>
	






