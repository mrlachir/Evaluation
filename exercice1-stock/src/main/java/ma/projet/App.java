package ma.projet;
import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.time.LocalDate;

public class App{
    public static Long[] seed(EntityManagerFactory emf){
        CategorieService cats=new CategorieService(emf);
        Categorie info=new Categorie("Informatique"),autre=new Categorie("Accessoires");cats.create(info);cats.create(autre);
        ProduitService ps=new ProduitService(emf);
        Produit a=new Produit("ES12",new BigDecimal("120"),info),b=new Produit("ZR85",new BigDecimal("100"),info),
            c=new Produit("EE85",new BigDecimal("200"),info),d=new Produit("USB1",new BigDecimal("50"),autre);
        for(Produit p:new Produit[]{a,b,c,d})ps.create(p);
        CommandeService cs=new CommandeService(emf);
        Commande cmd=new Commande(LocalDate.of(2013,3,14)),outside=new Commande(LocalDate.of(2014,1,1));cs.create(cmd);cs.create(outside);
        LigneCommandeService lines=new LigneCommandeService(emf);
        lines.create(new LigneCommande(cmd,a,7));lines.create(new LigneCommande(cmd,b,14));lines.create(new LigneCommande(cmd,c,5));
        lines.create(new LigneCommande(outside,d,1));
        return new Long[]{info.getId(),cmd.getId(),a.getId()};
    }
    public static void main(String[] args){
        try{
            EntityManagerFactory emf=HibernateUtil.getSessionFactory();Long[] ids=seed(emf);ProduitService ps=new ProduitService(emf);
            System.out.println("=== PRODUITS PAR CATEGORIE ===");System.out.println(ps.parCategorie(ids[0]));
            System.out.println("=== COMMANDES ENTRE DEUX DATES ===");System.out.println(ps.commandesEntre(LocalDate.of(2013,3,1),LocalDate.of(2013,3,31)));
            ps.afficherCommande(ids[1]);
            System.out.println("=== PRIX STRICTEMENT > 100 : NAMED QUERY ===");System.out.println(ps.prixSuperieur100());
            Produit p=ps.findById(ids[2]);p.setPrix(new BigDecimal("125"));ps.update(p);
            System.out.println("CRUD UPDATE : "+ps.findById(ids[2]));System.out.println("DEMO TERMINEE");
        }finally{HibernateUtil.shutdown();}
    }
}
