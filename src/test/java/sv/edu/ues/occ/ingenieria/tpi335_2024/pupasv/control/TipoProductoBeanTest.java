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
    CriteriaBuilder cb;

    @Mock
    CriteriaQuery<TipoProducto> cq;

    @Mock
    CriteriaQuery<Long> cqLong;

    @Mock
    Root<TipoProducto> root;

    @Mock
    Expression<Long> expression;

    @Mock
    TypedQuery<TipoProducto> typedQuery;

    @Mock
    TypedQuery<Long> countQuery;

    @InjectMocks
    TipoProductoBean cut;

    private final List<TipoProducto> findResult = Arrays.asList(
            new TipoProducto(1),
            new TipoProducto(2),
            new TipoProducto(3)
    );

    @Test
    void create() {
        System.out.println("TipoProductoBeanTest --> create");
        TipoProducto nuevo = new TipoProducto();

        assertThrows(IllegalArgumentException.class, () -> cut.create(null));
        assertThrows(IllegalStateException.class, () -> new TipoProductoBean().create(nuevo));

        cut.create(nuevo);
        verify(em).persist(nuevo);
    }

    @Test
    void findById() {
        System.out.println("TipoProductoBeanTest --> findById");
        Integer idEsperado = 1;
        TipoProducto esperado = new TipoProducto(idEsperado);

        when(em.find(TipoProducto.class, idEsperado)).thenReturn(esperado);

        assertThrows(IllegalStateException.class, () -> new TipoProductoBean().findById(idEsperado));

        TipoProducto resultado = cut.findById(idEsperado);
        assertNotNull(resultado);
        assertEquals(esperado, resultado);

        assertThrows(IllegalArgumentException.class, () -> cut.findById(null));
    }

    @Test
    void findRange() {
        System.out.println("TipoProductoBeanTest --> findRange");
        int first = 0;
        int max = 10;

        assertThrows(IllegalArgumentException.class, () -> cut.findRange(-1, 10));
        assertThrows(IllegalArgumentException.class, () -> cut.findRange(10, -1));

        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(TipoProducto.class)).thenReturn(cq);
        when(cq.from(TipoProducto.class)).thenReturn(root);
        when(em.createQuery(cq)).thenReturn(typedQuery);
        when(typedQuery.setFirstResult(first)).thenReturn(typedQuery);
        when(typedQuery.setMaxResults(max)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(findResult);

        List<TipoProducto> result = cut.findRange(first, max);

        assertNotNull(result);
        assertEquals(findResult.size(), result.size());
        assertThrows(IllegalStateException.class, () -> new TipoProductoBean().findRange(first, max));
    }

    @Test
    void delete() {
        System.out.println("TipoProductoBeanTest --> delete");
        TipoProducto entity = new TipoProducto(1);

        assertThrows(IllegalArgumentException.class, () -> cut.delete(null));
        assertThrows(IllegalStateException.class, () -> new TipoProductoBean().delete(entity));

        when(em.contains(entity)).thenReturn(true);
        cut.delete(entity);
        verify(em).remove(entity);

        when(em.contains(entity)).thenReturn(false);
        when(em.merge(entity)).thenReturn(entity);
        cut.delete(entity);
        verify(em, times(2)).remove(entity);
    }

    @Test
    void update() {
        System.out.println("TipoProductoBeanTest --> update");
        TipoProducto modificado = new TipoProducto(1);

        assertThrows(IllegalArgumentException.class, () -> cut.update(null));
        assertThrows(IllegalStateException.class, () -> new TipoProductoBean().update(modificado));

        when(em.merge(modificado)).thenReturn(modificado);
        TipoProducto result = cut.update(modificado);

        assertNotNull(result);
        assertEquals(modificado, result);
    }

    @Test
    void count() {
        System.out.println("TipoProductoBeanTest --> count");
        assertThrows(IllegalStateException.class, () -> new TipoProductoBean().count());

        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Long.class)).thenReturn(cqLong);
        when(cqLong.from(TipoProducto.class)).thenReturn(root);
        when(cb.count(root)).thenReturn(expression);
        when(cqLong.select(expression)).thenReturn(cqLong);
        when(em.createQuery(cqLong)).thenReturn(countQuery);
        when(countQuery.getSingleResult()).thenReturn(2L);

        int result = cut.count();
        assertEquals(2, result);
    }
}
