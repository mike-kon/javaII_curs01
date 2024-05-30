package org.learn.curs01.history;

public interface History {
  int getSize();
  HistoryDto undo();
  void save(String command, Object... params);
  boolean canUndo();
  void clear();
}
