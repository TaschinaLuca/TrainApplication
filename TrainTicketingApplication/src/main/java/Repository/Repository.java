package Repository;

import java.util.List;

public interface Repository<T> {
    int add(T entity);
    void remove(int id);
    void update(T entity);
    T getById(int id);
    List<T> getAll();
}
