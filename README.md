kvdb_android_java_lib
=====================

The java library interface for kvdb android backend

The java library is built to controlling the kvdb backend development in erlang environment for accessing to the data via a single 'non-blocking' socket.

Please, ignore the src code, the precompiled jar is now built. If you need compile with another java version or environment just pull the creation of a Makefile.

Add jar to project
==================

To use the library just copy the jar file under dist directory of this project to your android project (it could be made on any IDE as: NetBeans, Eclipse, etc..).

The precompiled jar is built on java version 1.6 and the default package is: com.zgbjgg.kvdb_android.


Step by step: connecting and enjoying
=====================================

KVDBSocket
__________

This class manages the connection to the kvdb backend.
To connect to the kvdb android backend just creates an object from the class 'KVDBSocket' like this:

	KVDBSocket conn = new KVDBSocket();
	conn.getConnection();
  
Now the object 'conn' can be used to write or read data from/to the backend.


KVDBTasks
_________

This class writes or read under the connection to the kvdb backend.
To write or read data is simply, just create an object from the class 'KVDBTasks';

	KVDBTasks sentence = new KVDBTasks();
	
Now 'sentence' object can be used like this:
To write: 
	
	sentence.write(conn, packet);
	
To read:
	
	String read = sentence.read(conn);

In the example above, first write the 'packet' to the connection and after read the response, which one is delivered as a single string (it could be encoded as json, xml or plain text).


KVDBPacket
__________

This class creates a valid packet before send on the active connection.
To make a valid packet first creates an instance of the class 'KVDBPacket':

	KVDBPacket pack = new KVDBPacket();
	
Then just use 'packet' object to encode:

	String packet = pack.encode("GET", "/test", "Key");
	
The example above could get the value on any table on the backend with the key=Key.


Troubleshooting
===============

Remember close the connection when you cannot use anymore, because the backend keep alive any connections and it will be out of heap or crashes come in, even in the application (apk):

	conn.closeConnection();
	

Author
======

Jorge Garrido <zgbjgg@gmail.com>
	






