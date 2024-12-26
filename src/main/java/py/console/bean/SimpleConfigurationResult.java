

package py.console.bean;

/**
 * SimpleConfigurationResult.
 */
public class SimpleConfigurationResult {

  private String key;
  private String value;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "\nSimpleConfigurationResult [key=" + key + ", value=" + value + "]";
  }

}
