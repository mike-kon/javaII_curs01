package org.learn.curs01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.learn.curs01.account.AccountBl;
import org.learn.curs01.account.Currency;
import org.learn.curs01.history.HistoryImpl;
import org.learn.curs01.storage.StorageImp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
  private final String owner = "koma";
  private final String owner1 = "mike";

  @Test
  public void saveTest(){
    AccountBl acct = new AccountBl(new HistoryImpl(), new StorageImp<>(), owner);
    assertEquals(0,acct.getSaveCounts());
    acct.setCurrencyValue(Currency.RUR,100);
    acct.setCurrencyValue(Currency.EUR, 200);
    assertEquals(2, acct.getHistorySize());
    assertEquals(2,acct.getCurrencyNumbers());

    acct.save();
    assertEquals(1,acct.getSaveCounts());
    acct.setOwner("aaa");
    acct.setCurrencyValue(Currency.RUR,300);
    acct.setCurrencyValue(Currency.EUR, 400);
    acct.setCurrencyValue(Currency.USD, 500);
    assertEquals(6, acct.getHistorySize());
    assertEquals(3,acct.getCurrencyNumbers());

    acct.restore(0);
    assertEquals(1,acct.getSaveCounts());
    assertEquals(0, acct.getHistorySize());
    assertEquals(2,acct.getCurrencyNumbers());
    assertEquals(owner,acct.getOwner());
    assertEquals(100, acct.getCurrencyValue(Currency.RUR));
    assertEquals(200, acct.getCurrencyValue(Currency.EUR));
    assertEquals(0, acct.getCurrencyValue(Currency.USD));
  }


  @Test
  public void restoreFirst(){
    AccountBl acct = new AccountBl(new HistoryImpl(), new StorageImp<>(), owner);
    assertEquals(0,acct.getSaveCounts());
    acct.save();
    assertEquals(1,acct.getSaveCounts());
    acct.setOwner("name1");
    acct.save();
    assertEquals(2,acct.getSaveCounts());
    acct.setOwner("name2");
    acct.save();
    assertEquals(3,acct.getSaveCounts());
    acct.setOwner("name3");
    acct.restore(0);
    assertEquals(owner, acct.getOwner());
  }
  
  @Test
  public void restoreLast(){
    AccountBl acct = new AccountBl(new HistoryImpl(), new StorageImp<>(), owner);
    assertEquals(0,acct.getSaveCounts());
    acct.save();
    acct.setOwner("name1");
    acct.save();
    acct.setOwner("name2");
    acct.save();
    acct.setOwner(owner1);
    acct.save();
    acct.setOwner("name3");
    int n = acct.getSaveCounts() - 1;
    acct.restore(n);
    assertEquals(owner1, acct.getOwner());
  }

  @Test
  public void restoreWrong(){
    AccountBl acct = new AccountBl(new HistoryImpl(), new StorageImp<>(), owner);
    assertEquals(0,acct.getSaveCounts());
    acct.save();
    acct.setOwner("name1");
    acct.save();
    acct.setOwner("name2");
    acct.save();
    acct.setOwner("name3");
    acct.save();
    acct.setOwner("name4");
    acct.save();
    int n = acct.getSaveCounts();
    Assertions.assertThrows(RuntimeException.class,() -> acct.restore(n));
  }
}
