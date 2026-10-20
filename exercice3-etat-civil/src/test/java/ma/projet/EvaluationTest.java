package ma.projet;
import ma.projet.beans.*;
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
    @Test public void populationAndOldest(){
        FemmeService fs=new FemmeService(emf);
        assertEquals(10,fs.findAll().size());assertEquals(5,new HommeService(emf).findAll().size());
        assertEquals("SALIMA",fs.plusAgee().getPrenom());
    }
    @Test public void wivesAndChildren(){
        HommeService hs=new HommeService(emf);FemmeService fs=new FemmeService(emf);
        assertEquals(3,hs.epousesEntre(ids[0],LocalDate.of(2010,1,1),LocalDate.of(2020,1,1)).size());
        assertEquals(4,fs.nombreEnfants(ids[1],LocalDate.of(1990,9,3),LocalDate.of(1990,9,3)));
        assertEquals(0,fs.nombreEnfants(ids[1],LocalDate.of(2010,1,1),LocalDate.of(2020,1,1)));
    }
    @Test public void namedAndCriteriaQueries(){
        FemmeService fs=new FemmeService(emf);HommeService hs=new HommeService(emf);
        assertEquals(1,fs.marieesDeuxFois().size());assertEquals(ids[3],fs.marieesDeuxFois().get(0).getId());
        assertEquals(1,hs.nombreHommesQuatreFemmes(LocalDate.of(2010,1,1),LocalDate.of(2020,12,31)));
        assertEquals(ids[2],hs.mariesQuatreFemmes(LocalDate.of(2010,1,1),LocalDate.of(2020,12,31)).get(0).getId());
        assertEquals(4,new MariageService(emf).parHomme(ids[0]).size());
    }
    @Test public void crudRollbackAndInvalidDates(){
        FemmeService fs=new FemmeService(emf);Femme f=new Femme("Temp","Test",LocalDate.of(2000,1,1));fs.create(f);
        f.setNom("Updated");fs.update(f);assertEquals("Updated",fs.findById(f.getId()).getNom());
        fs.delete(f);assertNull(fs.findById(f.getId()));
        assertThrows(IllegalStateException.class,()->fs.write(em->{em.persist(new Femme("Fail","Test",LocalDate.of(2000,1,1)));throw new IllegalStateException();}));
        assertEquals(10,fs.findAll().size());
        assertThrows(IllegalArgumentException.class,()->fs.nombreEnfants(ids[1],LocalDate.of(2020,1,2),LocalDate.of(2020,1,1)));
    }
}

