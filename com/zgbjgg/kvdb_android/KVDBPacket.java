
package com.zgbjgg.kvdb_android;

/**
 *
 * @author zgbjgg
 */
public class KVDBPacket {
    
    private static int MAX_METHOD_LEN = 4;
    private static int MAX_REQ_LEN = 50;
    
    /**
     * 
     * @description Encodes message for send to kvdb backend
     * 
     * @param method
     * @param req
     * @param body
     * @return 
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
     * @param origin
     * @param cicles
     * @param def
     * @return 
     */
    private String concat(String origin, int cicles, String def) {
        for (int i = 0; i < cicles; i ++){
            origin = origin.concat(def);
        }
        return origin;
    } 
    
}
