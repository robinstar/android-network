package com.example.network;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yueweiwei on 25/11/2017.
 */

public class JavaNetPackageDemo {

    private static final String URL = "https://www.baidu.com";
    private static final String SAVED_FILE = "app/index.html";

    public static void main(String[] args) {
        JavaNetPackageDemo demo = new JavaNetPackageDemo();

        try {
            URL url = new URL(URL);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            demo.printResponse(bis);
            bis.mark(0);
            bis.reset();
            demo.saveResponse(bis);

            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void printResponse(InputStream is) throws IOException {
        Reader reader = new InputStreamReader(is);
        char[] buffer = new char[1024];
        int len;

        while ((len = reader.read(buffer)) != -1) {
            printBuffer(buffer, len);
        }
    }

    private void printBuffer(char[] buffer, int len) {
        for (int i = 0; i < len; i++) {
            System.out.print(buffer[i]);
        }
    }

    private void saveResponse(InputStream is) throws IOException {
        OutputStream os = new FileOutputStream(SAVED_FILE);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.close();
    }
}
