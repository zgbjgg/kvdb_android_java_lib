package com.zgbjgg.kvdb_android;

import java.io.IOException;

/**
 * @description This class execute the tasks on the desired connection, the tasks
 *              are the basics: write and read. Each task is managed with only one 
 *              running stream of data.
 *
 * @author zgbjgg
 */
public class KVDBTasks {

    /**
     * @description Writes to KVDB BACKEND
     *
     * @param kvdbSocket The socket connected to the kvdb backend database
     * @param dataStream This string must be encoded by KVDBPacket for success
     * @return true if write operation is success, false otherwise.
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
     * @param kvdbSocket The socket connected to the kvdb backend database
     * @return The return statement of the backend database as data stream.
     * @throws IOException
     */
    public String read(KVDBSocket kvdbSocket) throws IOException, InterruptedException {
        Thread.sleep(1000);
        int available = kvdbSocket.getKvdbinputs().available();
        byte[] read = new byte[available];
        int x = 0;
        int ret;
        while (x < available) {
            ret = kvdbSocket.getKvdbinputs().read();
            read[x] = (byte) ret;
            x ++;
        } // while
        String bytes = new String(read, 0, 0, read.length);
        return bytes;
    }
}
