package sample;

import com.zgbjgg.kvdb_android.KVDBPacket;
import com.zgbjgg.kvdb_android.KVDBSocket;
import com.zgbjgg.kvdb_android.KVDBTasks;

/**
 *
 * @author zgbjgg
 */
public class Sample {


    public static void main(String[] args) {
        try {
            Sample sample = new Sample();
            sample.use_kvdb_android();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void use_kvdb_android() {
        try {
            KVDBSocket conn = new KVDBSocket();
            conn.getConnection();
            KVDBTasks sentence = new KVDBTasks();
            
            // Build packet
            KVDBPacket pack = new KVDBPacket();
           
            // Storing output
            String read;
            
            // Save data into your_table
            String put = pack.encode("POST", "/test", "my_key,milk,eggs,bacon");
            sentence.write(conn, put);
            read = sentence.read(conn);
            System.out.println("Saving data: " + read + "\n");
                       
            // Retrieve data before stored, get data1 attribute
            String get1 = pack.encode("GET", "/test", "data2,yourkey,my_key");
            sentence.write(conn, get1);
            read = sentence.read(conn);
            System.out.println("Retrieving attribute data1: " + read + "\n");
            
            // Update data1 attribute for 'bread'
            String update = pack.encode("PUT", "/test", "data1,bread,yourkey,my_key");
            sentence.write(conn, update);
            read = sentence.read(conn);
            System.out.println("Updating data: " + read + "\n");
            
            // Retrieve again data1 attribute using get1
            sentence.write(conn, get1);
            read = sentence.read(conn);
            System.out.println("Retrieving attribute data1 again: " + read + "\n");
            
            // Finally delete data and get again for checking notfound
            String delete = pack.encode("DEL", "/test", "your_table,my_key");
            sentence.write(conn, delete);
            read = sentence.read(conn);
            System.out.println("Deleting all data: " + read + "\n");
            
            sentence.write(conn, get1);
            read = sentence.read(conn);
            System.out.println("Checking erasing of data: " + read + "\n");

            conn.closeConnection();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } 
    }
}
