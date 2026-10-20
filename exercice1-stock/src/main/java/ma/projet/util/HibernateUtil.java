package ma.projet.util;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.*;
import java.io.*;

public final class HibernateUtil {
    private static SessionFactory factory;
    private HibernateUtil(){}
    public static SessionFactory build(boolean h2,String db){
        Properties p=new Properties();
        try(InputStream in=HibernateUtil.class.getResourceAsStream("/application.properties")){
            if(in==null)throw new IllegalStateException("application.properties missing");
            p.load(in);
        }catch(IOException e){throw new IllegalStateException(e);}
        if(h2){
            p.setProperty("hibernate.connection.driver_class","org.h2.Driver");
            p.setProperty("hibernate.connection.url","jdbc:h2:mem:"+db+";DB_CLOSE_DELAY=-1");
            p.setProperty("hibernate.connection.username","sa");p.setProperty("hibernate.connection.password","");
            p.setProperty("hibernate.dialect","org.hibernate.dialect.H2Dialect");
        }else{
            for(String[] pair:new String[][]{{"DB_URL","hibernate.connection.url"},{"DB_USER","hibernate.connection.username"},{"DB_PASSWORD","hibernate.connection.password"}})
                if(System.getenv(pair[0])!=null)p.setProperty(pair[1],System.getenv(pair[0]));
        }
        Configuration cfg=new Configuration().setProperties(p);
        cfg.addAnnotatedClass(ma.projet.classes.Categorie.class);
        cfg.addAnnotatedClass(ma.projet.classes.Produit.class);
        cfg.addAnnotatedClass(ma.projet.classes.Commande.class);
        cfg.addAnnotatedClass(ma.projet.classes.LigneCommande.class);
        return cfg.buildSessionFactory();
    }
    public static synchronized SessionFactory getSessionFactory(){
        if(factory==null||factory.isClosed())factory=build(!"mysql".equals(System.getProperty("eval.db")),"evaluation1");
        return factory;
    }
    public static synchronized void shutdown(){if(factory!=null&&!factory.isClosed())factory.close();factory=null;}
}
