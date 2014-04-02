package com.zgbjgg.kvdb_android.suite;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * @description This class manages the startup of erlang runtime environment and
 * initializes the database as you need. The kvdb android backend is started in
 * a beam process and can be managed by this library for connecting and actions
 * over database.
 *
 * @author zgbjgg
 */
public class KVDBSuite {

    // The zip file name for the erlang app.
    public static String KVDB_ANDROID_ZIP = "kvdb_android.zip";
    
    // The application directory (no xip file).
    private String KVDB_ANDROID = "kvdb_android";
    
    // The script tool for the application
    private String SCRIPT_TOOL = "kvdb-android-tool";
    
    // Commands for linux arm android
    // root access
    private static String SU = "su";
    
    // option to execute command
    private static String MINUS_C = "-c";
    
    // /system/bin/sh
    private static String SH = "sh";
    
    // scriptizer
    private String SCRIPT = File.separator + KVDB_ANDROID + File.separator + SCRIPT_TOOL + " ";
    
    // actions on script 
    private String COMPILE = " compile";
    private String START = " start";

    /**
     * @description Unzip the kvdb backend app erlang
     *
     * @param homePath
     * @param password
     * @return
     * @throws ZipException
     */
    public boolean unzip(String homePath, String password) throws ZipException {
        String src = homePath + File.separator + KVDB_ANDROID_ZIP;
        String dst = homePath + File.separator;

        ZipFile zipFile = new ZipFile(src);
        if (zipFile.isEncrypted()) {
            zipFile.setPassword(password);
        }
        zipFile.extractAll(dst);

        return true;
    }

    /**
     * Initializes the kvdb backend for using on the local host (in
     * this case the device)
     *
     * @param homePath The $HOME variable for the system operative via the
     * application
     * @param isCompiled If this value is true then compile all source code
     * before start erlang app, otherwise ignore (you must include only the
     * precompiled app)
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean init(String homePath, final boolean isCompiled) throws IOException, InterruptedException {

        String pwd = homePath + File.separator + KVDB_ANDROID;
        final String compile;
        final String start;
        start = SH + " " + homePath + SCRIPT + pwd + START;
        compile = SH + " " + homePath + SCRIPT + pwd + COMPILE;

        /*Thread t;
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                */try {
                    if (isCompiled) {
                        execute(compile);
                    }

                    // execute start
                    execute(start);
                } catch (IOException ex) {
                    Logger.getLogger(KVDBSuite.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(KVDBSuite.class.getName()).log(Level.SEVERE, null, ex);
                }
            /*()}
        });

        // start the thread
        t.start();

        // wait until finished
        t.join();*/

        return true;

    }

    /**
     * @description Executes a command in a separate thread and wait for the
     * process until this exit
     * @param cmd The command to execute as superuser (root)
     * @throws IOException
     * @throws InterruptedException
     */
    private void execute(String cmd) throws IOException, InterruptedException {
        Process proc = Runtime.getRuntime().exec(new String[]{SU, MINUS_C, cmd});
        proc.waitFor();
        
        // This is a bad practice, because we must wait automatically the time until
        // command is read, however this can be fix the issue.
        Thread.sleep(10000);
    }
}
