package com.company;

import java.io.*;
import java.net.Socket;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        // INPUT MENU 
        System.out.println("1 : fonction \'Bonjour, ...\' -->");
        System.out.println("2 : fonction \'Calcul a+b=?\' -->");
        String choix = sc.nextLine();

        Socket socket = new Socket("localhost", 80);

        switch (choix){
            case "1":
                //INPUT VALUES NOM/PRENOM
                System.out.print("Nom: ");
                String nom = sc.nextLine();
                System.out.print("Prénom: ");
                String prenom = sc.nextLine();

                System.out.println("1 \'GET\'  -->");
                System.out.println("2 \'POST\' -->");
                String choixVerbeHttpHello = sc.nextLine();
                switch (choixVerbeHttpHello) {
                    case "1":
                        getHello(socket, nom, prenom);
                        break;
                    case "2":
                        postHello(socket, nom, prenom);
                        break;
                }
                break;
            case "2":
                // INPUT VALUES A/B 
                System.out.println("a = ");
                String a = sc.nextLine();
                System.out.println("b = ");
                String b = sc.nextLine();

                System.out.println("1 \'GET\'  -->");
                System.out.println("2 \'POST\' -->");
                String choixVerbeHttpNumbers = sc.nextLine();
                switch (choixVerbeHttpNumbers) {
                    case "1":
                        getNumbers(socket, a, b);
                        break;
                    case "2":
                        postNumbers(socket, a, b);
                        break;
                }
                break;
        }

        socket.close();
    }

    private static void getHello(Socket sock, String nom, String prenom) throws IOException {
        String request = "GET /?nom=" + nom + "&prenom=" +prenom + "\r\nHost: localhost\r\n\r\n";
        sendGetRequest(sock, request);
    }

    private static void postHello(Socket sock, String nom, String prenom) throws IOException {
        //TODO remove headers
        String data = URLEncoder.encode("nom", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(nom, StandardCharsets.UTF_8) + "&";
        data += URLEncoder.encode("prenom", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(prenom, StandardCharsets.UTF_8);

        sendPostRequest(sock, data);
    }

    private static  void getNumbers(Socket socket, String a, String b) throws IOException {
        String request = "GET /?a=" + a + "&b=" + b + "\r\nHost: localhost\r\n\r\n";
        sendGetRequest(socket, request);
    }

    private static  void postNumbers(Socket socket, String a, String b) throws IOException {
        String data = URLEncoder.encode("a", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(a, StandardCharsets.UTF_8) + "&";
        data += URLEncoder.encode("b", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(b, StandardCharsets.UTF_8);

        sendPostRequest(socket, data);
    }

    private static void sendGetRequest(Socket socket, String request) throws IOException {
        StringBuilder content = new StringBuilder();

        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        bos.write(request.getBytes());
        bos.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            content.append(inputLine).append("\n");
        }
        System.out.println(content);
    }

    private static void sendPostRequest(Socket socket, String data) throws IOException {
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        wr.write("POST / HTTP/1.0\r\n");
        wr.write("Content-Length: " + data.length() + "\r\n");
        wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
        wr.write("\r\n");
        wr.write(data);
        wr.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        wr.close();
        reader.close();
    }
}
