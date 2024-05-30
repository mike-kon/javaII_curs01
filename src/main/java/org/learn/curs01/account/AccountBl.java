package org.learn.curs01.account;

import org.learn.curs01.history.History;
import org.learn.curs01.history.HistoryDto;
import org.learn.curs01.storage.Storage;

import java.util.HashMap;
import java.util.Map;

public class AccountBl {
  private final History history;
  private final Storage<Account> storage;
  private final Account accountDto;
  private final String ownerHistoryName = "owner";
  private final String currencyHistoryName = "currency";
  public AccountBl(History history, Storage<Account> storage, String owner) {
    this.history = history;
    this.storage = storage;
    accountDto = new Account();
    accountDto.setOwner(owner);
    accountDto.setCurrencyMap(new HashMap<>());
  }

  public String getOwner(){
    return accountDto.getOwner();
  }

  public void setOwner(String owner) {
    history.save(ownerHistoryName, accountDto.getOwner());
    accountDto.setOwner(owner);
  }

  public double getCurrencyValue(Currency currency) {
    Map<Currency, Double> currencyMap = accountDto.getCurrencyMap();
    return currencyMap.containsKey(currency) ? currencyMap.get(currency) : 0.0;
  }

  public int getCurrencyNumbers() {
    return accountDto.getCurrencyMap().size();
  }

  public void setCurrencyValue(Currency currency, double newValue){
    if (newValue <= 0){
      throw new RuntimeException("value must be more, then zero");
    }
    history.save(currencyHistoryName, currency, accountDto.getCurrencyMap().get(currency));
    accountDto.getCurrencyMap().put(currency,newValue);
  }

  public int getHistorySize() {
    return history.getSize();
  }

  public void undo() {
    HistoryDto point = history.undo();
    if (point == null){
      return;
    }
    switch (point.getCommand()){
      case ownerHistoryName:
        accountDto.setOwner((String)point.getParams()[0]);
        break;
      case currencyHistoryName:
        Currency currency = (Currency) point.getParams()[0];
        Double value = (Double) point.getParams()[1];
        if (value == null) {
          accountDto.getCurrencyMap().remove(currency);
        } else {
          accountDto.getCurrencyMap().put(currency, value);
        }
        break;
      default:
        throw new RuntimeException("Unknown history command " + point.getCommand());
    }
  }

  public boolean canUndo() {
    return history.canUndo();
  }

  public void save(){
    storage.save(new Account(accountDto));
  }

  public void restore(int i){
    Account acct  = storage.load(i);
    accountDto.setOwner(acct.getOwner());
    accountDto.setCurrencyMap(acct.getCurrencyMap());
    history.clear();
  }

  public int getSaveCounts(){
    return storage.getCount();
  }

  public String print(){
    StringBuilder strs = new StringBuilder();
    strs.append("Account of ").append(accountDto.getOwner()).append("\r\n");
    if (accountDto.getCurrencyMap().isEmpty()){
      strs.append("-= Has no currency =- \r\n");
    } else {
      strs.append("-= currency value =-\r\n");
      for(Map.Entry<Currency, Double> item : accountDto.getCurrencyMap().entrySet()){
        strs.append(item.getKey().toString()).append(":").append(item.getValue()).append("\r\n");
      }
    }
    if (history.getSize() == 0) {
      strs.append("the account state has not been changed\r\n");
    } else {
      strs.append("the account state has been changed ").append(history.getSize()).append(" times\r\n");
    }
    return strs.toString();
  }

}
