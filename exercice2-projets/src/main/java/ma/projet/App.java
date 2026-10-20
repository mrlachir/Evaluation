package ma.projet;
import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.time.LocalDate;

public class App{
    public static Long[] seed(EntityManagerFactory emf){
        EmployeService es=new EmployeService(emf);Employe alice=new Employe("Alice"),bob=new Employe("Bob");es.create(alice);es.create(bob);
        ProjetService ps=new ProjetService(emf);Projet projet=new Projet("Gestion de stock",LocalDate.of(2013,1,14),alice);ps.create(projet);
        TacheService ts=new TacheService(emf);EmployeTacheService ets=new EmployeTacheService(emf);
        String[] noms={"Analyse","Conception","Developpement","Documentation"};
        for(int i=0;i<4;i++){
            LocalDate debut=LocalDate.of(2013,2+i,10),fin=debut.plusDays(10);
            Tache t=new Tache(noms[i],new BigDecimal(new String[]{"1200","1000","3000","500"}[i]),debut,fin,projet);ts.create(t);
            ets.create(new EmployeTache(i<3?alice:bob,t,debut,i<3?fin:null));
        }
        return new Long[]{alice.getId(),projet.getId()};
    }
    public static void main(String[] args){
        try{
            EntityManagerFactory emf=HibernateUtil.getSessionFactory();Long[] ids=seed(emf);
            EmployeService es=new EmployeService(emf);ProjetService ps=new ProjetService(emf);TacheService ts=new TacheService(emf);
            System.out.println("=== TACHES REALISEES PAR EMPLOYE ===");System.out.println(es.tachesRealisees(ids[0]));
            System.out.println("=== PROJETS GERES ===");System.out.println(es.projetsGeres(ids[0]));
            System.out.println("=== TACHES PLANIFIEES ===");System.out.println(ps.tachesPlanifiees(ids[1]));
            ps.afficherRealisees(ids[1]);
            System.out.println("=== PRIX STRICTEMENT > 1000 ===");System.out.println(ts.prixSuperieur1000());
            System.out.println("=== REALISEES ENTRE DEUX DATES ===");System.out.println(ts.realiseesEntre(LocalDate.of(2013,2,1),LocalDate.of(2013,3,31)));
            System.out.println("COUT GLOBAL : "+ps.coutGlobal(ids[1])+" DH");System.out.println("DEMO TERMINEE");
        }finally{HibernateUtil.shutdown();}
    }
}
