/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.ReservaEntity;
import java.util.ArrayList;
import java.util.List;
import entity.DepartamentoEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
 
/**
 *
 * @author Sebastian
 */
public class DepartamentosJpaController implements Serializable {

    public DepartamentosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DepartamentoEntity departamentoEntity) {
        if (departamentoEntity.getReservasList() == null) {
            departamentoEntity.setReservasList(new ArrayList<ReservaEntity>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ReservaEntity> attachedReservasList = new ArrayList<ReservaEntity>();
            for (ReservaEntity reservasListReservasToAttach : departamentoEntity.getReservasList()) {
                reservasListReservasToAttach = em.getReference(reservasListReservasToAttach.getClass(), reservasListReservasToAttach.getIdReserva());
                attachedReservasList.add(reservasListReservasToAttach);
            }
            departamentoEntity.setReservasList(attachedReservasList);
            em.persist(departamentoEntity);
            for (ReservaEntity reservasListReservas : departamentoEntity.getReservasList()) {
                DepartamentoEntity oldDepartamentosIdDepartamento1OfReservasListReservas = reservasListReservas.getDepartamentosIdDepartamento1();
                reservasListReservas.setDepartamentosIdDepartamento1(departamentoEntity);
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

    public void edit(DepartamentoEntity departamentoEntity) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DepartamentoEntity persistentDepartamentos = em.find(DepartamentoEntity.class, departamentoEntity.getIdDepartamento());
            List<ReservaEntity> reservasListOld = persistentDepartamentos.getReservasList();
            List<ReservaEntity> reservasListNew = departamentoEntity.getReservasList();
            List<String> illegalOrphanMessages = null;
            for (ReservaEntity reservasListOldReservas : reservasListOld) {
                if (!reservasListNew.contains(reservasListOldReservas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ReservaEntity " + reservasListOldReservas + " since its departamentosIdDepartamento1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ReservaEntity> attachedReservasListNew = new ArrayList<ReservaEntity>();
            for (ReservaEntity reservasListNewReservasToAttach : reservasListNew) {
                reservasListNewReservasToAttach = em.getReference(reservasListNewReservasToAttach.getClass(), reservasListNewReservasToAttach.getIdReserva());
                attachedReservasListNew.add(reservasListNewReservasToAttach);
            }
            reservasListNew = attachedReservasListNew;
            departamentoEntity.setReservasList(reservasListNew);
            departamentoEntity = em.merge(departamentoEntity);
            for (ReservaEntity reservasListNewReservas : reservasListNew) {
                if (!reservasListOld.contains(reservasListNewReservas)) {
                    DepartamentoEntity oldDepartamentosIdDepartamento1OfReservasListNewReservas = reservasListNewReservas.getDepartamentosIdDepartamento1();
                    reservasListNewReservas.setDepartamentosIdDepartamento1(departamentoEntity);
                    reservasListNewReservas = em.merge(reservasListNewReservas);
                    if (oldDepartamentosIdDepartamento1OfReservasListNewReservas != null && !oldDepartamentosIdDepartamento1OfReservasListNewReservas.equals(departamentoEntity)) {
                        oldDepartamentosIdDepartamento1OfReservasListNewReservas.getReservasList().remove(reservasListNewReservas);
                        oldDepartamentosIdDepartamento1OfReservasListNewReservas = em.merge(oldDepartamentosIdDepartamento1OfReservasListNewReservas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = departamentoEntity.getIdDepartamento();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DepartamentoEntity departamentoEntity;
            try {
                departamentoEntity = em.getReference(DepartamentoEntity.class, id);
                departamentoEntity.getIdDepartamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamentos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ReservaEntity> reservasListOrphanCheck = departamentoEntity.getReservasList();
            for (ReservaEntity reservasListOrphanCheckReservas : reservasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DepartamentoEntity (" + departamentoEntity + ") cannot be destroyed since the ReservaEntity " + reservasListOrphanCheckReservas + " in its reservasList field has a non-nullable departamentosIdDepartamento1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(departamentoEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DepartamentoEntity> findDepartamentosEntities() {
        return findDepartamentosEntities(true, -1, -1);
    }

    public List<DepartamentoEntity> findDepartamentosEntities(int maxResults, int firstResult) {
        return findDepartamentosEntities(false, maxResults, firstResult);
    }

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

    public DepartamentoEntity findDepartamentos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DepartamentoEntity.class, id);
        } finally {
            em.close();
        }
    }

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
