/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.resourcesInterfaz;

import jakarta.inject.Inject;

/**
 *
 * @author morales
 */

public class ProcesadorPagos {
    @Inject
    @Paypal
    
    private ServicioPago servicioPago;
    
    public void procesarPago(double monto){
        servicioPago.pagar(monto);
    }
    
}
