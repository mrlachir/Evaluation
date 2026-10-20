package ma.projet.classes;
import javax.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity @Table(name="projets")

public class Projet{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String nom;
    @Column(nullable=false) private LocalDate dateDebut;
    @ManyToOne(optional=false) private Employe responsable;
    public Projet(){}
    public Long getId(){return id;}
    public String getNom(){return nom;}
    public void setNom(String value){nom=value;}
    public LocalDate getDateDebut(){return dateDebut;}
    public void setDateDebut(LocalDate value){dateDebut=value;}
    public Employe getResponsable(){return responsable;}
    public void setResponsable(Employe value){responsable=value;}
    public Projet(String nom,LocalDate date,Employe responsable){this.nom=nom;dateDebut=date;this.responsable=responsable;}
    @Override public String toString(){return nom;}
}

