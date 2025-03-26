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

/** 
 *
 * @author morales
 */

import java.util.List;

public abstract class AbstractDataPersistence<T>{

    final Class tipoDato;
    private Class<T> entityClass;

    public AbstractDataPersistence(Class tipoDato) {
        this.tipoDato = tipoDato;
    }

    public abstract EntityManager getEntityManager();
    
    /**
     * 
     * @param registro
     * @throws IllegalStateException
     * @throws IllegalArgumentException 
     */
    public void create(T registro) throws IllegalStateException, IllegalArgumentException {
        if (registro != null) {
            EntityManager em = getEntityManager();
            if (em != null){
                em.persist(registro);
                return;
            }
            throw new IllegalStateException("No se puede agregar el registro");
        }
        throw new IllegalArgumentException("No se puede agregar el registro");
    }

    public T update(T registro) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = null;
        em = getEntityManager();

        if (registro != null) {
            if (em != null) {
                return em.merge(registro);
            }
            throw new IllegalStateException("No se puede modificar el registro");
        }
        throw new IllegalArgumentException("No se puede modificar el registro");
    }

    public void delete(T registro) throws IllegalStateException, IllegalArgumentException {
        if (registro != null){
            EntityManager em = null;
            em = getEntityManager();

            if (em != null){
                if (!em.contains(registro)){
                    registro = em.merge(registro);
                }
                em.remove(registro);
                return;
            }else {
                throw new IllegalStateException("No se puede eliminar el registro");
            }
        }
        throw new IllegalArgumentException("No se puede eliminar el registro");
    }

    public List<T> findRange(int first, int pagesize) {
        // Validar parámetros de entrada
        if (first < 0) {
            throw new IllegalArgumentException("El índice 'first' no puede ser negativo.");
        }
        if (pagesize <= 0) {
            throw new IllegalArgumentException("El tamaño de la página 'pagesize' debe ser mayor que cero.");
        }

        EntityManager em = getEntityManager();

        // Verificar si el EntityManager es nulo
        if (em == null) {
            throw new IllegalStateException("El EntityManager no está disponible.");
        }

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(tipoDato);
            Root<T> raiz = cq.from(tipoDato);
            cq.select(raiz);

            TypedQuery<T> q = em.createQuery(cq);
            q.setFirstResult(first);
            q.setMaxResults(pagesize);

            return q.getResultList();

    }


    public T findById(Object id) {

        EntityManager em  =null;
        if(id != null){
            em = getEntityManager();
            if(em != null){
                return (T) em.find(tipoDato, id);
            }
            throw new IllegalStateException();
        }
        throw new IllegalArgumentException();
    }


    public int count() {
        EntityManager em = null;
        try{
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("No se puede contar registros: EntityManager no está disponible.");
            }

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(tipoDato)));

            return em.createQuery(criteriaQuery).getSingleResult().intValue();

        } catch (Exception ex) {
            // Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error inesperado: " + ex.getMessage(), ex);
            throw new IllegalStateException("Error inesperado al contar registros.", ex);
        }
    }

    public String imprimirCarnet(){
        return "MT20005";
    }

}