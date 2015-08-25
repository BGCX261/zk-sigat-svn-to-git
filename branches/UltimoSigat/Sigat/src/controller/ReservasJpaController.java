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
import entity.DepartamentoEntity;
import entity.ClienteEntity;
import entity.PasajeroEntity;
import java.util.ArrayList;
import java.util.List;
import entity.ReservaEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
 
/**
 *
 * @author Sebastian
 */
public class ReservasJpaController implements Serializable {

    public ReservasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ReservaEntity reservaEntity) {
        if (reservaEntity.getPasajerosList() == null) {
            reservaEntity.setPasajerosList(new ArrayList<PasajeroEntity>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DepartamentoEntity departamentosIdDepartamento1 = reservaEntity.getDepartamentosIdDepartamento1();
            if (departamentosIdDepartamento1 != null) {
                departamentosIdDepartamento1 = em.getReference(departamentosIdDepartamento1.getClass(), departamentosIdDepartamento1.getIdDepartamento());
                reservaEntity.setDepartamentosIdDepartamento1(departamentosIdDepartamento1);
            }
            ClienteEntity clientesIdclientes1 = reservaEntity.getClientesIdclientes1();
            if (clientesIdclientes1 != null) {
                clientesIdclientes1 = em.getReference(clientesIdclientes1.getClass(), clientesIdclientes1.getIdclientes());
                reservaEntity.setClientesIdclientes1(clientesIdclientes1);
            }
            List<PasajeroEntity> attachedPasajerosList = new ArrayList<PasajeroEntity>();
            for (PasajeroEntity pasajerosListPasajerosToAttach : reservaEntity.getPasajerosList()) {
                pasajerosListPasajerosToAttach = em.getReference(pasajerosListPasajerosToAttach.getClass(), pasajerosListPasajerosToAttach.getIdPasajeros());
                attachedPasajerosList.add(pasajerosListPasajerosToAttach);
            }
            reservaEntity.setPasajerosList(attachedPasajerosList);
            em.persist(reservaEntity);
            if (departamentosIdDepartamento1 != null) {
                departamentosIdDepartamento1.getReservasList().add(reservaEntity);
                departamentosIdDepartamento1 = em.merge(departamentosIdDepartamento1);
            }
            if (clientesIdclientes1 != null) {
                clientesIdclientes1.getReservasList().add(reservaEntity);
                clientesIdclientes1 = em.merge(clientesIdclientes1);
            }
            for (PasajeroEntity pasajerosListPasajeros : reservaEntity.getPasajerosList()) {
                pasajerosListPasajeros.getReservasList().add(reservaEntity);
                pasajerosListPasajeros = em.merge(pasajerosListPasajeros);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ReservaEntity reservaEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReservaEntity persistentReservas = em.find(ReservaEntity.class, reservaEntity.getIdReserva());
            DepartamentoEntity departamentosIdDepartamento1Old = persistentReservas.getDepartamentosIdDepartamento1();
            DepartamentoEntity departamentosIdDepartamento1New = reservaEntity.getDepartamentosIdDepartamento1();
            ClienteEntity clientesIdclientes1Old = persistentReservas.getClientesIdclientes1();
            ClienteEntity clientesIdclientes1New = reservaEntity.getClientesIdclientes1();
            List<PasajeroEntity> pasajerosListOld = persistentReservas.getPasajerosList();
            List<PasajeroEntity> pasajerosListNew = reservaEntity.getPasajerosList();
            if (departamentosIdDepartamento1New != null) {
                departamentosIdDepartamento1New = em.getReference(departamentosIdDepartamento1New.getClass(), departamentosIdDepartamento1New.getIdDepartamento());
                reservaEntity.setDepartamentosIdDepartamento1(departamentosIdDepartamento1New);
            }
            if (clientesIdclientes1New != null) {
                clientesIdclientes1New = em.getReference(clientesIdclientes1New.getClass(), clientesIdclientes1New.getIdclientes());
                reservaEntity.setClientesIdclientes1(clientesIdclientes1New);
            }
            List<PasajeroEntity> attachedPasajerosListNew = new ArrayList<PasajeroEntity>();
            for (PasajeroEntity pasajerosListNewPasajerosToAttach : pasajerosListNew) {
                pasajerosListNewPasajerosToAttach = em.getReference(pasajerosListNewPasajerosToAttach.getClass(), pasajerosListNewPasajerosToAttach.getIdPasajeros());
                attachedPasajerosListNew.add(pasajerosListNewPasajerosToAttach);
            }
            pasajerosListNew = attachedPasajerosListNew;
            reservaEntity.setPasajerosList(pasajerosListNew);
            reservaEntity = em.merge(reservaEntity);
            if (departamentosIdDepartamento1Old != null && !departamentosIdDepartamento1Old.equals(departamentosIdDepartamento1New)) {
                departamentosIdDepartamento1Old.getReservasList().remove(reservaEntity);
                departamentosIdDepartamento1Old = em.merge(departamentosIdDepartamento1Old);
            }
            if (departamentosIdDepartamento1New != null && !departamentosIdDepartamento1New.equals(departamentosIdDepartamento1Old)) {
                departamentosIdDepartamento1New.getReservasList().add(reservaEntity);
                departamentosIdDepartamento1New = em.merge(departamentosIdDepartamento1New);
            }
            if (clientesIdclientes1Old != null && !clientesIdclientes1Old.equals(clientesIdclientes1New)) {
                clientesIdclientes1Old.getReservasList().remove(reservaEntity);
                clientesIdclientes1Old = em.merge(clientesIdclientes1Old);
            }
            if (clientesIdclientes1New != null && !clientesIdclientes1New.equals(clientesIdclientes1Old)) {
                clientesIdclientes1New.getReservasList().add(reservaEntity);
                clientesIdclientes1New = em.merge(clientesIdclientes1New);
            }
            for (PasajeroEntity pasajerosListOldPasajeros : pasajerosListOld) {
                if (!pasajerosListNew.contains(pasajerosListOldPasajeros)) {
                    pasajerosListOldPasajeros.getReservasList().remove(reservaEntity);
                    pasajerosListOldPasajeros = em.merge(pasajerosListOldPasajeros);
                }
            }
            for (PasajeroEntity pasajerosListNewPasajeros : pasajerosListNew) {
                if (!pasajerosListOld.contains(pasajerosListNewPasajeros)) {
                    pasajerosListNewPasajeros.getReservasList().add(reservaEntity);
                    pasajerosListNewPasajeros = em.merge(pasajerosListNewPasajeros);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reservaEntity.getIdReserva();
                if (findReservas(id) == null) {
                    throw new NonexistentEntityException("The reservas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReservaEntity reservaEntity;
            try {
                reservaEntity = em.getReference(ReservaEntity.class, id);
                reservaEntity.getIdReserva();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservas with id " + id + " no longer exists.", enfe);
            }
            DepartamentoEntity departamentosIdDepartamento1 = reservaEntity.getDepartamentosIdDepartamento1();
            if (departamentosIdDepartamento1 != null) {
                departamentosIdDepartamento1.getReservasList().remove(reservaEntity);
                departamentosIdDepartamento1 = em.merge(departamentosIdDepartamento1);
            }
            ClienteEntity clientesIdclientes1 = reservaEntity.getClientesIdclientes1();
            if (clientesIdclientes1 != null) {
                clientesIdclientes1.getReservasList().remove(reservaEntity);
                clientesIdclientes1 = em.merge(clientesIdclientes1);
            }
            List<PasajeroEntity> pasajerosList = reservaEntity.getPasajerosList();
            for (PasajeroEntity pasajerosListPasajeros : pasajerosList) {
                pasajerosListPasajeros.getReservasList().remove(reservaEntity);
                pasajerosListPasajeros = em.merge(pasajerosListPasajeros);
            }
            em.remove(reservaEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ReservaEntity> findReservasEntities() {
        return findReservasEntities(true, -1, -1);
    }

    public List<ReservaEntity> findReservasEntities(int maxResults, int firstResult) {
        return findReservasEntities(false, maxResults, firstResult);
    }

    private List<ReservaEntity> findReservasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ReservaEntity.class));
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

    public ReservaEntity findReservas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ReservaEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ReservaEntity> rt = cq.from(ReservaEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
