package client.db;

import java.util.LinkedList;

public interface DBSet<T> {
    T get(String usernameDir, int id);
    LinkedList<T> all(String usernameDir);
    int add(String usernameDir, T t);
    void remove(String usernameDir, int id);
    void clear(String usernameDir);
    void update(String usernameDir, T t);
    int nextId(String usernameDir);
}
