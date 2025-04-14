/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDataPersistence<T> implements Serializable {
    final Class tipoDato;

    public AbstractDataPersistence(Class tipoDato) {
        this.tipoDato = tipoDato;
    }

    public abstract EntityManager getEntityManager();

    public void create(T registro) throws IllegalArgumentException, IllegalStateException {
        if (registro != null) {
            EntityManager em = getEntityManager();
            if (em != null) {
                em.persist(registro);
                return;
            }
            throw new IllegalStateException();
        }
        throw new IllegalArgumentException();
    }

    public T findById(Object id) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = null;
        if (id != null) {
            em = getEntityManager();
            if (em != null) {
                return (T) em.find(tipoDato, id);
            }
            throw new IllegalStateException();
        }
        throw new IllegalArgumentException();
    }

    public List<T> findRange(int first, int max) throws IllegalStateException, IllegalArgumentException {
        return findRange(first, max, "",""); // Llama al método con orden como cadena vacía
    }

    public List<T> findRange(int first, int pageSize, String orden, String direccion) throws IllegalStateException {
        EntityManager em = null;
        if (first < 0) {
            throw new IllegalArgumentException("El parámetro 'first' debe ser mayor o igual a cero.");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("El parámetro 'pageSize' debe ser mayor que cero.");
        }

        em = getEntityManager();
        if (em != null) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(tipoDato);
            Root<T> root = cq.from(tipoDato);
            cq.select(root);
            TypedQuery<T> query = em.createQuery(cq);
            query.setFirstResult(first);
            query.setMaxResults(pageSize);

            return query.getResultList();
        }
        throw new IllegalStateException();

    }

    public void delete(T registro) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = null;
        if (registro != null) {
            em = getEntityManager();
            if (em != null) {
                if (!em.contains(registro)) {
                    registro = em.merge(registro);
                }
                em.remove(registro);
                return;
            } else {
                throw new IllegalStateException();
            }
        }
        throw new IllegalArgumentException();

    }

    public T update(T registro) throws IllegalStateException {
        EntityManager em = null;
        if (registro == null) {
            throw new IllegalArgumentException();
        }
        em = getEntityManager();
        if (em != null) {
            return em.merge(registro);
        }
        throw new IllegalStateException();
    }

    public int count() throws IllegalStateException {
        EntityManager em = null;
        em = getEntityManager();
        if (em != null) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);

            cq.select(cb.count(cq.from(tipoDato)));

            return em.createQuery(cq).getSingleResult().intValue();
        }
        throw new IllegalStateException();
    }

}