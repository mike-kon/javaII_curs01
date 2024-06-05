package org.learn.curs01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.learn.curs01.account.Account;
import org.learn.curs01.account.Currency;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
  private final String owner = "koma";
  private final String owner1 = "mike";

  @Test
  public void createAccountTest() {
    Account acct = new Account(owner);
    assertEquals(0, acct.getCurrencyNumbers());
    acct.setCurrencyValue(Currency.RUR, 100);
    assertEquals(1, acct.getCurrencyNumbers());
    acct.setCurrencyValue(Currency.EUR, 200);

    assertEquals(owner, acct.getOwner());
    assertEquals(2, acct.getCurrencyNumbers());
    assertEquals(100, acct.getCurrencyValue(Currency.RUR));
    assertEquals(200, acct.getCurrencyValue(Currency.EUR));
    assertEquals(0, acct.getCurrencyValue(Currency.USD));
    assertEquals(2, acct.getHistorySize());
  }

  @Test
  public void changeNameTest() {
    Account acct = new Account(owner);
    acct.setOwner(owner1);
    assertEquals(owner1, acct.getOwner());
    assertEquals(1, acct.getHistorySize());
  }

  @Test
  public void addCurrencyTest() {
    Account acct = new Account(owner);
    acct.setCurrencyValue(Currency.RUR, 100);
    acct.setCurrencyValue(Currency.RUR, 200);
    assertEquals(200, acct.getCurrencyValue(Currency.RUR));
    assertEquals(2, acct.getHistorySize());
  }

  @Test
  public void wrongCurrencyTest() {
    Account acct = new Account(owner);
    RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> acct.setCurrencyValue(Currency.RUR, -1));
    assertEquals("value must be more, then zero", exception.getMessage());
  }

  @Test
  public void undoChangeNameTest() {
    Account acct = new Account(owner);
    assertFalse(acct.canUndo());
    acct.setOwner(owner1);
    assertTrue(acct.canUndo());
    acct.undo();
    assertFalse(acct.canUndo());
    assertEquals(owner, acct.getOwner());
    assertEquals(0, acct.getHistorySize());
  }

  @Test
  public void undoChangeCurrencyTest1() {
    Account acct = new Account(owner);
    assertFalse(acct.canUndo());
    acct.setCurrencyValue(Currency.RUR, 100);
    assertTrue(acct.canUndo());
    acct.setCurrencyValue(Currency.RUR, 200);
    assertTrue(acct.canUndo());
    assertEquals(200, acct.getCurrencyValue(Currency.RUR));
    assertEquals(1, acct.getCurrencyNumbers());
    assertEquals(2, acct.getHistorySize());
    acct.undo();
    assertTrue(acct.canUndo());
    assertEquals(100, acct.getCurrencyValue(Currency.RUR));
    assertEquals(1, acct.getCurrencyNumbers());
    assertEquals(1, acct.getHistorySize());
    acct.undo();
    assertFalse(acct.canUndo());
    assertEquals(0, acct.getCurrencyValue(Currency.RUR));
    assertEquals(0, acct.getCurrencyNumbers());
    assertEquals(0, acct.getHistorySize());
    RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> acct.undo());
    assertEquals("Undo is not possible", exception.getMessage());
  }

  @Test
  public void undoChangeCurrencyTest2() {
    Account acct = new Account(owner);
    assertFalse(acct.canUndo());
    acct.setCurrencyValue(Currency.RUR, 100);
    assertTrue(acct.canUndo());
    acct.setCurrencyValue(Currency.EUR, 200);
    assertTrue(acct.canUndo());
    acct.setCurrencyValue(Currency.RUR, 300);
    assertTrue(acct.canUndo());
    assertEquals(300, acct.getCurrencyValue(Currency.RUR));
    assertEquals(200, acct.getCurrencyValue(Currency.EUR));
    assertEquals(2, acct.getCurrencyNumbers());
    assertEquals(3, acct.getHistorySize());
    acct.undo();
    assertEquals(100, acct.getCurrencyValue(Currency.RUR));
    assertTrue(acct.canUndo());
    assertEquals(200, acct.getCurrencyValue(Currency.EUR));
    assertTrue(acct.canUndo());
    assertEquals(2, acct.getCurrencyNumbers());
    assertEquals(2, acct.getHistorySize());
    acct.undo();
    assertTrue(acct.canUndo());
    assertEquals(100, acct.getCurrencyValue(Currency.RUR));
    assertEquals(0, acct.getCurrencyValue(Currency.EUR));
    assertEquals(1, acct.getCurrencyNumbers());
    assertEquals(1, acct.getHistorySize());
  }
}
