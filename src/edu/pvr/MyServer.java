package edu.pvr;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author pvr
 */
public class MyServer implements Runnable {

    ServerSocket serverSocket = null;
    Socket socket = null;

    public MyServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started at " + port);
            new Thread(this).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Client connected");
                new MyChild(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class MyChild extends Thread {

        Socket s = null;
        BufferedReader bufferedReader = null;
        PrintStream printStream = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        public MyChild(Socket s) {
            try {
                this.s = s;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void run() {
            try {
                inputStream = s.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                outputStream = s.getOutputStream();
                printStream = new PrintStream(outputStream);
                String msg = "";
//                msg = br.readLine();
                while (true) {
                    msg = bufferedReader.readLine();
                    System.out.println("Server: "+msg);
                    if (msg == null) {
                        break;
                    }
                    if (msg.equals("exit")) {
                        break;
                    } else {
                        printStream.println(msg);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        MyServer myServer = new MyServer(2222);
    }
}
