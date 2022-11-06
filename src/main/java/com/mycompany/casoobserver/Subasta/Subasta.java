/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.casoobserver.Subasta;

/**
 *
 * @author valer
 */
public class Subasta extends com.mycompany.casoobserver.Observable{
    public String subastador;
    public String producto;
    public int lastOfert;
    
    public Subasta (String subastador, String producto,int lastOfert ){
        super();
        this.subastador = subastador;
        this.producto = producto;
        this.lastOfert = lastOfert;
    }
    
    @Override 
    public void notifyAllObservers(String command, Object source){}
}
