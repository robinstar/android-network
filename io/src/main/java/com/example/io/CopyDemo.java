package com.example.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.example.io.Constants.FILE_COPY_PATH;
import static com.example.io.Constants.FILE_PATH;

/**
 * Created by yueweiwei on 25/11/2017.
 */

public class CopyDemo {

    public static void main(String[] args) {
        CopyDemo demo = new CopyDemo();

        try {
            demo.copy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copy() throws IOException {
        InputStream is = new FileInputStream(FILE_PATH);
        OutputStream os = new FileOutputStream(FILE_COPY_PATH);

        int r;
        while ((r = is.read()) != -1) {
            os.write(r);
        }

        os.close();
        is.close();
    }
}
