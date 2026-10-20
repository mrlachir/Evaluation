package ma.projet.classes;
import javax.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity @Table(name="taches")
@NamedQuery(name="Tache.prixSuperieur1000",query="SELECT t FROM Tache t WHERE t.prix > 1000 ORDER BY t.id")
public class Tache{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String nom;
    @Column(nullable=false,precision=12,scale=2) private BigDecimal prix;
    @Column(nullable=false) private LocalDate dateDebut;
    @Column(nullable=false) private LocalDate dateFin;
    @ManyToOne(optional=false) private Projet projet;
    public Tache(){}
    public Long getId(){return id;}
    public String getNom(){return nom;}
    public void setNom(String value){nom=value;}
    public BigDecimal getPrix(){return prix;}
    public void setPrix(BigDecimal value){prix=value;}
    public LocalDate getDateDebut(){return dateDebut;}
    public void setDateDebut(LocalDate value){dateDebut=value;}
    public LocalDate getDateFin(){return dateFin;}
    public void setDateFin(LocalDate value){dateFin=value;}
    public Projet getProjet(){return projet;}
    public void setProjet(Projet value){projet=value;}
    public Tache(String nom,BigDecimal prix,LocalDate debut,LocalDate fin,Projet projet){this.nom=nom;this.prix=prix;dateDebut=debut;dateFin=fin;this.projet=projet;}
    @PrePersist @PreUpdate private void validate(){if(prix==null||prix.signum()<0||dateDebut==null||dateFin==null||dateDebut.isAfter(dateFin))throw new IllegalArgumentException("Tache invalide");}
    @Override public String toString(){return id+" "+nom+" "+prix+" DH";}
}

