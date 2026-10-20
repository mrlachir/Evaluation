package ma.projet.service;
import ma.projet.classes.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
public class LigneCommandeService extends AbstractService<LigneCommande>{
    public LigneCommandeService(){super(LigneCommande.class);}
    public LigneCommandeService(EntityManagerFactory emf){super(LigneCommande.class,emf);}

}
