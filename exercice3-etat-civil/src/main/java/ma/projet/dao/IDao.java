package ma.projet.dao;
import java.util.List;
public interface IDao<T>{
    void create(T entity);
    T findById(Long id);
    List<T> findAll();
    T update(T entity);
    void delete(T entity);
}
