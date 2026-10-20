package ma.projet.classes;
import javax.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity @Table(name="commandes")

public class Commande{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private LocalDate dateCommande;
    public Commande(){}
    public Long getId(){return id;}
    public LocalDate getDateCommande(){return dateCommande;}
    public void setDateCommande(LocalDate value){dateCommande=value;}
    public Commande(LocalDate date){dateCommande=date;}
}

