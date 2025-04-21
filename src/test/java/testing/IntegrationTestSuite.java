/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testing;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.ProductoResourceIT;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.OrdenBeanIT;

/**
 *
 * @author morales
// */
@Suite
@SelectClasses({
    OrdenBeanIT.class,     // Se ejecuta primero
    ProductoResourceIT.class  // Se ejecuta después
})
public class IntegrationTestSuite {
    
}
