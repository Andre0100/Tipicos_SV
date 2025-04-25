/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testing;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.ComboResourceIT;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.ProductoResourceIT;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.PupaSvAppE2EIT;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.ComboBeanIT;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.OrdenBeanIT;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.ProductoBeanIT;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.TipoProductoBeanIT;

/**
 *
 * @author morales
// */
@Suite
@SelectClasses({
    ProductoBeanIT.class,
    ComboBeanIT.class,
    OrdenBeanIT.class,
    ProductoBeanIT.class,
    TipoProductoBeanIT.class,
    ProductoResourceIT.class,
    ComboResourceIT.class,
})
public class IntegrationTestSuite {
    
}
