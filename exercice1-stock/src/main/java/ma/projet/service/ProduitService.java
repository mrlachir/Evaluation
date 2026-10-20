package ma.projet.service;
import ma.projet.classes.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
public class ProduitService extends AbstractService<Produit>{
    public ProduitService(){super(Produit.class);}
    public ProduitService(EntityManagerFactory emf){super(Produit.class,emf);}
    public List<Produit> parCategorie(Long id){return read(em->em.createQuery("SELECT p FROM Produit p WHERE p.categorie.id=:id ORDER BY p.reference",Produit.class).setParameter("id",id).getResultList());}
    public List<Produit> commandesEntre(LocalDate debut,LocalDate fin){
        dates(debut,fin);
        return read(em->em.createQuery("SELECT DISTINCT l.produit FROM LigneCommande l WHERE l.commande.dateCommande BETWEEN :debut AND :fin",Produit.class).setParameter("debut",debut).setParameter("fin",fin).getResultList());
    }
    public List<LigneCommande> dansCommande(Long id){return read(em->em.createQuery("SELECT l FROM LigneCommande l JOIN FETCH l.produit JOIN FETCH l.commande WHERE l.commande.id=:id ORDER BY l.id",LigneCommande.class).setParameter("id",id).getResultList());}
    public List<Produit> prixSuperieur100(){return read(em->em.createNamedQuery("Produit.prixSuperieur100",Produit.class).getResultList());}
    public void afficherCommande(Long id){
        Commande c=new CommandeService(emf).findById(id);
        if(c==null)throw new IllegalArgumentException("Commande introuvable");
        System.out.println("Commande : "+id+" Date : "+c.getDateCommande());
        System.out.println("Reference\tPrix\tQuantite");
        dansCommande(id).forEach(System.out::println);
    }
}
