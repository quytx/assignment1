package assignment;

public class Link implements Comparable<Link> {

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

  @Override
  public boolean equals(Object o) {
    Link l = (Link) o;
    return this.fromEntity.getId() == l.getFromEntity().getId()
            && this.toEntity.getId() == l.getToEntity().getId();
  }

  @Override
  public int compareTo(Link o) {
    int compareFrom = this.fromEntity.compareTo(o.getFromEntity());
    return compareFrom == 0 ? this.toEntity.compareTo(o.getToEntity()) : compareFrom;
  }
}
