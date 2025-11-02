package org.example.config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void serverConfig() throws IOException {
        ServerSocket s = new ServerSocket(1234);
        byte b[] = new byte[100];

        while (true) {
            Socket c = s.accept();
            System.out.println("Client connected");
            c.getInputStream().read(b);
            System.out.println();

            c.close();
        }
    }

}
