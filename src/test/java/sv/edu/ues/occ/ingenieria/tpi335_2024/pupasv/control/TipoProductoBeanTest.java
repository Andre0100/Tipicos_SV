package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.entity.TipoProducto;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TipoProductoBeanTest {

    @Mock
    EntityManager em;

    @Mock
    TypedQuery<String> query;

    @InjectMocks
    TipoProductoBean cut;

    @Test
    void getEntityManager(){
        System.out.println("TipoProductoBean --> getEntityManager");
        assertEquals(em, cut.getEntityManager());
    }

    @Test
    void findByNombreTipoProductoName() {
        System.out.println("TipoProductoBean --> findByNombreTipoProductoName");
        TypedQuery<String> mockQuery = mock(TypedQuery.class);
        List<String> nombresEsperados = List.of("Pupusas", "Tamales");

        when(em.createNamedQuery("TipoProducto.findAllNombres", String.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(nombresEsperados);

        List<String> resultado = cut.findByNombreTipoProductoName();

        assertNotNull(resultado);
        assertEquals(nombresEsperados.size(), resultado.size());
        assertEquals("Pupusas", resultado.get(0));
        assertEquals("Tamales", resultado.get(1));

        when(em.createNamedQuery("TipoProducto.findAllNombres", String.class)).thenThrow(RuntimeException.class);

        List<String> resultadoExcepcion = cut.findByNombreTipoProductoName();
        assertNotNull(resultadoExcepcion);
        assertTrue(resultadoExcepcion.isEmpty(), "Debe retornar una lista vacía si ocurre una excepción.");
    }
}
