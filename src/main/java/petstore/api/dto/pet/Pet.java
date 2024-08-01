package petstore.api.dto.pet;

import java.util.List;

public class Pet {

  private int id;
  private Category category;
  private String name;
  private List<String> photoUrls;
  private List<TagsItem> tags;
  private String status;


  public List<String> getPhotoUrls() {
    return photoUrls;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public Category getCategory() {
    return category;
  }

  public List<TagsItem> getTags() {
    return tags;
  }

  public String getStatus() {
    return status;
  }

  public void setName(String name) {
    this.name = name;
  }
}