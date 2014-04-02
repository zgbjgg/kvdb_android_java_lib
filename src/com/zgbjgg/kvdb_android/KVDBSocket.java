package com.zgbjgg.kvdb_android;

import java.io.*;
import java.net.*;

/**
 * @description This class manages the socket connection to the kvdb backend database,
 *              and can write or read results via streams packets.
 *
 * @author zgbjgg
 *
 */
public class KVDBSocket {

    // default socket connection to localhost 
    public static String KVDB_HOST = "localhost";
    
    // default port 2187
    public static int KVDB_PORT = 2187;
    
    // initializes with null all stream and socket instances
    private Socket kvdbSocket = null;
    private DataOutputStream kvdboutputs = null;
    private DataInputStream kvdbinputs = null;

    
    /**
     * @description Get a valid connection to the kvdb backend databse
     * 
     * @throws UnknownHostException
     * @throws IOException 
     */
    public void getConnection() throws UnknownHostException, IOException {
        this.kvdbSocket = new Socket(KVDB_HOST, KVDB_PORT);
        kvdboutputs = new DataOutputStream(this.kvdbSocket.getOutputStream());
        kvdbinputs = new DataInputStream(this.kvdbSocket.getInputStream());
    }
    
    
    /**
     * @description Close a valid connection to kvdb backend
     * 
     * @throws IOException 
     */
    public void closeConnection() throws IOException {
        this.getKvdbinputs().close();
        this.getKvdboutputs().close();
        this.getKvdbSocket().close();       
    }

    /**
     * @description Get a socket instance conected to the kvdb android backend
     * 
     * @return the kvdbSocket
     */
    public Socket getKvdbSocket() {
        return kvdbSocket;
    }


    /**
     * @description Get a read instance conected to the kvdb android backend
     * 
     * @return the kvdboutputs
     */
    public DataOutputStream getKvdboutputs() {
        return kvdboutputs;
    }

    /**
     * @description Get a write instance conected to the kvdb android backend
     * 
     * @return the kvdbinputs
     */
    public DataInputStream getKvdbinputs() {
        return kvdbinputs;
    }
    
}
