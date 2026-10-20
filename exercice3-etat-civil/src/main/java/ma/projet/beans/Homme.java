package ma.projet.beans;
import javax.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity @Table(name="hommes")

public class Homme{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String nom;
    @Column(nullable=false) private String prenom;
    @Column(nullable=false) private LocalDate dateNaissance;
    public Homme(){}
    public Long getId(){return id;}
    public String getNom(){return nom;}
    public void setNom(String value){nom=value;}
    public String getPrenom(){return prenom;}
    public void setPrenom(String value){prenom=value;}
    public LocalDate getDateNaissance(){return dateNaissance;}
    public void setDateNaissance(LocalDate value){dateNaissance=value;}
    public Homme(String nom,String prenom,LocalDate date){this.nom=nom;this.prenom=prenom;dateNaissance=date;}
    @Override public String toString(){return nom+" "+prenom;}
}

