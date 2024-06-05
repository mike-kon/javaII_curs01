package org.learn.curs01;

import org.junit.jupiter.api.Test;
import org.learn.curs01.account.Account;
import org.learn.curs01.account.Currency;
import org.learn.curs01.storage.AccountSave;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
  private final String owner = "koma";
  @Test
  public void saveTest(){
    Account acct = new Account( owner);
    acct.setCurrencyValue(Currency.RUR,100);
    acct.setCurrencyValue(Currency.EUR, 200);
    assertEquals(2, acct.getHistorySize());
    assertEquals(2,acct.getCurrencyNumbers());

    AccountSave saveObj = acct.save();
    acct.setOwner("aaa");
    acct.setCurrencyValue(Currency.RUR,300);
    acct.setCurrencyValue(Currency.EUR, 400);
    acct.setCurrencyValue(Currency.USD, 500);
    assertEquals(6, acct.getHistorySize());
    assertEquals(3,acct.getCurrencyNumbers());

    acct.restore(saveObj);
    assertEquals(0, acct.getHistorySize());
    assertEquals(2,acct.getCurrencyNumbers());
    assertEquals(owner,acct.getOwner());
    assertEquals(100, acct.getCurrencyValue(Currency.RUR));
    assertEquals(200, acct.getCurrencyValue(Currency.EUR));
    assertEquals(0, acct.getCurrencyValue(Currency.USD));
  }


  @Test
  public void restoreFirst(){
    Account acct = new Account(owner);
    AccountSave saveObj = acct.save();
    acct.setOwner("name1");
    acct.setOwner("name2");
    acct.setOwner("name3");
    acct.restore(saveObj);
    assertEquals(owner, acct.getOwner());  }
  
}
