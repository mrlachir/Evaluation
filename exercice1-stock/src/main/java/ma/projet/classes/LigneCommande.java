package ma.projet.classes;
import javax.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity @Table(name="lignecommandes",uniqueConstraints=@UniqueConstraint(columnNames={"commande_id","produit_id"}))

public class LigneCommande{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Commande commande;
    @ManyToOne(optional=false) private Produit produit;
    @Column(nullable=false) private int quantite;
    public LigneCommande(){}
    public Long getId(){return id;}
    public Commande getCommande(){return commande;}
    public void setCommande(Commande value){commande=value;}
    public Produit getProduit(){return produit;}
    public void setProduit(Produit value){produit=value;}
    public int getQuantite(){return quantite;}
    public void setQuantite(int value){quantite=value;}
    public LigneCommande(Commande c,Produit p,int q){commande=c;produit=p;quantite=q;}
    @PrePersist @PreUpdate private void validate(){if(quantite<1)throw new IllegalArgumentException("Quantite positive requise");}
    @Override public String toString(){return produit.getReference()+"\t"+produit.getPrix()+" DH\t"+quantite;}
}

