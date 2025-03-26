/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.resourcesInterfaz.ProcesadorPagos;

/**
 *
 * @author morales
 */
public class Main {
    public static void main(String args[]){
        
        try(SeContainer container = SeContainerInitializer.newInstance().initialize()){
            ProcesadorPagos procesador  = container.select(ProcesadorPagos.class).get();
            procesador.procesarPago(100.0);//Pagara con paypal
                    
        }
    }
    
}
