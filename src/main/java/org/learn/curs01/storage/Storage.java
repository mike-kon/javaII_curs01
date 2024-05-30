package org.learn.curs01.storage;

public interface Storage<T> {
  void save(T obj);
  int getCount();
  T load(int i);
}
