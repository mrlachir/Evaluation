package ma.projet.service;
import ma.projet.classes.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
public class TacheService extends AbstractService<Tache>{
    public TacheService(){super(Tache.class);}
    public TacheService(EntityManagerFactory emf){super(Tache.class,emf);}
    public List<Tache> prixSuperieur1000(){return read(em->em.createNamedQuery("Tache.prixSuperieur1000",Tache.class).getResultList());}
    public List<Tache> realiseesEntre(LocalDate debut,LocalDate fin){
        dates(debut,fin);
        return read(em->em.createQuery("SELECT DISTINCT et.tache FROM EmployeTache et WHERE et.dateDebutReelle>=:debut AND et.dateFinReelle<=:fin",Tache.class).setParameter("debut",debut).setParameter("fin",fin).getResultList());
    }
}
