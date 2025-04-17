/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testing;

import java.nio.file.Paths;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.MountableFile;

/**
 *
 * @author morales
 */
public class ContainerExtension implements BeforeAllCallback, AfterAllCallback{
    
    private static boolean contenedorIniciado = false;
    private static int numClassTest = 0;
    
    protected static final Network red = Network.newNetwork();
    
    protected static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("PupaSV")
            .withPassword("abc123")
            .withUsername("postgres")
            .withInitScript("pupa_db.sql")
            .withExposedPorts(5432)
            .withNetwork(red)
            .withNetworkAliases("db");
    
    protected static final GenericContainer<?> openliberty = new GenericContainer<>("openliberty:latest")
            .withExposedPorts(9080)
            .withCopyFileToContainer(getWarFile(), "/home/usuario/wlp/usr/servers/app/dropins/PupaSV-1.0-SNAPSHOT.war")
            .withNetwork(red)
            .withEnv("PGPASSWORD", "abc123")
            .withEnv("PGUSER", "postgres")
            .withEnv("PGDBNAME", "PupaSV")
            .withEnv("PGPORT", "5432")
            .withEnv("PGHOST", "db")
            .dependsOn(postgres)
            .waitingFor(Wait.forLogMessage(".*The app server is ready to run a smarter planet.*", 1));
    
    
    @Override
    public void beforeAll(ExtensionContext contex) throws Exception{
        synchronized (ContainerExtension.class) {
            numClassTest++;
            if(!contenedorIniciado){
                postgres.start();
                openliberty.start();
                contenedorIniciado = true;
                System.out.println("Contenedores iniciados");
                
                Runtime.getRuntime().addShutdownHook(new Thread(()->{
                    if(contenedorIniciado){
                        openliberty.stop();
                        postgres.stop();
                        System.out.println("Contenedores detenidos");
                    }
                }));
            }
        }
    }
    
     @Override
    public void afterAll(ExtensionContext context) throws Exception {
        synchronized (ContainerExtension.class) {
            numClassTest--;
            if (numClassTest == 0 && contenedorIniciado) {
                // No detenemos aquí, el shutdown hook se encargará
            }
        }
    }
    
      // Métodos para acceder a los contenedores
    public static PostgreSQLContainer<?> getPostgres() {
        return postgres;
    }
    
    public static GenericContainer<?> getOpenLiberty() {
        return openliberty;
    }
    
    private static MountableFile getWarFile() {
        return MountableFile.forHostPath(Paths.get("target/PupaSv-1.0-SNAPSHOT.war").toAbsolutePath());
    }
}
