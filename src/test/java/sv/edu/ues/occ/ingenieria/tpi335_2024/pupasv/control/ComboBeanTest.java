/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.Combo;

/**
 *
 * @author andrea
 */
@ExtendWith(MockitoExtension.class)
public class ComboBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Combo> comboQuery;

    @Mock
    private TypedQuery<Long> countQuery;

    @InjectMocks
    private ComboBean cut;

    private final Combo combo = new Combo(1L); // <- Nota la 'L' para Long
    private final List<Combo> combos = Arrays.asList(
            new Combo(1L),
            new Combo(2L)
    );

    @Test
    void findRange_DeberiaRetornarCombosActivosConPaginacion() {
        // Configurar
        when(em.createNamedQuery("Combo.findActivos", Combo.class))
                .thenReturn(comboQuery);
        when(comboQuery.setFirstResult(0)).thenReturn(comboQuery);
        when(comboQuery.setMaxResults(10)).thenReturn(comboQuery);
        when(comboQuery.getResultList()).thenReturn(combos);

        // Actuar
        List<Combo> resultado = cut.findRange(0, 10, true);

        // Verificar
        assertEquals(2, resultado.size());
        verify(em).createNamedQuery("Combo.findActivos", Combo.class);
        verify(comboQuery).setFirstResult(0);
        verify(comboQuery).setMaxResults(10);
    }

    @Test
    void findRange_DeberiaRetornarTodosLosCombos() {
        // Configurar
        when(em.createNamedQuery("Combo.findAll", Combo.class))
                .thenReturn(comboQuery);
        when(comboQuery.setFirstResult(5)).thenReturn(comboQuery);
        when(comboQuery.setMaxResults(20)).thenReturn(comboQuery);
        when(comboQuery.getResultList()).thenReturn(combos);

        // Actuar
        List<Combo> resultado = cut.findRange(5, 20, false);

        // Verificar
        assertEquals(2, resultado.size());
        verify(em).createNamedQuery("Combo.findAll", Combo.class);
        verify(comboQuery).setFirstResult(5);
        verify(comboQuery).setMaxResults(20);
    }

    @Test
     void count_DeberiaContarCombosActivos() {
        // Configurar
        when(em.createNamedQuery("Combo.countActivos", Long.class))
                .thenReturn(countQuery);
        when(countQuery.getSingleResult()).thenReturn(5L);

        // Actuar
        long resultado = cut.count(true);

        // Verificar
        assertEquals(5L, resultado);
        verify(em).createNamedQuery("Combo.countActivos", Long.class);
    }

    @Test
    void count_DeberiaContarTodosLosCombos() {
        // Configurar
        when(em.createNamedQuery("Combo.countAll", Long.class))
                .thenReturn(countQuery);
        when(countQuery.getSingleResult()).thenReturn(10L);

        // Actuar
        long resultado = cut.count(false);

        // Verificar
        assertEquals(10L, resultado);
        verify(em).createNamedQuery("Combo.countAll", Long.class);
    }

    @Test
    void findByIdWithDetalles_DeberiaRetornarComboConDetalles() {
        // Configurar
        TypedQuery<Combo> query = mock(TypedQuery.class);
        when(em.createNamedQuery("Combo.findWithDetalles", Combo.class))
                .thenReturn(query);
        when(query.setParameter("idCombo", 1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(combo);

        // Actuar
        Combo resultado = cut.findByIdWithDetalles(1);

        // Verificar
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdCombo());
        verify(query).setParameter("idCombo", 1);
    }

    @ParameterizedTest
    @CsvSource({
        "-1, 10, true",
        "0, -5, false",
        "-5, -10, true"
    })
    void findRange_DeberiaLanzarExcepcionConParametrosInvalidos(int first, int pageSize, boolean activos) {
        assertThrows(
                IllegalArgumentException.class,
                () -> cut.findRange(first, pageSize, activos)
        );
    }

    @Test
    void findByIdWithDetalles_DeberiaLanzarExcepcionSiNoEncuentraCombo() {
        // Configurar
        TypedQuery<Combo> query = mock(TypedQuery.class);
        when(em.createNamedQuery("Combo.findWithDetalles", Combo.class))
                .thenReturn(query);
        when(query.setParameter("idCombo", 999)).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new jakarta.persistence.NoResultException());

        // Actuar y Verificar
        assertThrows(
                jakarta.persistence.NoResultException.class,
                () -> cut.findByIdWithDetalles(999)
        );
    }
}
