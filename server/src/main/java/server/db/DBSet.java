package server.db;

import java.util.LinkedList;

public interface DBSet<T> {
    T get(int id);
    LinkedList<T> all();
    int add(T t);
    void remove(int id);
    void clear();
    void update(T t);
    int nextId();
}
