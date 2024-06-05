package org.learn.curs01.history;

import org.learn.curs01.account.Account;

public abstract class History {
  protected final Account account;

  protected History(Account account) {
    this.account = account;
  }

  public abstract void undo();
}
