package org.learn.curs01;

import org.learn.curs01.account.Account;
import org.learn.curs01.account.AccountBl;
import org.learn.curs01.account.Currency;
import org.learn.curs01.history.HistoryImpl;
import org.learn.curs01.storage.StorageImp;

public class App {
  public static void main(String[] args) throws CloneNotSupportedException {
    AccountBl acct = new AccountBl(new HistoryImpl(), new StorageImp<Account>(),"koma");
    System.out.println(acct.print());

    acct.setCurrencyValue(Currency.RUR,100);
    System.out.println(acct.print());
    acct.setCurrencyValue(Currency.EUR, 10);
    System.out.println(acct.print());
    acct.setCurrencyValue(Currency.RUR, 1000);
    System.out.println(acct.print());
    acct.setOwner("mike");
    System.out.println(acct.print());
    System.out.println("undo");
    acct.undo();
    System.out.println(acct.print());
    acct.undo();
    System.out.println(acct.print());
    acct.undo();
    System.out.println(acct.print());
    acct.undo();
    System.out.println(acct.print());
    acct.undo();
    System.out.println(acct.print());
  }
}
