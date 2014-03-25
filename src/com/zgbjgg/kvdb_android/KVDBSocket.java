package com.zgbjgg.kvdb_android;

import java.io.*;
import java.net.*;

/**
 *
 * @author zgbjgg
 *
 * @description This class manages the socket connection to KVDB BACKEND.
 */
public class KVDBSocket {

    public static String KVDB_HOST = "localhost";
    public static int KVDB_PORT = 2187;
    
    private Socket kvdbSocket = null;
    private DataOutputStream kvdboutputs = null;
    private DataInputStream kvdbinputs = null;

    
    /**
     * @description Get a valid connection to kvdb backend
     * 
     * @throws UnknownHostException
     * @throws IOException 
     */
    public void getConnection() throws UnknownHostException, IOException {
        setKvdbSocket(new Socket(KVDB_HOST, KVDB_PORT));
        kvdboutputs = new DataOutputStream(getKvdbSocket().getOutputStream());
        kvdbinputs = new DataInputStream(getKvdbSocket().getInputStream());
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
     * @return the kvdbSocket
     */
    public Socket getKvdbSocket() {
        return kvdbSocket;
    }

    /**
     * @param kvdbSocket the kvdbSocket to set
     */
    public void setKvdbSocket(Socket kvdbSocket) {
        this.kvdbSocket = kvdbSocket;
    }

    /**
     * @return the kvdboutputs
     */
    public DataOutputStream getKvdboutputs() {
        return kvdboutputs;
    }

    /**
     * @return the kvdbinputs
     */
    public DataInputStream getKvdbinputs() {
        return kvdbinputs;
    }
    
}
