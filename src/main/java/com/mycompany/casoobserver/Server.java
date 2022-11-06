/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.casoobserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**s
 *
 * @author valer
 */
public class Server {
    
    private static final int PORT = 9090;
    public static ArrayList<ClienteHandler> clients = new ArrayList<ClienteHandler>();
    public static ArrayList<Observable> observables = new ArrayList<Observable>(); //sirve para subastas y perfiles
    
    public static void main(String[] args) throws IOException {
        // server is listening on port 1234 
        ServerSocket ss = new ServerSocket(9090); 
        ss.setReuseAddress(true); 
        
        // running infinite loop for getting client request 
        while (true){
            // Accept the incoming request 
            Socket s = ss.accept(); 
            System.out.println("New client request received : " + s); 
            // obtain input and output streams 
            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
			
            System.out.println("Creating a new handler for this client..."); 
            ClienteHandler clientThread = new ClienteHandler (s);
            // Create a new Thread with this object. 
            Thread t = new Thread(clientThread); 
            
            System.out.println("Adding this client to active client list"); 

	    // add this client to active clients list 
	    clients.add(clientThread);

	    // start the thread. 
	    t.start(); 
        }
        
    }
    
}
