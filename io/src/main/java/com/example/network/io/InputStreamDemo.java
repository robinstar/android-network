package com.example.network.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.example.network.io.Constants.FILE_PATH;

class InputStreamDemo {

    public static void main(String[] arg) {
        InputStreamDemo demo = new InputStreamDemo();
        InputStream is;

        try {
            is = new FileInputStream(FILE_PATH);
            demo.readByByte(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println();
        }

        try {
            is = new FileInputStream(FILE_PATH);
            demo.readFromBuffer(is, 8);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println();
        }

        try {
            is = new FileInputStream(FILE_PATH);
            demo.readFromBuffer(is, 8, 2, 3);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void readByByte(InputStream is) throws IOException {
        System.out.println("InputStreamDemo.readByByte");

        int r;
        while ((r = is.read()) != -1) {
            System.out.println((char) r);
        }

        System.out.println("InputStreamDemo.readByByte end");
    }


    private void readFromBuffer(InputStream is, int bufferSize) throws IOException {
        System.out.println("InputStreamDemo.readFromBuffer, bufferSize = [" + bufferSize + "]");

        checkBufferSize(bufferSize);

        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = is.read(buffer)) != -1) {
            printBuffer(buffer, len);
        }

        System.out.println("InputStreamDemo.readFromBuffer end");
    }

    private void printBuffer(byte[] buffer, int len) {
        System.out.println("InputStreamDemo.printBuffer, buffer len = [" + len + "]");
        for (int i = 0; i < len; i++) {
            System.out.print((char) buffer[i]);
        }
        System.out.println();
    }


    private void readFromBuffer(InputStream is, int bufferSize, int offset, int count) throws IOException {
        System.out.println("InputStreamDemo.readFromBuffer, bufferSize = [" + bufferSize + "], offset = [" + offset + "], count = [" + count + "]");

        checkBufferSize(bufferSize);

        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = is.read(buffer, offset, count)) != -1) {
            printBuffer(buffer, offset, len);
        }

        System.out.println("InputStreamDemo.readFromBuffer end");
    }

    private void printBuffer(byte[] buffer, int offset, int len) {
        System.out.println("InputStreamDemo.printBuffer, offset = [" + offset + "], len = [" + len + "]");
        for (int i = offset; i < offset + len; i++) {
            System.out.print((char) buffer[i]);
        }
        System.out.println();
    }


    private void checkBufferSize(int size) {
        if (size < 0) {
            throw new RuntimeException("buffer size [" + size + "] must bigger than 0");
        }
    }
}
