package ma.projet.classes;
import javax.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity @Table(name="employetaches")

public class EmployeTache{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Employe employe;
    @ManyToOne(optional=false) private Tache tache;
    @Column(nullable=false) private LocalDate dateDebutReelle;
     private LocalDate dateFinReelle;
    public EmployeTache(){}
    public Long getId(){return id;}
    public Employe getEmploye(){return employe;}
    public void setEmploye(Employe value){employe=value;}
    public Tache getTache(){return tache;}
    public void setTache(Tache value){tache=value;}
    public LocalDate getDateDebutReelle(){return dateDebutReelle;}
    public void setDateDebutReelle(LocalDate value){dateDebutReelle=value;}
    public LocalDate getDateFinReelle(){return dateFinReelle;}
    public void setDateFinReelle(LocalDate value){dateFinReelle=value;}
    public EmployeTache(Employe e,Tache t,LocalDate debut,LocalDate fin){employe=e;tache=t;dateDebutReelle=debut;dateFinReelle=fin;}
    @PrePersist @PreUpdate private void validate(){if(dateDebutReelle==null||(dateFinReelle!=null&&dateFinReelle.isBefore(dateDebutReelle)))throw new IllegalArgumentException("Dates reelles invalides");}
    @Override public String toString(){return tache.getId()+" "+tache.getNom()+" "+dateDebutReelle+" "+dateFinReelle;}
}

