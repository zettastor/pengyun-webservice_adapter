

package py.console.bean;

import java.util.List;

public class InstanceFormat {

  private String ip;
  private List<SimpleInstance> instances;

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public List<SimpleInstance> getInstances() {
    return instances;
  }

  public void setInstances(List<SimpleInstance> instances) {
    this.instances = instances;
  }

  @Override
  public String toString() {
    return "InstanceFormat{" + "ip='" + ip + '\'' + ", instances=" + instances + '}';
  }
}
