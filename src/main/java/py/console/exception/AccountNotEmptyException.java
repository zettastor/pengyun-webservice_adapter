

package py.console.exception;

/**
 * exception throw when delete a account but the account has available volumes.
 *
 */
public class AccountNotEmptyException extends Exception {

  private static final long serialVersionUID = -1065415306395856777L;

  public AccountNotEmptyException() {
    super();
  }

  public AccountNotEmptyException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public AccountNotEmptyException(String message, Throwable cause) {
    super(message, cause);
  }

  public AccountNotEmptyException(String message) {
    super(message);
  }

  public AccountNotEmptyException(Throwable cause) {
    super(cause);
  }

}
