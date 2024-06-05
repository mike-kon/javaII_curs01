package org.learn.curs01.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.learn.curs01.account.Currency;

@Getter
@Setter
@AllArgsConstructor
public class CurrencySave {
  private Currency currency;
  private Double value;
}
