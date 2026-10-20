package ma.projet.classes;
import javax.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity @Table(name="produits")
@NamedQuery(name="Produit.prixSuperieur100",query="SELECT p FROM Produit p WHERE p.prix > 100 ORDER BY p.reference")
public class Produit{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,unique=true) private String reference;
    @Column(nullable=false,precision=12,scale=2) private BigDecimal prix;
    @ManyToOne(optional=false) @JoinColumn(name="categorie_id",nullable=false) private Categorie categorie;
    public Produit(){}
    public Long getId(){return id;}
    public String getReference(){return reference;}
    public void setReference(String value){reference=value;}
    public BigDecimal getPrix(){return prix;}
    public void setPrix(BigDecimal value){prix=value;}
    public Categorie getCategorie(){return categorie;}
    public void setCategorie(Categorie value){categorie=value;}
    public Produit(String ref,BigDecimal prix,Categorie cat){reference=ref;this.prix=prix;categorie=cat;}
    @PrePersist @PreUpdate private void validate(){if(prix==null||prix.signum()<0)throw new IllegalArgumentException("Prix invalide");}
    @Override public String toString(){return reference+" "+prix+" DH";}
}

