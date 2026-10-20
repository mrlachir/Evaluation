package ma.projet.service;
import ma.projet.beans.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
public class FemmeService extends AbstractService<Femme>{
    public FemmeService(){super(Femme.class);}
    public FemmeService(EntityManagerFactory emf){super(Femme.class,emf);}
    public Femme plusAgee(){return read(em->em.createQuery("SELECT f FROM Femme f ORDER BY f.dateNaissance,f.id",Femme.class).setMaxResults(1).getResultStream().findFirst().orElse(null));}
    // Children are aggregated for marriages whose start dates lie in the inclusive interval.
    public long nombreEnfants(Long id,LocalDate debut,LocalDate fin){
        dates(debut,fin);
        return read(em->((Number)em.createNamedQuery("Femme.nombreEnfants").setParameter("femmeId",id).setParameter("debut",debut).setParameter("fin",fin).getSingleResult()).longValue());
    }
    public List<Femme> marieesDeuxFois(){return read(em->em.createNamedQuery("Femme.marieesDeuxFois",Femme.class).getResultList());}
}
