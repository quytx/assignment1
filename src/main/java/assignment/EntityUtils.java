package assignment;

import assignment.EntityException.EntityExceptionError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EntityUtils {

  /*
   * Clone an entity and all of its related entities
   */
  public static void deepClone(List<Entity> entities, List<Link> links, int id) {

    // Generate hash maps
    HashMap<Integer, Entity> entityMap = new HashMap<>();
    for (Entity entity : entities) {
      validateEntity(entity);
      entityMap.put(entity.getId(), entity);
    }

    HashMap<Integer, List<Entity>> linkFromMap = new HashMap<>();
    HashMap<Integer, List<Entity>> linkToMap = new HashMap<>();
    for (Link link : links) {
      validateLink(link);
      if (!linkFromMap.containsKey(link.getFromEntity().getId())) {
        linkFromMap.put(link.getFromEntity().getId(), new ArrayList<>());
      }
      linkFromMap.get(link.getFromEntity().getId()).add(link.getToEntity());

      if (!linkToMap.containsKey(link.getToEntity().getId())) {
        linkToMap.put(link.getToEntity().getId(), new ArrayList<>());
      }
      linkToMap.get(link.getToEntity().getId()).add(link.getFromEntity());
    }

    // Clone the entity recursively
    Entity originalEntity = entityMap.get(id);
    Entity cloneEntity = deepClone(entityMap, linkFromMap, linkToMap, id, null, null);

    // Set parents of initial entity to new entity
    List<Entity> parents = linkToMap.get(id);
    for (Entity parent : parents) {
      if (!linkFromMap.containsKey(parent.getId())) {
        linkFromMap.put(parent.getId(), new ArrayList<>());
      }
      linkFromMap.get(parent.getId()).add(cloneEntity);

      // No need to update linkToMap as we only need one to rebuild original list of links
    }

    // Rebuild entities list
    List<Entity> updatedEntities = new ArrayList<>(entityMap.values());

    // Rebuild links list
    List<Link> updatedLinks = new ArrayList<>();
    for (Map.Entry<Integer, List<Entity>> entry : linkFromMap.entrySet()) {
      Entity fromEntity = entityMap.get(entry.getKey());
      for (Entity toEntity : entry.getValue()) {
        updatedLinks.add(new Link(fromEntity, toEntity));
      }
    }

    entities.clear();
    entities.addAll(updatedEntities);
    links.clear();
    links.addAll(updatedLinks);
  }

  /*

   */
  private static Entity deepClone(HashMap<Integer, Entity> entityMap,
      HashMap<Integer, List<Entity>> linkFromMap,
      HashMap<Integer, List<Entity>> linkToMap,
      int id,
      HashMap<Integer, Entity> entityToCloneMap,
      HashSet<Integer> visited) {

    if (visited == null) {
      visited = new HashSet<>();
    }

    if (entityToCloneMap == null) {
      entityToCloneMap = new HashMap<>();
    }

    if (visited.contains(id)) {
      // If child already visited - use the cloned one
      return entityToCloneMap.get(id);
    }

    // Mark entity as visited
    visited.add(id);

    // Find the entity
    Entity entityToClone = entityMap.get(id);

    // Clone the entity
    Entity cloneEntity = cloneEntity(entityToClone);
    entityMap.put(cloneEntity.getId(), cloneEntity);
    entityToCloneMap.put(id, cloneEntity);
    if (!linkFromMap.containsKey(cloneEntity.getId())) {
      linkFromMap.put(cloneEntity.getId(), new ArrayList<>());
    }

    // Deep clone the child
    List<Entity> childEntities = linkFromMap.get(id);

    if (childEntities != null) {
      for (Entity child : childEntities) {
        Entity childClone = deepClone(entityMap, linkFromMap, linkToMap, child.getId(), entityToCloneMap, visited);
        entityMap.put(childClone.getId(), childClone);
        entityToCloneMap.put(child.getId(), childClone);
        if (!linkToMap.containsKey(childClone.getId())) {
          linkToMap.put(childClone.getId(), new ArrayList<>());
        }
        linkToMap.get(childClone.getId()).add(cloneEntity);
        linkFromMap.get(cloneEntity.getId()).add(childClone);
      }
    }

    return cloneEntity;
  }

  private static Entity cloneEntity(Entity entity) {
    return new Entity(generateNewId(), entity.getName(), entity.getDescription());
  }

  private static void validateEntity(Entity entity) {
    if (entity == null) {
      throw new EntityException(EntityExceptionError.INVALID_ENTITY);
    }
  }

  private static void validateLink(Link link) {
    if (link == null || link.getFromEntity() == null || link.getToEntity() == null) {
      throw new EntityException(EntityExceptionError.INVALID_LINK);
    }
    validateEntity(link.getFromEntity());
    validateEntity(link.getToEntity());
  }

  /*
   * Generate a random integer between 100 (inclusive) and 10000 (exclusive)
   */
  public static int generateNewId() {
    Random r = new Random();
    int low = 100;
    int high = 10000;
    return r.nextInt(high - low) + low;
  }

}
