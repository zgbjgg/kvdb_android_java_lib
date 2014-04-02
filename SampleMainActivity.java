package com.zgbjgg.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.zgbjgg.kvdb_android.KVDBPacket;
import com.zgbjgg.kvdb_android.KVDBSocket;
import com.zgbjgg.kvdb_android.KVDBTasks;
import com.zgbjgg.kvdb_android.suite.KVDBSuite;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.logging.Logger;
import net.lingala.zip4j.exception.ZipException;

public class MainActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Test kvdb");
        try {
            super.onCreate(savedInstanceState);
            
            String homePath;

            // Start KVDB backend
            KVDBSuite suite = new KVDBSuite();
            homePath = this.setHomePath(this);
            this.readAsset(homePath, this);
            suite.unzip(homePath, "");
            suite.init(homePath, false);
            
            
            KVDBSocket conn = new KVDBSocket();
            conn.getConnection();
            KVDBTasks sentence = new KVDBTasks();

            // Build packet
            KVDBPacket pack = new KVDBPacket();

            // Storing output
            String output = "";
            String read;

            // Save data into your_table
            String put = pack.encode("POST", "/test", "my_key,milk,eggs,bacon");
            sentence.write(conn, put);
            read = sentence.read(conn);
            output += "Saving data: " + read + "\n";

            // Retrieve data before stored, get data1 attribute
            String get1 = pack.encode("GET", "/test", "data2,yourkey,my_key");
            sentence.write(conn, get1);
            read = sentence.read(conn);
            output += "Retrieving attribute data1: " + read + "\n";

            // Update data1 attribute for 'bread'
            String update = pack.encode("PUT", "/test", "data1,bread,yourkey,my_key");
            sentence.write(conn, update);
            read = sentence.read(conn);
            output += "Updating data: " + read + "\n";

            // Retrieve again data1 attribute using get1
            sentence.write(conn, get1);
            read = sentence.read(conn);
            output += "Retrieving attribute data1 again: " + read + "\n";

            // Finally delete data and get again for checking notfound
            String delete = pack.encode("DEL", "/test", "your_table,my_key");
            sentence.write(conn, delete);
            read = sentence.read(conn);
            output += "Deleting all data: " + read + "\n";

            sentence.write(conn, get1);
            read = sentence.read(conn);
            output += "Checking erasing of data: " + read + "\n";

            conn.closeConnection();

            alert.setMessage(output);

        } catch (UnknownHostException ex) {
            alert.setMessage(ex.getMessage());
        } catch (IOException ex) {
            alert.setMessage(ex.getMessage());
        } catch (InterruptedException ex) {
            alert.setMessage(ex.getMessage());
        } catch (ZipException ex) {
            alert.setMessage(ex.getMessage());
        }

        alert.show();

    }

    public String setHomePath(MainActivity main) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(main);
        SharedPreferences.Editor editor = mPrefs.edit();
        String defValue = main.getDir("HOME", MainActivity.MODE_PRIVATE).getAbsolutePath();
        String homePath = mPrefs.getString("home_path", defValue);
        editor.putString("home_path", homePath);
        editor.commit();
        return homePath;
    }

    public void readAsset(String homePath, MainActivity main) throws IOException {
        File f = new File(homePath + File.separator + KVDBSuite.KVDB_ANDROID_ZIP);
        InputStream is = main.getAssets().open(KVDBSuite.KVDB_ANDROID_ZIP);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(buffer);
        fos.close();

    }
}

