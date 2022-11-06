/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.casoobserver;

/**
 *
 * @author valer
 */
public interface IObservable {
    public void notifyAllObservers(String command, Object source);   
}
