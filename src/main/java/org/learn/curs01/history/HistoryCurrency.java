package org.learn.curs01.history;

import org.learn.curs01.account.Account;
import org.learn.curs01.account.Currency;

public class HistoryCurrency extends History {

  private final Currency currency;
  private final Double value;

  public HistoryCurrency(Account account, Currency currency, Double value) {
    super(account);
    this.currency = currency;
    this.value = value;
  }

  @Override
  public void undo() {
    account.getCurrencyMap().put(currency,value);
  }
}
