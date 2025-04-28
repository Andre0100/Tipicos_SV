/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class ComboDetalleDTOTest {
    
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testConstructorYGettersSetters() {
        // Crear un objeto ComboDetalleDTO usando el constructor con parámetros
        ComboDetalleDTO dto = new ComboDetalleDTO(1L, 100L, 5);

        // Verificar que los valores iniciales son correctos a través de los getters
        assertEquals(1L, dto.getIdDetalle(), "El idDetalle debería ser 1");
        assertEquals(100L, dto.getIdProducto(), "El idProducto debería ser 100");
        assertEquals(5, dto.getCantidad(), "La cantidad debería ser 5");

        // Cambiar los valores a través de los setters
        dto.setIdDetalle(2L);
        dto.setIdProducto(200L);
        dto.setCantidad(10);

        // Verificar que los valores se actualizan correctamente
        assertEquals(2L, dto.getIdDetalle(), "El idDetalle debería ser 2 después de actualizar");
        assertEquals(200L, dto.getIdProducto(), "El idProducto debería ser 200 después de actualizar");
        assertEquals(10, dto.getCantidad(), "La cantidad debería ser 10 después de actualizar");
        
        System.out.println("ComboDetalleDTOTest --> testConstructorYGettersSetters");
        
    }

    @Test
    void testIdProductoNulo() {
        ComboDetalleDTO dto = new ComboDetalleDTO();
        dto.setIdProducto(null); // Violación: idProducto no puede ser nulo
        dto.setCantidad(5);

        Set<ConstraintViolation<ComboDetalleDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Debería fallar porque el idProducto es nulo");

        // Verificar que la violación corresponde a 'idProducto'
        boolean productoViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("idProducto"));
        assertTrue(productoViolation, "La violación debe ser por el campo 'idProducto'");
        
        System.out.println("ComboDetalleDTOTest --> testIdProductoNulo");
        
    }

    @Test
    void testCantidadNula() {
        ComboDetalleDTO dto = new ComboDetalleDTO();
        dto.setIdProducto(1L);
        dto.setCantidad(null); // Violación: cantidad no puede ser nula

        Set<ConstraintViolation<ComboDetalleDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Debería fallar porque la cantidad es nula");

        // Verificar que la violación corresponde a 'cantidad'
        boolean cantidadViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("cantidad"));
        assertTrue(cantidadViolation, "La violación debe ser por el campo 'cantidad'");
        
        System.out.println("ComboDetalleDTOTest --> testCantidadNula");
        
    }

    @Test
    void testCantidadMenorA1() {
        ComboDetalleDTO dto = new ComboDetalleDTO();
        dto.setIdProducto(1L);
        dto.setCantidad(0); // Violación: cantidad debe ser al menos 1

        Set<ConstraintViolation<ComboDetalleDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Debería fallar porque la cantidad es menor que 1");

        // Verificar que la violación corresponde a 'cantidad'
        boolean cantidadViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("cantidad"));
        assertTrue(cantidadViolation, "La violación debe ser por el campo 'cantidad'");
        
        System.out.println("ComboDetalleDTOTest --> testCantidadMenorA1");
        
    }

    @Test
    void testValidComboDetalleDTO() {
        ComboDetalleDTO dto = new ComboDetalleDTO();
        dto.setIdProducto(1L); // Asumiendo que el idProducto es válido
        dto.setCantidad(5); // Cantidad válida (mayor que 0)

        Set<ConstraintViolation<ComboDetalleDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "No deberían haber violaciones de validaciones");
        
        System.out.println("ComboDetalleDTOTest --> testValidComboDetalleDTO");
        
    }
}
