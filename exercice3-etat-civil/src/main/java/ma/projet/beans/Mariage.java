package ma.projet.beans;
import javax.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity @Table(name="mariages")

public class Mariage{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="homme_id",nullable=false) private Homme homme;
    @ManyToOne(optional=false) @JoinColumn(name="femme_id",nullable=false) private Femme femme;
    @Column(nullable=false) private LocalDate dateDebut;
     private LocalDate dateFin;
    @Column(nullable=false) private int nombreEnfants;
    public Mariage(){}
    public Long getId(){return id;}
    public Homme getHomme(){return homme;}
    public void setHomme(Homme value){homme=value;}
    public Femme getFemme(){return femme;}
    public void setFemme(Femme value){femme=value;}
    public LocalDate getDateDebut(){return dateDebut;}
    public void setDateDebut(LocalDate value){dateDebut=value;}
    public LocalDate getDateFin(){return dateFin;}
    public void setDateFin(LocalDate value){dateFin=value;}
    public int getNombreEnfants(){return nombreEnfants;}
    public void setNombreEnfants(int value){nombreEnfants=value;}
    public Mariage(Homme h,Femme f,LocalDate debut,LocalDate fin,int enfants){homme=h;femme=f;dateDebut=debut;dateFin=fin;nombreEnfants=enfants;}
    @PrePersist @PreUpdate private void validate(){if(dateDebut==null||(dateFin!=null&&dateFin.isBefore(dateDebut))||nombreEnfants<0)throw new IllegalArgumentException("Mariage invalide");}
    @Override public String toString(){return "Femme : "+femme+" Date Debut : "+dateDebut+" Date Fin : "+dateFin+" Nbr Enfants : "+nombreEnfants;}
}

