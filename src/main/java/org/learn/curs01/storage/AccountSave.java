package org.learn.curs01.storage;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountSave {
  private String owner;
  private List<CurrencySave> currency;
}
