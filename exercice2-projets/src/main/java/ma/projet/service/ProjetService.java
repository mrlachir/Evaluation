package ma.projet.service;
import ma.projet.classes.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
public class ProjetService extends AbstractService<Projet>{
    public ProjetService(){super(Projet.class);}
    public ProjetService(EntityManagerFactory emf){super(Projet.class,emf);}
    public List<Tache> tachesPlanifiees(Long id){return read(em->em.createQuery("SELECT t FROM Tache t WHERE t.projet.id=:id ORDER BY t.id",Tache.class).setParameter("id",id).getResultList());}
    public List<EmployeTache> tachesRealisees(Long id){return read(em->em.createQuery("SELECT et FROM EmployeTache et JOIN FETCH et.tache WHERE et.tache.projet.id=:id AND et.dateFinReelle IS NOT NULL ORDER BY et.tache.id,et.id",EmployeTache.class).setParameter("id",id).getResultList());}
    public java.math.BigDecimal coutGlobal(Long id){return read(em->{
        java.math.BigDecimal result=em.createQuery("SELECT SUM(t.prix) FROM Tache t WHERE t.projet.id=:id",java.math.BigDecimal.class).setParameter("id",id).getSingleResult();
        return result==null?java.math.BigDecimal.ZERO:result;
    });}
    public void afficherRealisees(Long id){
        Projet p=findById(id);if(p==null)throw new IllegalArgumentException("Projet introuvable");
        System.out.println("Projet : "+id+" Nom : "+p.getNom()+" Date debut : "+p.getDateDebut());
        System.out.println("Num Nom Date Debut Reelle Date Fin Reelle");
        tachesRealisees(id).forEach(System.out::println);
    }
}
