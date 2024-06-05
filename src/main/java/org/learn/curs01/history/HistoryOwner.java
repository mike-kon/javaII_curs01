package org.learn.curs01.history;

import org.learn.curs01.account.Account;

public class HistoryOwner extends History {
  private final String oldOwner;
  public HistoryOwner(Account account, String oldOwner) {
    super(account);
    this.oldOwner = oldOwner;
  }

  @Override
  public void undo() {
    account.undoOwner(oldOwner);
  }
}
