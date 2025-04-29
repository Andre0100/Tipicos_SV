/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testing;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.integrationTests.ComboResourceIT;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.integrationTests.ProductoResourceIT;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.integrationTests.*;

/**
 *
 * @author morales
// */
@Suite
@SelectClasses({
    TipoProductoBeanIT.class,
    ProductoBeanIT.class,
    ComboBeanIT.class,
    OrdenBeanIT.class,
    ProductoResourceIT.class,
    ComboResourceIT.class,
        PagoBeanIT.class,
})
public class IntegrationTestSuite {
    
}
