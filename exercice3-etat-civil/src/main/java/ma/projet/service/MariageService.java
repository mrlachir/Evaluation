package ma.projet.service;
import ma.projet.beans.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
public class MariageService extends AbstractService<Mariage>{
    public MariageService(){super(Mariage.class);}
    public MariageService(EntityManagerFactory emf){super(Mariage.class,emf);}
    public List<Mariage> parHomme(Long id){return read(em->em.createQuery("SELECT m FROM Mariage m JOIN FETCH m.femme WHERE m.homme.id=:id ORDER BY m.dateDebut,m.id",Mariage.class).setParameter("id",id).getResultList());}
    public void afficherMariages(Long id){
        Homme h=new HommeService(emf).findById(id);if(h==null)throw new IllegalArgumentException("Homme introuvable");
        List<Mariage> mariages=parHomme(id);
        System.out.println("Nom : "+h);
        System.out.println("Mariages En Cours :");
        mariages.stream().filter(m->m.getDateFin()==null).forEach(System.out::println);
        System.out.println("Mariages echoues :");
        mariages.stream().filter(m->m.getDateFin()!=null).forEach(System.out::println);
    }
}
