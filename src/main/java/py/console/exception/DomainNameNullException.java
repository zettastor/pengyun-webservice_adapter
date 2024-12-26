

package py.console.exception;

/**
 * DomainNameNullException.
 */
public class DomainNameNullException extends Exception {

  private static final long serialVersionUID = -1065415306395856777L;

  public DomainNameNullException() {
    super();
  }

  public DomainNameNullException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public DomainNameNullException(String message, Throwable cause) {
    super(message, cause);
  }

  public DomainNameNullException(String message) {
    super(message);
  }

  public DomainNameNullException(Throwable cause) {
    super(cause);
  }

}
