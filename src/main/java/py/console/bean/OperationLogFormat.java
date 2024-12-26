

package py.console.bean;

import java.util.List;

public class OperationLogFormat {

  private String dayTime;
  private List<SimpleOperation> operationList;

  public String getDayTime() {
    return dayTime;
  }

  public void setDayTime(String dayTime) {
    this.dayTime = dayTime;
  }

  public List<SimpleOperation> getOperationList() {
    return operationList;
  }

  public void setOperationList(List<SimpleOperation> operationList) {
    this.operationList = operationList;
  }

  @Override
  public String toString() {
    return "OperationLogFormat{" + "dayTime='" + dayTime + '\'' + ", operationList=" + operationList
        + '}';
  }
}
