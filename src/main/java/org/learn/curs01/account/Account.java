package org.learn.curs01.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Account {
  private String owner;
  private Map<Currency,Double> currencyMap;
  public Account(Account acct){
    owner = acct.owner;
    currencyMap = new HashMap<>(acct.currencyMap);
  }
}
