

package py.console.bean;

public class ResourceItem {

  public String id;
  public String name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "ResourceItem{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
  }
}
