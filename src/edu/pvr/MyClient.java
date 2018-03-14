/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pvr;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author pvr
 */
public class MyClient {

    Socket socket = null;
    Sender sender = null;
    Receiver receiver = null;

    public MyClient(String host, int port) {
        try {
            socket = new Socket(host, port);
            sender = new Sender(socket);
            receiver = new Receiver(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SENDER SUB CLASS START
    public class Sender {

        OutputStream outputStream;
        PrintStream printStream;
        Socket sSocket;

        public Sender(Socket sSocket) {
            try {
                this.sSocket = sSocket;
                outputStream = sSocket.getOutputStream();
                printStream = new PrintStream(outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void sendText(String msg) {
            try {
                printStream.println(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    //SENDER SUB CLASS END
    //RECEIVER SUB CLASS START

    class Receiver extends Thread {

        Socket rSocket = null;
        InputStream inputStream = null;
        DataInputStream dataInputStream = null;

        public Receiver(Socket rSocket) {
            try {
                this.rSocket = rSocket;
                inputStream = rSocket.getInputStream();
                dataInputStream = new DataInputStream(inputStream);
                this.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                String msg = null;
                while (true) {
                    msg = dataInputStream.readLine();
                    if (msg == null) {
                        break;
                    }
                    if (msg.equals("exit")) {
                        break;
                    } else {
                        System.out.println("Client: " + msg);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    //RECEIVER SUB CLASS END

    public static void main(String[] args) {
        MyClient chatClient = new MyClient("localhost", 2222);
        chatClient.sender.sendText("hello");

    }
}
