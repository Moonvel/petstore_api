package petstore.api.dto.pet;

import lombok.Getter;

@Getter
public enum Status {

  AVAILABLE("available"),
  PENDING("pending"),
  SOLD("sold");

  private final String status;

  Status(String status) {
    this.status = status;
  }

}
