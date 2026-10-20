package ma.projet;
import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.Assert.*;

public class EvaluationTest{
    private SessionFactory emf;
    private Long[] ids;
    @Before public void setup(){emf=HibernateUtil.build(true,"test_"+UUID.randomUUID());ids=App.seed(emf);}
    @After public void close(){if(emf!=null)emf.close();}
    @Test public void stockQueries(){
        ProduitService ps=new ProduitService(emf);
        assertEquals(3,ps.parCategorie(ids[0]).size());
        assertEquals(3,ps.commandesEntre(LocalDate.of(2013,3,14),LocalDate.of(2013,3,14)).size());
        assertEquals(0,ps.commandesEntre(LocalDate.of(2012,1,1),LocalDate.of(2012,12,31)).size());
        assertEquals(2,ps.prixSuperieur100().size());
    }
    @Test public void orderLinesAndQuantities(){
        java.util.List<LigneCommande> lines=new ProduitService(emf).dansCommande(ids[1]);
        assertEquals(3,lines.size());assertEquals(7,lines.get(0).getQuantite());
        assertEquals(14,lines.get(1).getQuantite());assertEquals(5,lines.get(2).getQuantite());
        assertEquals("ES12",lines.get(0).getProduit().getReference());
    }
    @Test public void crudAndRollback(){
        CategorieService cs=new CategorieService(emf);Categorie c=new Categorie("Temp");cs.create(c);
        c.setNom("Updated");cs.update(c);assertEquals("Updated",cs.findById(c.getId()).getNom());
        cs.delete(c);assertNull(cs.findById(c.getId()));
        assertThrows(IllegalStateException.class,()->cs.write(em->{em.persist(new Categorie("Rollback"));throw new IllegalStateException();}));
        assertEquals(2,cs.findAll().size());
    }
    @Test public void invalidValuesRejected(){
        ProduitService ps=new ProduitService(emf);
        assertThrows(IllegalArgumentException.class,()->ps.commandesEntre(LocalDate.of(2020,1,2),LocalDate.of(2020,1,1)));
        Produit p=new Produit("BAD",new BigDecimal("-1"),new CategorieService(emf).findById(ids[0]));
        assertThrows(RuntimeException.class,()->ps.create(p));
        assertEquals(4,ps.findAll().size());
    }
}

