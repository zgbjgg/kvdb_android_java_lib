package com.zgbjgg.kvdb_android;

/**
 * @description This class encodes a package to be sent to the kvdb android backend,
 *              as a single string.
 *              The encodes ensure that a backend receives a correct formed message
 *              for processing in a desired target.
 * 
 * @author zgbjgg
 */
public class KVDBPacket {
    
    // max method length, only allowed "POST", "PUT", "GET", "DEL"
    private static int MAX_METHOD_LEN = 4;
    
    // max request data length, is the maximum identifier for the target
    private static int MAX_REQ_LEN = 50;
    
    /**
     * 
     * @description Encodes message before send to the kvdb backend target.
     * 
     * @param method The method to be requested ("POST", "PUT", "GET", "DEL")
     * @param req The request (identifier for the backend target)
     * @param body The body of the request (as json, xml or plain text in an http request)
     * @return The encoded packet
     */
    public String encode(String method, String req, String body) {
        // Now encode method
        if (method.length() < MAX_METHOD_LEN) {
            int spaceLeft = MAX_METHOD_LEN - method.length(); 
            method = concat(method, spaceLeft, " ");
        }
        
        // As method, encode Req
        if (req.length() < MAX_REQ_LEN) {
            int spaceLeft = MAX_REQ_LEN - req.length(); 
            req = concat(req, spaceLeft, " ");
        }
        
        // Return the entire encoded message
        return method + req + body;
    }
    
    /**
     * @description Concatenates all left spaces
     * 
     * @param origin The original string to be completed by spaces
     * @param cicles The cicles to left to be completed
     * @param def The default completed string (in this case an space)
     * @return  The completed string with all left spaces filled
     */
    private String concat(String origin, int cicles, String def) {
        for (int i = 0; i < cicles; i ++){
            origin = origin.concat(def);
        }
        return origin;
    } 
    
}
