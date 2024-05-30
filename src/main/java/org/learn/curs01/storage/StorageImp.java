package org.learn.curs01.storage;

import java.util.ArrayList;
import java.util.List;

public class StorageImp<T> implements Storage<T>{
  private final List<T> data = new ArrayList<>();
  @Override
  public void save(T obj) {
    data.add(obj);
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public T load(int i) {
    return data.get(i);
  }
}
