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
    @Test public void employeeAndProjectQueries(){
        EmployeService es=new EmployeService(emf);ProjetService ps=new ProjetService(emf);
        assertEquals(3,es.tachesRealisees(ids[0]).size());assertEquals(1,es.projetsGeres(ids[0]).size());
        assertEquals(4,ps.tachesPlanifiees(ids[1]).size());assertEquals(3,ps.tachesRealisees(ids[1]).size());
        assertEquals(0,new BigDecimal("5700").compareTo(ps.coutGlobal(ids[1])));
    }
    @Test public void taskNamedAndDateQueries(){
        TacheService ts=new TacheService(emf);
        assertEquals(2,ts.prixSuperieur1000().size());
        assertEquals(2,ts.realiseesEntre(LocalDate.of(2013,2,1),LocalDate.of(2013,3,31)).size());
        assertEquals(0,ts.realiseesEntre(LocalDate.of(2020,1,1),LocalDate.of(2020,12,31)).size());
    }
    @Test public void genericCrud(){
        EmployeService es=new EmployeService(emf);Employe e=new Employe("Temp");es.create(e);
        e.setNom("Updated");es.update(e);assertEquals("Updated",es.findById(e.getId()).getNom());
        es.delete(e);assertNull(es.findById(e.getId()));
    }
    @Test public void rollbackAndInvalidPeriod(){
        EmployeService es=new EmployeService(emf);
        assertThrows(IllegalStateException.class,()->es.write(em->{em.persist(new Employe("Rollback"));throw new IllegalStateException();}));
        assertEquals(2,es.findAll().size());
        assertThrows(IllegalArgumentException.class,()->new TacheService(emf).realiseesEntre(LocalDate.of(2020,1,2),LocalDate.of(2020,1,1)));
    }
}

