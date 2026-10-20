package ma.projet;
import ma.projet.beans.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;

public class App{
    public static Long[] seed(EntityManagerFactory emf){
        FemmeService fs=new FemmeService(emf);HommeService hs=new HommeService(emf);MariageService ms=new MariageService(emf);
        Femme[] femmes=new Femme[10];Homme[] hommes=new Homme[5];
        String[] noms={"RAMI","ALI","ALAOUI","ALAMI","BENALI","IDRISSI","FASSI","AMRANI","TAZI","NACIRI"};
        String[] prenoms={"SALIMA","AMAL","WAFA","KARIMA","NORA","SARA","LINA","HANA","LEILA","AMINA"};
        for(int i=0;i<10;i++){femmes[i]=new Femme(noms[i],prenoms[i],LocalDate.of(1960+i,1,1));fs.create(femmes[i]);}
        for(int i=0;i<5;i++){hommes[i]=new Homme(i==0?"SAFI":"HOMME"+i,i==0?"SAID":"Prenom"+i,LocalDate.of(1955+i,1,1));hs.create(hommes[i]);}
        ms.create(new Mariage(hommes[0],femmes[3],LocalDate.of(1989,9,3),LocalDate.of(1990,9,3),0));
        ms.create(new Mariage(hommes[0],femmes[0],LocalDate.of(1990,9,3),null,4));
        ms.create(new Mariage(hommes[0],femmes[1],LocalDate.of(1995,9,3),null,2));
        ms.create(new Mariage(hommes[0],femmes[2],LocalDate.of(2000,11,4),null,3));
        ms.create(new Mariage(hommes[1],femmes[3],LocalDate.of(1992,1,1),null,1));
        for(int i=4;i<8;i++)ms.create(new Mariage(hommes[2],femmes[i],LocalDate.of(2000+i,1,1),null,1));
        return new Long[]{hommes[0].getId(),femmes[0].getId(),hommes[2].getId(),femmes[3].getId()};
    }
    public static void main(String[] args){
        try{
            EntityManagerFactory emf=HibernateUtil.getSessionFactory();Long[] ids=seed(emf);
            FemmeService fs=new FemmeService(emf);HommeService hs=new HommeService(emf);
            System.out.println("=== 10 FEMMES, 5 HOMMES ===");System.out.println(fs.findAll());
            System.out.println("PLUS AGEE : "+fs.plusAgee());
            System.out.println("=== EPOUSES ENTRE DATES ===");System.out.println(hs.epousesEntre(ids[0],LocalDate.of(1990,1,1),LocalDate.of(2020,12,31)));
            System.out.println("ENFANTS : "+fs.nombreEnfants(ids[1],LocalDate.of(1980,1,1),LocalDate.of(2020,12,31)));
            System.out.println("=== FEMMES MARIEES AU MOINS DEUX FOIS ===");System.out.println(fs.marieesDeuxFois());
            System.out.println("=== CRITERIA : QUATRE FEMMES SUR INTERVALLE 2010..2020 ===");
            System.out.println(hs.mariesQuatreFemmes(LocalDate.of(2010,1,1),LocalDate.of(2020,12,31)));
            System.out.println("NOMBRE : "+hs.nombreHommesQuatreFemmes(LocalDate.of(2010,1,1),LocalDate.of(2020,12,31)));
            new MariageService(emf).afficherMariages(ids[0]);System.out.println("DEMO TERMINEE");
        }finally{HibernateUtil.shutdown();}
    }
}
