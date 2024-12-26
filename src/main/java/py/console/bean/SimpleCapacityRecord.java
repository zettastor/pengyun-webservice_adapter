

package py.console.bean;

import java.util.TreeMap;
import py.icshare.TotalAndUsedCapacity;

/**
 * SimpleCapacityRecord.
 */
public class SimpleCapacityRecord {

  private String message;

  private TreeMap<String, TotalAndUsedCapacity> capacityRecord;

  public TreeMap<String, TotalAndUsedCapacity> getCapacityRecord() {
    return capacityRecord;
  }

  public void setCapacityRecord(TreeMap<String, TotalAndUsedCapacity> capacityRecord) {
    this.capacityRecord = capacityRecord;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
