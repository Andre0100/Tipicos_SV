/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.resourcesInterfaz;

import jakarta.enterprise.context.ApplicationScoped;

/**
 *
 * @author morales
 */
@Tarjeta
@ApplicationScoped
public class ServicioPagoTarjeta implements ServicioPago{

    @Override
    public void pagar(double monto) {
        System.out.println("Pagando con tarjeta: "+monto);
    }
    
}
