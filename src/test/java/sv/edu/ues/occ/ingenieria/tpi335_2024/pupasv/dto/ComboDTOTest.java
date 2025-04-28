/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.dto;

import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andrea
 */
public class ComboDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidComboDTO() {
        ComboDTO dto = new ComboDTO();
        dto.setNombre("Combo Feliz");
        dto.setDescripcion("Combo especial");
        dto.setPrecio(new BigDecimal("10.00")); // aquí sí debe haber precio
        dto.setActivo(true);

        // Crear al menos un detalle para cumplir con @Size(min = 1)
        ComboDetalleDTO detalle = new ComboDetalleDTO();
        List<ComboDetalleDTO> detalles = new ArrayList<>();
        detalles.add(detalle);
        dto.setDetalles(detalles);

        Set<ConstraintViolation<ComboDTO>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
        }

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validaciones");

        System.out.println("ComboDTOTest --> testValidComboDTO");

    }

    @Test
    void testNombreNulo() {
        ComboDTO dto = new ComboDTO();
        dto.setNombre(null); // Violación
        dto.setDescripcion("Descripcion de prueba");
        //dto.setPrecio(10.0);
        dto.setActivo(true);

        Set<ConstraintViolation<ComboDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Debería fallar porque nombre es nulo");

        // Opcional: verificar que la violación corresponde a 'nombre'
        boolean nombreViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre"));
        assertTrue(nombreViolation, "La violación debe ser por el campo 'nombre'");

        System.out.println("ComboDTOTest --> testNombreNulo");

    }

    @Test
    void testConstructorAndGettersSetters() {
        Long id = 1L;
        String nombre = "Combo Mega";
        String descripcion = "Incluye 3 pupusas y bebida";
        BigDecimal precio = new BigDecimal("5.99");
        Boolean activo = true;
        ComboDetalleDTO detalle = new ComboDetalleDTO();
        List<ComboDetalleDTO> detalles = Collections.singletonList(detalle);

        // Usar constructor parametrizado
        ComboDTO dto = new ComboDTO(id, nombre, descripcion, precio, activo, detalles);

        // Probar getters
        assertEquals(id, dto.getIdCombo());
        assertEquals(nombre, dto.getNombre());
        assertEquals(descripcion, dto.getDescripcion());
        assertEquals(precio, dto.getPrecio());
        assertEquals(activo, dto.getActivo());
        assertEquals(detalles, dto.getDetalles());

        // Probar setters cambiando los valores
        dto.setIdCombo(2L);
        dto.setNombre("Combo Super");
        dto.setDescripcion("Incluye pupusas, tamales y bebida");
        dto.setPrecio(new BigDecimal("8.99"));
        dto.setActivo(false);

        ComboDetalleDTO nuevoDetalle = new ComboDetalleDTO();
        List<ComboDetalleDTO> nuevosDetalles = Collections.singletonList(nuevoDetalle);
        dto.setDetalles(nuevosDetalles);

        assertEquals(2L, dto.getIdCombo());
        assertEquals("Combo Super", dto.getNombre());
        assertEquals("Incluye pupusas, tamales y bebida", dto.getDescripcion());
        assertEquals(new BigDecimal("8.99"), dto.getPrecio());
        assertEquals(false, dto.getActivo());
        assertEquals(nuevosDetalles, dto.getDetalles());

        // Probar toString() (solo que no truene, no necesita asserts complicados)
        String toStringResult = dto.toString();
        assertTrue(toStringResult.contains("ComboDTO"));
        assertTrue(toStringResult.contains("idCombo=2"));

        System.out.println("ComboDTOTest --> testConstructorAndGettersSetters");
    }

}
