package ma.projet.service;
import ma.projet.classes.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
public class CommandeService extends AbstractService<Commande>{
    public CommandeService(){super(Commande.class);}
    public CommandeService(EntityManagerFactory emf){super(Commande.class,emf);}

}
