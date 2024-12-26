

package py.console.bean;

import java.util.List;

/**
 * return message of deleting account to the browser, if the account still have volume then return
 * the list of volumes.
 *
 */
public class DeleteAccountResult {

  private String message;

  private List<SimpleVolumeMetadata> volumeList;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<SimpleVolumeMetadata> getVolumeList() {
    return volumeList;
  }

  public void setVolumeList(List<SimpleVolumeMetadata> volumeList) {
    this.volumeList = volumeList;
  }
}
