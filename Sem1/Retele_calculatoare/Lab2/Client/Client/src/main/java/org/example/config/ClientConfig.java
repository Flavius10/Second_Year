package org.example.config;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConfig {

    public void initClientServer() throws IOException {

        String serverIp = "127.0.0.1";
        int port = 8080;

        try(
            Socket socket = new Socket(serverIp, port);

            DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(socket.getInputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ){
            System.out.println("Conectat la serverul C pe portul " + port);

            System.out.println("Dati un string de trimis: ");
            String userInput = br.readLine();

            outToServer.writeBytes(userInput);
            outToServer.write(0);
            outToServer.flush();

            System.out.println("String trimis catre server: " + userInput);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte b;

            while((b = inFromServer.readByte()) != 0) {
                baos.write(b);
            }

            String reversedString = baos.toString("UTF-8");

            System.out.println("String trimis de la server: " + reversedString);
        } catch (UnknownHostException e) {
            System.err.println("Serverul nu a putut fi gasit!");
        } catch (IOException e) {
            System.err.println("Serverul nu a putut fi conectat!");
        }

    }

}
