
package py.console.service.configuration;

import java.util.List;
import org.apache.thrift.TException;
import py.console.bean.SimpleConfiguration;
import py.console.bean.SimpleConfigurationResult;
import py.thrift.share.InvalidInputExceptionThrift;

/**
 * ConfigurationService.
 */
public interface ConfigurationService {

  public List<SimpleConfiguration> getConfiguration(String conditions)
      throws InvalidInputExceptionThrift, TException;

  public List<SimpleConfigurationResult> setConfiguration(String unFormattedConfigurations)
      throws InvalidInputExceptionThrift,
      TException;
}
