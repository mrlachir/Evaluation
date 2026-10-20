package ma.projet.service;
import ma.projet.beans.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
public class HommeService extends AbstractService<Homme>{
    public HommeService(){super(Homme.class);}
    public HommeService(EntityManagerFactory emf){super(Homme.class,emf);}
    public List<Femme> epousesEntre(Long id,LocalDate debut,LocalDate fin){
        dates(debut,fin);
        return read(em->em.createQuery("SELECT DISTINCT m.femme FROM Mariage m WHERE m.homme.id=:id AND m.dateDebut<=:fin AND (m.dateFin IS NULL OR m.dateFin>=:debut)",Femme.class).setParameter("id",id).setParameter("debut",debut).setParameter("fin",fin).getResultList());
    }
    // Criteria query: four distinct wives with marriages overlapping the requested interval.
    public List<Homme> mariesQuatreFemmes(LocalDate debut,LocalDate fin){
        dates(debut,fin);
        return read(em->{
            javax.persistence.criteria.CriteriaBuilder cb=em.getCriteriaBuilder();
            javax.persistence.criteria.CriteriaQuery<Homme> q=cb.createQuery(Homme.class);
            javax.persistence.criteria.Root<Homme> h=q.from(Homme.class);
            javax.persistence.criteria.Subquery<Long> sub=q.subquery(Long.class);
            javax.persistence.criteria.Root<Mariage> m=sub.from(Mariage.class);
            sub.select(cb.countDistinct(m.get("femme").get("id")));
            sub.where(cb.equal(m.get("homme"),h),
                cb.lessThanOrEqualTo(m.<LocalDate>get("dateDebut"),fin),
                cb.or(cb.isNull(m.get("dateFin")),cb.greaterThanOrEqualTo(m.<LocalDate>get("dateFin"),debut)));
            q.select(h).where(cb.equal(sub,4L)).orderBy(cb.asc(h.get("id")));
            return em.createQuery(q).getResultList();
        });
    }
    public long nombreHommesQuatreFemmes(LocalDate debut,LocalDate fin){return mariesQuatreFemmes(debut,fin).size();}
}
