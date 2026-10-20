package ma.projet.classes;
import javax.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity @Table(name="employes")

public class Employe{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String nom;
    public Employe(){}
    public Long getId(){return id;}
    public String getNom(){return nom;}
    public void setNom(String value){nom=value;}
    public Employe(String nom){this.nom=nom;}
    @Override public String toString(){return nom;}
}

