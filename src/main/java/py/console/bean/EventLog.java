

package py.console.bean;

public class EventLog {

  private String id;
  private String eventLog;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEventLog() {
    return eventLog;
  }

  public void setEventLog(String eventLog) {
    this.eventLog = eventLog;
  }

  @Override
  public String toString() {
    return "EventLog{" + "id='" + id + '\'' + ", eventLog='" + eventLog + '\'' + '}';
  }
}
