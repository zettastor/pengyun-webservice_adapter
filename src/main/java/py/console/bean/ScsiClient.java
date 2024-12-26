

package py.console.bean;

public class ScsiClient {

  private String ip;
  private String status;

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "ScsiClient{" + "ip='" + ip + '\'' + ", status='" + status + '\'' + '}';
  }
}
