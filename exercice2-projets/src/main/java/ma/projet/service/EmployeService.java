package ma.projet.service;
import ma.projet.classes.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
public class EmployeService extends AbstractService<Employe>{
    public EmployeService(){super(Employe.class);}
    public EmployeService(EntityManagerFactory emf){super(Employe.class,emf);}
    public List<Tache> tachesRealisees(Long id){return read(em->em.createQuery("SELECT DISTINCT et.tache FROM EmployeTache et WHERE et.employe.id=:id AND et.dateFinReelle IS NOT NULL",Tache.class).setParameter("id",id).getResultList());}
    public List<Projet> projetsGeres(Long id){return read(em->em.createQuery("SELECT p FROM Projet p WHERE p.responsable.id=:id ORDER BY p.id",Projet.class).setParameter("id",id).getResultList());}
}
