

package py.console.bean;

import java.util.List;

/**
 * AccountList.
 */
public class AccountList {

  private String message;

  private List<Account> accountList;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<Account> getAccountList() {
    return accountList;
  }

  public void setAccountList(List<Account> accountList) {
    this.accountList = accountList;
  }

}
