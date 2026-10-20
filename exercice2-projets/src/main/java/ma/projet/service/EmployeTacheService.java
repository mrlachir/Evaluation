package ma.projet.service;
import ma.projet.classes.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
public class EmployeTacheService extends AbstractService<EmployeTache>{
    public EmployeTacheService(){super(EmployeTache.class);}
    public EmployeTacheService(EntityManagerFactory emf){super(EmployeTache.class,emf);}

}
