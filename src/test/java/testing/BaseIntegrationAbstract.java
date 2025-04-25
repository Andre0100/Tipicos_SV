/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testing;

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
import testing.NeedsLiberty;

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
        
        assertTrue(postgres.isRunning());
        
        //System.out.println("=== PostgreSQL Logs ===");
        //
        //System.out.println(postgres.getLogs());
        
        
        //Inicialiaz liberty y postgres si la el test lo requiere
        if(this.getClass().isAnnotationPresent(NeedsLiberty.class)){
            GenericContainer<?> openliberty = ContainerExtension.getOpenLiberty();
            assertTrue(openliberty.isRunning());
            //System.out.println("=== Liberty Logs ===");
            System.out.println(openliberty.getLogs());
        

            cliente = ClientBuilder.newClient();
            target = cliente.target(getBaseUrl());
            System.out.println("Testing URL: " + getBaseUrl());
        }
        String host = postgres.getHost();
        Map<String, Object> propiedades = new HashMap<>();
        propiedades.put("jakarta.persistence.jdbc.url", String.format("jdbc:postgresql://%s:%d/PupaSV", host, postgres.getMappedPort(5432)));
        emf = Persistence.createEntityManagerFactory("PupaIP", propiedades);
        System.out.println("VER URL DE POSTGRES"+ postgres.getHost());

    }
    
    protected String getBaseUrl() {
        if (this.getClass().isAnnotationPresent(NeedsLiberty.class)) {
            String hostliberty = ContainerExtension.getOpenLiberty().getHost();
            return String.format("http://%s:%d/PupaSV-1.0-SNAPSHOT/v1/", hostliberty, 
                   ContainerExtension.getOpenLiberty().getMappedPort(9080));
        }
        return null;
    }
    
    
}
