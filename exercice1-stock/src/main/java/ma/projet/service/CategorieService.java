package ma.projet.service;
import ma.projet.classes.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
public class CategorieService extends AbstractService<Categorie>{
    public CategorieService(){super(Categorie.class);}
    public CategorieService(EntityManagerFactory emf){super(Categorie.class,emf);}

}
