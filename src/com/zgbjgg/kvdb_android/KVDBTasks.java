
package com.zgbjgg.kvdb_android;

import java.io.IOException;

/**
 *
 * @author zgbjgg
 */
public class KVDBTasks {

    /**
     * @description Writes to KVDB BACKEND
     *
     * @param kvdbSocket
     * @param dataStream This string must be encoded by KVDBPacket for success
     * @return
     * @throws IOException
     */
    public boolean write(KVDBSocket kvdbSocket, String dataStream) throws IOException {
        if (kvdbSocket.getKvdbSocket() != null && kvdbSocket.getKvdboutputs() != null
                && kvdbSocket.getKvdbinputs() != null) {
            byte[] dataBytes = dataStream.getBytes("US-ASCII");
            kvdbSocket.getKvdboutputs().write(dataBytes);
            kvdbSocket.getKvdboutputs().flush();
            return true;
        } else {
            return false;
        }
    }
    

    /**
     * @description Reads from KVDB BACKEND
     *
     * @param kvdbSocket
     * @return
     * @throws IOException
     */
    public String read(KVDBSocket kvdbSocket) throws IOException, InterruptedException {
        Thread.sleep(1000);
        int available = kvdbSocket.getKvdbinputs().available();
        System.out.println("available:" + available);
        byte[] read = new byte[available];
        int x = 0;
        int ret = 0;
        while (x < available) {
            ret = kvdbSocket.getKvdbinputs().read();
            read[x] = (byte) ret;
            x ++;
        } // while
        String bytes = new String(read, 0, 0, read.length);
        System.out.println("erlang response: " + bytes);
        return bytes;
    }
}
