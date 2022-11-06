/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.casoobserver;

import java.util.ArrayList;

/**
 *
 * @author valer
 */
public abstract class Observable {
    public ArrayList <ClienteHandler> asociados;
    
    public Observable (){
        this.asociados = new ArrayList<>();
    } 
    public abstract void notifyAllObservers(String command, Object source);   
}
