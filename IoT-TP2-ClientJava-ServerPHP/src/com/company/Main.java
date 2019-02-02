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
                String choixVerbeHttp = sc.nextLine();
                switch (choixVerbeHttp) {
                    case "1":
                        get(socket, nom, prenom);
                        break;
                    case "2":
                        post(socket, nom, prenom);
                        break;
                }
                break;
            case "2":
                // INPUT VALUES A/B 
                System.out.println("a = ");
                int a = sc.nextInt();
                System.out.println("b = ");
                int b = sc.nextInt();

                break;
        }

        socket.close();
    }

    private static void get(Socket sock, String nom, String prenom) throws IOException {
        String request = "GET /?nom=" + nom + "&prenom=" +prenom + "\r\nHost: localhost\r\n\r\n";
        StringBuilder content = new StringBuilder();

        BufferedOutputStream bos = new BufferedOutputStream(sock.getOutputStream());
        bos.write(request.getBytes());
        bos.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            content.append(inputLine).append("\n");
        }
        System.out.println(content);
    }

    private static void post(Socket sock, String nom, String prenom) throws IOException {
        //TODO remove headers
        String data = URLEncoder.encode("nom", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(nom, StandardCharsets.UTF_8) + "&";
        data += URLEncoder.encode("prenom", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(prenom, StandardCharsets.UTF_8);

        /*String request = "POST / HTTP/1.1\r\n" +
                "Content-Length: " + data.length() +
                "\r\nContent-Type: application/x-www-form-urlencoded\r\n\r\n" +
                data;
        BufferedOutputStream bos = new BufferedOutputStream(sock.getOutputStream());
        bos.write(request.getBytes());
        bos.flush();*/

        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), StandardCharsets.UTF_8));
        wr.write("POST / HTTP/1.0\r\n");
        wr.write("Content-Length: " + data.length() + "\r\n");
        wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
        wr.write("\r\n");
        wr.write(data);
        wr.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        wr.close();
        reader.close();
    }
}
