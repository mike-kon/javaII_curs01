package org.learn.curs01.account;

import lombok.Getter;
import org.learn.curs01.history.History;
import org.learn.curs01.history.HistoryCurrency;
import org.learn.curs01.history.HistoryOwner;
import org.learn.curs01.history.HistoryRemoveCurrency;
import org.learn.curs01.storage.AccountSave;
import org.learn.curs01.storage.CurrencySave;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Getter
public class Account {
  private String owner;
  private Map<Currency, Double> currencyMap;
  private final Stack<History> history;

  public Account(String owner) {
    this.history = new Stack<>();
    this.owner = owner;
    currencyMap = new HashMap<>();
  }

  public void setOwner(String owner) {
    history.push(new HistoryOwner(this, this.owner));
    this.owner = owner;
  }

  public double getCurrencyValue(Currency currency) {
    return currencyMap.getOrDefault(currency, 0.0);
  }

  public int getCurrencyNumbers() {
    return currencyMap.size();
  }

  public void setCurrencyValue(Currency currency, double newValue) {
    if (newValue <= 0) {
      throw new RuntimeException("value must be more, then zero");
    }
    history.push(currencyMap.containsKey(currency)
            ? new HistoryCurrency(this, currency, currencyMap.get(currency))
            : new HistoryRemoveCurrency(this, currency));
    currencyMap.put(currency, newValue);
  }

  public int getHistorySize() {
    return history.size();
  }

  public void undo() {
    if (history.isEmpty()) {
      throw new RuntimeException("Undo is not possible");
    }
    History point = history.pop();
    point.undo();
  }

  public void undoOwner(String owner) {
    this.owner = owner;
  }

  public boolean canUndo() {
    return !history.isEmpty();
  }

  public AccountSave save(){
    AccountSave acct = new AccountSave();
    acct.setOwner(owner);
    acct.setCurrency(
            currencyMap.entrySet().stream()
                    .map(x -> new CurrencySave(x.getKey(),x.getValue())).toList());
    return acct;
  }

  public void restore(AccountSave acct){
    if (acct == null){
      return;
    }
    owner = acct.getOwner();
    currencyMap = acct.getCurrency().stream().collect(HashMap::new,
            (map, cr) -> map.put(cr.getCurrency(),cr.getValue()), Map::putAll);
    history.clear();
  }

  public String print() {
    StringBuilder strs = new StringBuilder();
    strs.append("Account of ").append(owner).append("\r\n");
    if (currencyMap.isEmpty()) {
      strs.append("-= Has no currency =- \r\n");
    } else {
      strs.append("-= currency value =-\r\n");
      for (Map.Entry<Currency, Double> item : currencyMap.entrySet()) {
        strs.append(item.getKey().toString()).append(":").append(item.getValue()).append("\r\n");
      }
    }
    if (history.isEmpty()) {
      strs.append("the account state has not been changed\r\n");
    } else {
      strs.append("the account state has been changed ").append(history.size()).append(" times\r\n");
    }
    return strs.toString();
  }

}
