/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.casoobserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author valer
 */
public class ClienteHandler implements Runnable {

    private Socket client;

    public com.mycompany.casoobserver.Subasta.Subasta subasta;

    public ClienteHandler(Socket clientSocket) throws IOException {
        this.client = clientSocket;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        String received;
        try {
            
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            while (true) {

                // receive the string
                received = in.readLine();

                if (received.equals("logout")) {
                    break;
                }

                System.out.println(received);
            }

        } catch (IOException ex) {
            Logger.getLogger(ClienteHandler.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                out.close();
                in.close();
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(ClienteHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
