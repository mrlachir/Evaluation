package ma.projet.beans;
import javax.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity @Table(name="femmes")
@NamedQuery(name="Femme.marieesDeuxFois",query="SELECT f FROM Femme f WHERE (SELECT COUNT(m) FROM Mariage m WHERE m.femme=f)>=2 ORDER BY f.id")
@NamedNativeQuery(name="Femme.nombreEnfants",query="SELECT COALESCE(SUM(m.nombreEnfants),0) FROM mariages m WHERE m.femme_id=:femmeId AND m.dateDebut>=:debut AND m.dateDebut<=:fin")
public class Femme{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String nom;
    @Column(nullable=false) private String prenom;
    @Column(nullable=false) private LocalDate dateNaissance;
    public Femme(){}
    public Long getId(){return id;}
    public String getNom(){return nom;}
    public void setNom(String value){nom=value;}
    public String getPrenom(){return prenom;}
    public void setPrenom(String value){prenom=value;}
    public LocalDate getDateNaissance(){return dateNaissance;}
    public void setDateNaissance(LocalDate value){dateNaissance=value;}
    public Femme(String nom,String prenom,LocalDate date){this.nom=nom;this.prenom=prenom;dateNaissance=date;}
    @Override public String toString(){return nom+" "+prenom;}
}

