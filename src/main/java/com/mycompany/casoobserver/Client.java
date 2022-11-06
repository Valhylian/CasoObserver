/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.casoobserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author valer
 */
public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9090;
    
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket (SERVER_IP, SERVER_PORT);
        
        BufferedReader input = new BufferedReader (new InputStreamReader(socket.getInputStream()));
        BufferedReader keyboard = new BufferedReader (new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        
        while (true){
            System.out.println("> ");
            String command = keyboard.readLine();
            
            if (command.equals ("quit")) break;
            
            out.println(command);
            
            String serverResponse = input.readLine();
            System.out.println("Server says "+ serverResponse);
            
            
        }
    }
    
}
