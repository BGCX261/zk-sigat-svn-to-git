/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Comodidades;
import entity.DepartamentoEntity;
import java.util.ArrayList;
import java.util.List;
import entity.ReservaEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Clase encargada de controlar las transacciones contra la base de datos 
 * del Entity Departamentos
 * 
 * @author Sebastian
 */
public class DepartamentosJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor del JPAController
	 * 
	 * @param EntityManagerFactrory emf
	 */
	public DepartamentosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Metodo encargado de persistir la entidad en la Base de Datos
     * 
     * @param DepartamentoEntity departamentos
     */
    public void create(DepartamentoEntity departamentos) {
        if (departamentos.getComodidadesList() == null) {
            departamentos.setComodidadesList(new ArrayList<Comodidades>());
        }
        if (departamentos.getReservasList() == null) {
            departamentos.setReservasList(new ArrayList<ReservaEntity>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Comodidades> attachedComodidadesList = new ArrayList<Comodidades>();
            for (Comodidades comodidadesListComodidadesToAttach : departamentos.getComodidadesList()) {
                comodidadesListComodidadesToAttach = em.getReference(comodidadesListComodidadesToAttach.getClass(), comodidadesListComodidadesToAttach.getIdcomodidades());
                attachedComodidadesList.add(comodidadesListComodidadesToAttach);
            }
            departamentos.setComodidadesList(attachedComodidadesList);
            List<ReservaEntity> attachedReservasList = new ArrayList<ReservaEntity>();
            for (ReservaEntity reservasListReservasToAttach : departamentos.getReservasList()) {
                reservasListReservasToAttach = em.getReference(reservasListReservasToAttach.getClass(), reservasListReservasToAttach.getIdReserva());
                attachedReservasList.add(reservasListReservasToAttach);
            }
            departamentos.setReservasList(attachedReservasList);
            em.persist(departamentos);
            for (Comodidades comodidadesListComodidades : departamentos.getComodidadesList()) {
            	DepartamentoEntity oldDepartamentosIdDepartamentoOfComodidadesListComodidades = comodidadesListComodidades.getDepartamentosIdDepartamento();
                comodidadesListComodidades.setDepartamentosIdDepartamento(departamentos);
                comodidadesListComodidades = em.merge(comodidadesListComodidades);
                if (oldDepartamentosIdDepartamentoOfComodidadesListComodidades != null) {
                    oldDepartamentosIdDepartamentoOfComodidadesListComodidades.getComodidadesList().remove(comodidadesListComodidades);
                    oldDepartamentosIdDepartamentoOfComodidadesListComodidades = em.merge(oldDepartamentosIdDepartamentoOfComodidadesListComodidades);
                }
            }
            for (ReservaEntity reservasListReservas : departamentos.getReservasList()) {
                DepartamentoEntity oldDepartamentosIdDepartamento1OfReservasListReservas = reservasListReservas.getDepartamentosIdDepartamento1();
                reservasListReservas.setDepartamentosIdDepartamento1(departamentos);
                reservasListReservas = em.merge(reservasListReservas);
                if (oldDepartamentosIdDepartamento1OfReservasListReservas != null) {
                    oldDepartamentosIdDepartamento1OfReservasListReservas.getReservasList().remove(reservasListReservas);
                    oldDepartamentosIdDepartamento1OfReservasListReservas = em.merge(oldDepartamentosIdDepartamento1OfReservasListReservas);
                }
            }
            em.getTransaction().commit();
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
     * @param DepartamentoEntity departamentos
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(DepartamentoEntity departamentos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DepartamentoEntity persistentDepartamentos = em.find(DepartamentoEntity.class, departamentos.getIdDepartamento());
            List<Comodidades> comodidadesListOld = persistentDepartamentos.getComodidadesList();
            List<Comodidades> comodidadesListNew = departamentos.getComodidadesList();
            List<ReservaEntity> reservasListOld = persistentDepartamentos.getReservasList();
            List<ReservaEntity> reservasListNew = departamentos.getReservasList();
            List<Comodidades> attachedComodidadesListNew = new ArrayList<Comodidades>();
            for (Comodidades comodidadesListNewComodidadesToAttach : comodidadesListNew) {
                comodidadesListNewComodidadesToAttach = em.getReference(comodidadesListNewComodidadesToAttach.getClass(), comodidadesListNewComodidadesToAttach.getIdcomodidades());
                attachedComodidadesListNew.add(comodidadesListNewComodidadesToAttach);
            }
            comodidadesListNew = attachedComodidadesListNew;
            departamentos.setComodidadesList(comodidadesListNew);
            List<ReservaEntity> attachedReservasListNew = new ArrayList<ReservaEntity>();
            for (ReservaEntity reservasListNewReservasToAttach : reservasListNew) {
                reservasListNewReservasToAttach = em.getReference(reservasListNewReservasToAttach.getClass(), reservasListNewReservasToAttach.getIdReserva());
                attachedReservasListNew.add(reservasListNewReservasToAttach);
            }
            reservasListNew = attachedReservasListNew;
            departamentos.setReservasList(reservasListNew);
            departamentos = em.merge(departamentos);
            for (Comodidades comodidadesListOldComodidades : comodidadesListOld) {
                if (!comodidadesListNew.contains(comodidadesListOldComodidades)) {
                    comodidadesListOldComodidades.setDepartamentosIdDepartamento(null);
                    comodidadesListOldComodidades = em.merge(comodidadesListOldComodidades);
                }
            }
            for (Comodidades comodidadesListNewComodidades : comodidadesListNew) {
                if (!comodidadesListOld.contains(comodidadesListNewComodidades)) {
                    DepartamentoEntity oldDepartamentosIdDepartamentoOfComodidadesListNewComodidades = comodidadesListNewComodidades.getDepartamentosIdDepartamento();
                    comodidadesListNewComodidades.setDepartamentosIdDepartamento(departamentos);
                    comodidadesListNewComodidades = em.merge(comodidadesListNewComodidades);
                    if (oldDepartamentosIdDepartamentoOfComodidadesListNewComodidades != null && !oldDepartamentosIdDepartamentoOfComodidadesListNewComodidades.equals(departamentos)) {
                        oldDepartamentosIdDepartamentoOfComodidadesListNewComodidades.getComodidadesList().remove(comodidadesListNewComodidades);
                        oldDepartamentosIdDepartamentoOfComodidadesListNewComodidades = em.merge(oldDepartamentosIdDepartamentoOfComodidadesListNewComodidades);
                    }
                }
            }
            for (ReservaEntity reservasListOldReservas : reservasListOld) {
                if (!reservasListNew.contains(reservasListOldReservas)) {
                    reservasListOldReservas.setDepartamentosIdDepartamento1(null);
                    reservasListOldReservas = em.merge(reservasListOldReservas);
                }
            }
            for (ReservaEntity reservasListNewReservas : reservasListNew) {
                if (!reservasListOld.contains(reservasListNewReservas)) {
                    DepartamentoEntity oldDepartamentosIdDepartamento1OfReservasListNewReservas = reservasListNewReservas.getDepartamentosIdDepartamento1();
                    reservasListNewReservas.setDepartamentosIdDepartamento1(departamentos);
                    reservasListNewReservas = em.merge(reservasListNewReservas);
                    if (oldDepartamentosIdDepartamento1OfReservasListNewReservas != null && !oldDepartamentosIdDepartamento1OfReservasListNewReservas.equals(departamentos)) {
                        oldDepartamentosIdDepartamento1OfReservasListNewReservas.getReservasList().remove(reservasListNewReservas);
                        oldDepartamentosIdDepartamento1OfReservasListNewReservas = em.merge(oldDepartamentosIdDepartamento1OfReservasListNewReservas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = departamentos.getIdDepartamento();
                if (findDepartamentos(id) == null) {
                    throw new NonexistentEntityException("The departamentos with id " + id + " no longer exists.");
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
            DepartamentoEntity departamentos;
            try {
                departamentos = em.getReference(DepartamentoEntity.class, id);
                departamentos.getIdDepartamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamentos with id " + id + " no longer exists.", enfe);
            }
            List<Comodidades> comodidadesList = departamentos.getComodidadesList();
            for (Comodidades comodidadesListComodidades : comodidadesList) {
                comodidadesListComodidades.setDepartamentosIdDepartamento(null);
                comodidadesListComodidades = em.merge(comodidadesListComodidades);
            }
            List<ReservaEntity> reservasList = departamentos.getReservasList();
            for (ReservaEntity reservasListReservas : reservasList) {
                reservasListReservas.setDepartamentosIdDepartamento1(null);
                reservasListReservas = em.merge(reservasListReservas);
            }
            em.remove(departamentos);
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
     * @return List<DepartamentoEntity> allDepartamentos
     */
    public List<DepartamentoEntity> findDepartamentosEntities() {
        return findDepartamentosEntities(true, -1, -1);
    }

    /**
     * Metodo encargado de realizar una busqueda acotada de Entidades en la 
     * Base de datos segun los parametros ingresados 
     * 
     * @param int maxResults
     * @param int firstResult
     * @return List<DepartamentoEntity> listaDepartamentos
     */
    public List<DepartamentoEntity> findDepartamentosEntities(int maxResults, int firstResult) {
        return findDepartamentosEntities(false, maxResults, firstResult);
    }

    /**
     * Metodo general de busqueda acotada de Entidades en la 
     * Base de datos segun los parametros ingresados 
     *
     * @param boolean all
     * @param int maxResults
     * @param int firstResult
     * @return List<DepartamentoEntity> listaDepartamentos
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<DepartamentoEntity> findDepartamentosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DepartamentoEntity.class));
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
     * @return DepartamentoEntity departamento
     */
    public DepartamentoEntity findDepartamentos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DepartamentoEntity.class, id);
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
	public int getDepartamentosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DepartamentoEntity> rt = cq.from(DepartamentoEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
