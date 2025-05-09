package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.unitTests;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.AbstractDataPersistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public abstract class AbstractDataPersistenceTest <T>{

    @Mock
    public EntityManager em;

    public abstract AbstractDataPersistence<T> getBean();
    public abstract T crearInstancia();
    public abstract List<T> listaInstancias();
    public abstract Class<T> getClaseEntidad();
    public void prepararMocks(EntityManager em) {
    }

    @Test
    void create() {
        T entidad = crearInstancia();
        AbstractDataPersistence<T> beanEmNull = new AbstractDataPersistence<T>(getClaseEntidad()) {
            @Override
            public EntityManager getEntityManager(){
                return null;
            }
        };
        assertThrows(IllegalStateException.class, () -> beanEmNull.create(entidad));
        assertThrows(IllegalArgumentException.class, () -> getBean().create(null));
        assertDoesNotThrow(() -> getBean().create(entidad));
    }

    @Test
    void findById(){
        T entidad = crearInstancia();
        when(em.find(getClaseEntidad(),1)).thenReturn(entidad);
        assertThrows(IllegalArgumentException.class, () -> getBean().findById(null));
        AbstractDataPersistence<T> beanEmNull = new AbstractDataPersistence<T>(getClaseEntidad()) {
            @Override
            public EntityManager getEntityManager(){
                return null;
            }
        };
        assertThrows(IllegalStateException.class, () -> beanEmNull.findById(1));
        assertDoesNotThrow(() -> {
            T resultado = getBean().findById(1);
            assertEquals(entidad, resultado);
        });
    }

    @Test
    void findRange(){
        List<T> listaEsperada = listaInstancias();
        assertThrows(IllegalArgumentException.class, () -> getBean().findRange(-1,10));
        assertThrows(IllegalArgumentException.class, () -> getBean().findRange(0,0));
        AbstractDataPersistence<T> beanEmNull = new AbstractDataPersistence<T>(getClaseEntidad()) {
            @Override
            public EntityManager getEntityManager(){
                return null;
            }
        };
        assertThrows(IllegalStateException.class, () -> beanEmNull.findRange(0,10));
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<T> cq = mock(CriteriaQuery.class);
        Root<T> root = mock(Root.class);
        TypedQuery<T> tq = mock(TypedQuery.class);

        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(getClaseEntidad())).thenReturn(cq);
        when(cq.from(getClaseEntidad())).thenReturn(root);
        when(em.createQuery(cq)).thenReturn(tq);
        when(tq.setFirstResult(anyInt())).thenReturn(tq);
        when(tq.setMaxResults(anyInt())).thenReturn(tq);
        when(tq.getResultList()).thenReturn(listaEsperada);

        List<T> resultado = getBean().findRange(0,10);
        assertEquals(listaEsperada.size(), resultado.size());
    }

    @Test
    void delete() {
        T entidad = crearInstancia();
        when(em.contains(entidad)).thenReturn(true);
        AbstractDataPersistence<T> beanEmNull = new AbstractDataPersistence<T>(getClaseEntidad()) {
            @Override
            public EntityManager getEntityManager(){
                return null;
            }
        };
        assertThrows(IllegalStateException.class, () -> beanEmNull.delete(entidad));
        when(em.contains(entidad)).thenReturn(false);
        when(em.merge(entidad)).thenReturn(entidad);
        assertThrows(IllegalArgumentException.class, () -> getBean().delete(null));
        assertDoesNotThrow(() -> getBean().delete(entidad));
        verify(em).merge(entidad);
        verify(em).remove(entidad);
    }

    @Test
    void update(){
        T entidad = crearInstancia();
        when(em.merge(entidad)).thenReturn(entidad);
        AbstractDataPersistence<T> beanEmNull = new AbstractDataPersistence<T>(getClaseEntidad()) {
            @Override
            public EntityManager getEntityManager(){
                return null;
            }
        };
        assertThrows(IllegalStateException.class, () -> beanEmNull.update(entidad));
        assertThrows(IllegalArgumentException.class, () -> getBean().update(null));
        T resultado = getBean().update(entidad);
        assertEquals(entidad, resultado);
    }

    @Test
    void count(){
        AbstractDataPersistence<T> beanEmNull = new AbstractDataPersistence<T>(getClaseEntidad()) {
            @Override
            public EntityManager getEntityManager(){
                return null;
            }
        };
        assertThrows(IllegalStateException.class, beanEmNull::count);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<Long> cq = mock(CriteriaQuery.class);
        Root<T> root = mock(Root.class);
        TypedQuery<Long> tq = mock(TypedQuery.class);

        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Long.class)).thenReturn(cq);
        when(cq.from(getClaseEntidad())).thenReturn(root);
        when(cb.count(root)).thenReturn(mock(jakarta.persistence.criteria.Expression.class));
        when(cq.select(any())).thenReturn(cq);
        when(em.createQuery(cq)).thenReturn(tq);
        when(tq.getSingleResult()).thenReturn(3L);

        int resultado = getBean().count();
        assertEquals(3, resultado);
    }

}
