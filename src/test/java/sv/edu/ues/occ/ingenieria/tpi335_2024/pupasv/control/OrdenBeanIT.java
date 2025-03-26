/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Orden;

/**
 *
 * @author morales
 */
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrdenBeanIT {
    
    protected EntityManagerFactory emf;
    
    
    static Network red = Network.newNetwork();
    
    @Container
    static GenericContainer postgres = new PostgreSQLContainer("postgres:16-alpine")
            .withDatabaseName("PupaSV")
            .withPassword("abc123")
            .withUsername("postgres")
            .withInitScript("pupa_db.sql")
            .withExposedPorts(5432)
            .withNetworkAliases("db");
    
     // Latest liberty image
    static final DockerImageName libertyImage = DockerImageName.parse("openliberty_pg:10.25.0.0.1");
    
    
    
    
    
    @BeforeAll
    public void inicializar(){
        Map<String, Object> propiedades = new HashMap<>();
        propiedades.put("jakarta.persistence.jdbc.url", String.format("jdbc:postgresql://localhost:%d/PupaSV", postgres.getMappedPort(5432)));
        emf = Persistence.createEntityManagerFactory("PupaIP", propiedades);
    }
    
    @Test
    @Order(1)
    public void testContar(){
        System.out.println("ContarIT");
        OrdenBean cut = new OrdenBean();
        
        EntityManager em = emf.createEntityManager();
       
        cut.em = em;
        
        int esperado = 3;
        int resultado = cut.count();
        Assertions.assertEquals(esperado, resultado);
    }
    
    @Test
    @Order(2)
    public void insertar(){
        System.out.println("InsertarIT");
        
        OrdenBean cut = new OrdenBean();
        EntityManager em = emf.createEntityManager();
        
        cut.em = em;
        
        Orden registro = new Orden();
        registro.setFecha(new Date()); // Fecha actual
        registro.setSucursal("A001"); // Código de sucursal
        registro.setAnulada(false); // No anulada
        
        try {
            cut.em.getTransaction().begin();
            cut.create(registro);
            cut.em.getTransaction().commit();
            Assertions.assertNotNull(registro.getIdOrden());
            System.out.println("Registro Creado con ID "+registro.getIdOrden());
            
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            try {
                cut.em.getTransaction().rollback();
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
}
