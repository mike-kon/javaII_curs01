package org.learn.curs01.history;

import org.learn.curs01.account.Account;
import org.learn.curs01.account.Currency;

public class HistoryRemoveCurrency extends History {
  private final Currency currency;

  public HistoryRemoveCurrency(Account account, Currency currency) {
    super(account);
    this.currency = currency;
  }

  @Override
  public void undo() {
    account.getCurrencyMap().remove(currency);
  }
}
