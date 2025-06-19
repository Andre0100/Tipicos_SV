/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.unitTests;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.boundary.rest.server.PupaSvApp;

/**
 *
 * @author andrea
 */
public class PupaSvAppTest {
    
    @Test
    void testClassAnnotations() {
        // Verificar que la clase tenga la anotación ApplicationPath
        ApplicationPath annotation = PupaSvApp.class.getAnnotation(ApplicationPath.class);
        assertNotNull(annotation, "La clase debe tener la anotación @ApplicationPath");
        assertEquals("v1", annotation.value(), "El path de la aplicación debe ser 'v1'");
    }

    @Test
    void testSuperClass() {
        // Verificar que extiende de Application
        assertTrue(Application.class.isAssignableFrom(PupaSvApp.class), 
            "PupaSvApp debe extender de jakarta.ws.rs.core.Application");
    }

    @Test
    void testClassIsNotAbstract() {
        // Verificar que la clase no sea abstracta
        assertFalse(java.lang.reflect.Modifier.isAbstract(PupaSvApp.class.getModifiers()), 
            "PupaSvApp no debe ser una clase abstracta");
    }

    @Test
    void testDefaultConstructor() {
        // Verificar que se puede instanciar
        PupaSvApp app = new PupaSvApp();
        assertNotNull(app, "Debe poder crearse una instancia de PupaSvApp");
    }

    @Test
    void testApplicationPathBehavior() {
        // Verificar el comportamiento del path (prueba más integrada)
        PupaSvApp app = new PupaSvApp();
        ApplicationPath annotation = app.getClass().getAnnotation(ApplicationPath.class);
        assertNotNull(annotation);
        assertEquals("v1", annotation.value());
    }
    
}
