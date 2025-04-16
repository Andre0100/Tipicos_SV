/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.OrdenResource; 
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.OrdenBean; 
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden; 
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.OrdenBean;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;

/**
 *
 * @author andrea
 */
public class OrdenResourceTest {
    /**
    // LE VOY A DECIR UNA LISTA, QUE CUANDO LA VAYA A BUSCAR ME LA DEVUELVA 
    List<Orden> LISTA_ORDEN=Arrays.asList(new Orden[]{ 
        new Orden(1l),
        new Orden(2l), 
        new Orden(3l) 
            });
    
    // Este código es una prueba unitaria para la clase OrdenResource, que expone un endpoint REST en la aplicación. 
    // Se utiliza JUnit 5 para las aserciones y Mockito para simular (mockear) la dependencia OrdenBean.
    
    @Test
    public void testFindRange(){ 
        System.out.println("findRange");
        int first=0;
        int pageSize=50; 
        // NECESITO UN RECUERSO, UN ENDPOINT DE REST; CREAR 
        OrdenResource cut = new OrdenResource(); // se instancia la clase OrdenResource, que es lo que estamos probando 
        // voy hacer mock de oBean 
        OrdenBean mockordenBean = Mockito.mock(OrdenBean.class); 
        Mockito.when(mockordenBean.findRange(first, pageSize)).thenReturn(LISTA_ORDEN); //sea llamado, devolverá LISTA_ORDEN.
        Mockito.when(mockordenBean.count()).thenReturn(3l); 
        cut.ordenBean = mockordenBean; //
        Response resultado = cut.findRange(first, pageSize);  // el resultado deberia de ser un 200 
        
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(200,resultado.getStatus()); 
        
        Assertions.fail(" ESTA PRUEBA NO PASA "); 
    }
**/
}
