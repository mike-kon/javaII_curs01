package org.learn.curs01.history;

import java.util.Stack;

public class HistoryImpl implements  History{
  private final Stack<HistoryDto> stack = new Stack<>();
  @Override
  public int getSize() {
    return stack.size();
  }

  @Override
  public HistoryDto undo() {
    if (stack.empty()){
      throw new RuntimeException("Undo is not possible");
    } else {
      return stack.pop();
    }
  }

  @Override
  public void save(String command, Object... params) {
    stack.push(new HistoryDto(command,params));
  }

  @Override
  public boolean canUndo() {
    return !stack.empty();
  }

  @Override
  public void clear() {
    stack.clear();
  }
}
