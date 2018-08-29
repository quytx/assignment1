package assignment;

public class Link {

  private Entity fromEntity;
  private Entity toEntity;

  public Link(Entity fromEntity, Entity toEntity) {
    this.fromEntity = fromEntity;
    this.toEntity = toEntity;
  }

  public Entity getFromEntity() {
    return fromEntity;
  }

  public void setFromEntity(Entity fromEntity) {
    this.fromEntity = fromEntity;
  }

  public Entity getToEntity() {
    return toEntity;
  }

  public void setToEntity(Entity toEntity) {
    this.toEntity = toEntity;
  }
}
