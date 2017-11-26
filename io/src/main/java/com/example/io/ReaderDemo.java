package com.example.io;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import static com.example.io.Constants.FILE_PATH;

/**
 * Created by yueweiwei on 25/11/2017.
 */

public class ReaderDemo {

    public static void main(String[] args) {
        ReaderDemo demo = new ReaderDemo();
        demo.fileReader();
        demo.inputStreamReader();
        demo.stringReader();
    }

    private void fileReader() {
        try {
            Reader reader = new FileReader(FILE_PATH);
            int ch;
            while ((ch = reader.read()) != -1) {
                System.out.print((char) ch);
            }
            reader.close();

            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inputStreamReader() {
        try {
            InputStream is = new FileInputStream(FILE_PATH);
            Reader reader = new InputStreamReader(is);
            int ch;
            while ((ch = reader.read()) != -1) {
                System.out.print((char) ch);
            }
            reader.close();
            is.close();

            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stringReader() {
        try {
            Reader reader = new StringReader("The content of this file.");
            int ch;
            while ((ch = reader.read()) != -1) {
                System.out.print((char) ch);
            }
            reader.close();

            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
