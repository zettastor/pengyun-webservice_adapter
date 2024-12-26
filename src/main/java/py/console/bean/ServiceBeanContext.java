
package py.console.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.app.context.AppContextImpl;

public class ServiceBeanContext extends AppContextImpl {

  private final Logger logger = LoggerFactory.getLogger(ServiceBeanContext.class);

  public ServiceBeanContext(String name) {
    super(name);
  }

  @Override
  public String toString() {
    return "ServiceBeanContext{"
        + ", super=" + super.toString()
        + '}';
  }
}
