package ma.projet.service;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import javax.persistence.*;
import java.util.*;
import java.util.function.Function;

public class AbstractService<T> implements IDao<T>{
    protected final EntityManagerFactory emf;
    private final Class<T> type;
    public AbstractService(Class<T> type){this(type,HibernateUtil.getSessionFactory());}
    public AbstractService(Class<T> type,EntityManagerFactory emf){this.type=type;this.emf=emf;}
    public <R> R read(Function<EntityManager,R> action){
        EntityManager em=emf.createEntityManager();
        try{return action.apply(em);}finally{em.close();}
    }
    public <R> R write(Function<EntityManager,R> action){
        EntityManager em=emf.createEntityManager();EntityTransaction tx=em.getTransaction();
        try{tx.begin();R result=action.apply(em);tx.commit();return result;}
        catch(RuntimeException e){if(tx.isActive())try{tx.rollback();}catch(RuntimeException r){e.addSuppressed(r);}throw e;}
        finally{em.close();}
    }
    public void create(T entity){Objects.requireNonNull(entity);write(em->{em.persist(entity);return null;});}
    public T findById(Long id){return read(em->em.find(type,id));}
    public List<T> findAll(){return read(em->em.createQuery("SELECT e FROM "+em.getMetamodel().entity(type).getName()+" e ORDER BY e.id",type).getResultList());}
    public T update(T entity){Objects.requireNonNull(entity);return write(em->em.merge(entity));}
    public void delete(T entity){Objects.requireNonNull(entity);write(em->{em.remove(em.merge(entity));return null;});}
    protected static void dates(java.time.LocalDate debut,java.time.LocalDate fin){
        if(debut==null||fin==null||debut.isAfter(fin))throw new IllegalArgumentException("Intervalle invalide");
    }
}
