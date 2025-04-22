/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import testing.ContainerExtension;

/**
 *
 * @author morales
 */
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(ContainerExtension.class)
public abstract class BaseIntegrationAbstract {
    
    protected Client cliente;
    protected WebTarget target;
    protected EntityManagerFactory emf;

    @BeforeAll
    public void initializeClient() {
        PostgreSQLContainer<?> postgres = ContainerExtension.getPostgres();
        GenericContainer<?> openliberty = ContainerExtension.getOpenLiberty();
        
        assertTrue(postgres.isRunning());
        assertTrue(openliberty.isRunning());
        
        //System.out.println("=== PostgreSQL Logs ===");
        //System.out.println(postgres.getLogs());
        System.out.println("=== Liberty Logs ===");
        System.out.println(openliberty.getLogs());
        
        cliente = ClientBuilder.newClient();
        target = cliente.target(getBaseUrl());
        System.out.println("Testing URL: " + getBaseUrl());
        
        Map<String, Object> propiedades = new HashMap<>();
        propiedades.put("jakarta.persistence.jdbc.url", String.format("jdbc:postgresql://localhost:%d/PupaSV", postgres.getMappedPort(5432)));
        emf = Persistence.createEntityManagerFactory("PupaIP", propiedades);
    }
    
    protected String getBaseUrl() {
        return String.format("http://localhost:%d/PupaSV-1.0-SNAPSHOT/v1/", 
               ContainerExtension.getOpenLiberty().getMappedPort(9080));
    }
    
    
}
