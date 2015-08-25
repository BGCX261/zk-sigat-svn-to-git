/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.Comodidades;
import entity.DepartamentoEntity;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Clase encargada de controlar las transacciones contra la base de datos 
 * del Entity Comodidades
 *
 * @author Sebastian
 */
public class ComodidadesJpaController implements Serializable {

   
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor del JPAController
	 * 
	 * @param EntityManagerFactrory emf
	 */
	public ComodidadesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Metodo encargado de persistir la entidad en la Base de Datos
     * 
     * @param Comodidades comodidades
     * @throws PreexistingEntityException
     * @throws Exception
     */
    public void create(Comodidades comodidades) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DepartamentoEntity departamentosIdDepartamento = comodidades.getDepartamentosIdDepartamento();
            if (departamentosIdDepartamento != null) {
                departamentosIdDepartamento = em.getReference(departamentosIdDepartamento.getClass(), departamentosIdDepartamento.getIdDepartamento());
                comodidades.setDepartamentosIdDepartamento(departamentosIdDepartamento);
            }
            em.persist(comodidades);
            if (departamentosIdDepartamento != null) {
                departamentosIdDepartamento.getComodidadesList().add(comodidades);
                departamentosIdDepartamento = em.merge(departamentosIdDepartamento);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComodidades(comodidades.getIdcomodidades()) != null) {
                throw new PreexistingEntityException("Comodidades " + comodidades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Metodo encargado de realizar un Update en la Tabla para la entidad recibida
     * como parametro
     * 
     * @param Comodidades comodidades
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(Comodidades comodidades) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comodidades persistentComodidades = em.find(Comodidades.class, comodidades.getIdcomodidades());
            DepartamentoEntity departamentosIdDepartamentoOld = persistentComodidades.getDepartamentosIdDepartamento();
            DepartamentoEntity departamentosIdDepartamentoNew = comodidades.getDepartamentosIdDepartamento();
            if (departamentosIdDepartamentoNew != null) {
                departamentosIdDepartamentoNew = em.getReference(departamentosIdDepartamentoNew.getClass(), departamentosIdDepartamentoNew.getIdDepartamento());
                comodidades.setDepartamentosIdDepartamento(departamentosIdDepartamentoNew);
            }
            comodidades = em.merge(comodidades);
            if (departamentosIdDepartamentoOld != null && !departamentosIdDepartamentoOld.equals(departamentosIdDepartamentoNew)) {
                departamentosIdDepartamentoOld.getComodidadesList().remove(comodidades);
                departamentosIdDepartamentoOld = em.merge(departamentosIdDepartamentoOld);
            }
            if (departamentosIdDepartamentoNew != null && !departamentosIdDepartamentoNew.equals(departamentosIdDepartamentoOld)) {
                departamentosIdDepartamentoNew.getComodidadesList().add(comodidades);
                departamentosIdDepartamentoNew = em.merge(departamentosIdDepartamentoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comodidades.getIdcomodidades();
                if (findComodidades(id) == null) {
                    throw new NonexistentEntityException("The comodidades with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Metodo encargado de destruir de la base de datos la entidad con el ID 
     * recibido como parametro 
     * 
     * @param Integer id
     * @throws NonexistentEntityException
     */
    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comodidades comodidades;
            try {
                comodidades = em.getReference(Comodidades.class, id);
                comodidades.getIdcomodidades();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comodidades with id " + id + " no longer exists.", enfe);
            }
            DepartamentoEntity departamentosIdDepartamento = comodidades.getDepartamentosIdDepartamento();
            if (departamentosIdDepartamento != null) {
                departamentosIdDepartamento.getComodidadesList().remove(comodidades);
                departamentosIdDepartamento = em.merge(departamentosIdDepartamento);
            }
            em.remove(comodidades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Metodo encargado de recolectar de la Base de Datos TODOS las entidades
     * existentes 
     * 
     * @return List<Comodidades> allComodidades
     */
    public List<Comodidades> findComodidadesEntities() {
        return findComodidadesEntities(true, -1, -1);
    }

    /**
     * Metodo encargado de realizar una busqueda acotada de Entidades en la 
     * Base de datos segun los parametros ingresados 
     * 
     * @param int maxResults
     * @param int firstResult
     * @return List<Comodidades> listaComodidades
     */
    public List<Comodidades> findComodidadesEntities(int maxResults, int firstResult) {
        return findComodidadesEntities(false, maxResults, firstResult);
    }

    /**
     * Metodo general de busqueda acotada de Entidades en la 
     * Base de datos segun los parametros ingresados 
     *
     * @param boolean all
     * @param int maxResults
     * @param int firstResult
     * @return List<Comodidades> listaComodidades
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Comodidades> findComodidadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comodidades.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Metodo encargado de buscar en la Base de Datos la entidad con el ID pasado 
     * por parametros 
     * 
     * @param Integer id
     * @return Comodidades comodidad
     */
    public Comodidades findComodidades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comodidades.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Metodo encargado de retornar la cantidad de Entidades en tabla
     * 
     * @return int cantidad
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int getComodidadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comodidades> rt = cq.from(Comodidades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
