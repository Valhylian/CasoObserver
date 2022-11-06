/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.casoobserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author valer
 */
public class SendMessageThread extends Thread {

    Scanner scn;
    DataOutputStream dos;

    public SendMessageThread(Scanner scn, DataOutputStream dos) {
        super();
        this.scn = scn;
        this.dos = dos;
    }

    @Override
    public void run() {
        while (true) {

            // read the message to deliver. 
            String msg = scn.nextLine();

            try {
                // write on the output stream 
                dos.writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
