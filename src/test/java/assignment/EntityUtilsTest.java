package assignment;

import assignment.Entity;
import assignment.EntityUtils;
import assignment.Link;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class EntityUtilsTest {


  @Test
  public void deepClone_shouldCloneGraph() {

    Entity e1 = new Entity(1, "e1", "");
    Entity e2 = new Entity(2, "e2", "");
    Entity e3 = new Entity(3, "e3", "");
    Entity e4 = new Entity(4, "e4", "");
    List<Entity> entities = new ArrayList<>();
    entities.add(e1);
    entities.add(e2);
    entities.add(e3);
    entities.add(e4);

    Link l1 = new Link(e1, e2);
    Link l2 = new Link(e1, e3);
    Link l3 = new Link(e2, e3);
    Link l4 = new Link(e3, e4);
    List<Link> links = new ArrayList<>();
    links.add(l1);
    links.add(l2);
    links.add(l3);
    links.add(l4);

    EntityUtils.deepClone(entities, links, 2);

    Assert.assertEquals(7, entities.size());
    Assert.assertEquals(7, links.size());

    HashMap<Entity, Integer> fromCounts = new HashMap<>();
    for (Entity entity : entities) {
      fromCounts.put(entity, 0);
    }
    for (Link link : links) {
      Entity fromEntity = link.getFromEntity();
      fromCounts.put(fromEntity, fromCounts.get(fromEntity) + 1);
    }
    for (Map.Entry<Entity, Integer> entry : fromCounts.entrySet()) {
      switch (entry.getKey().getId()) {
        case 1:
          Assert.assertEquals(3, (int) entry.getValue());
          break;
        case 2:
          Assert.assertEquals(1, (int) entry.getValue());
          break;
        case 3:
          Assert.assertEquals(1, (int) entry.getValue());
          break;
        case 4:
          Assert.assertEquals(0, (int) entry.getValue());
          break;
        default:
          break;
      }
    }

  }

  @Test
  public void generateNewId_shouldGenerateAnIntegerBetween100and10000() {
    for (int i = 0; i < 10000; i++) {
      int id = EntityUtils.generateNewId();
      Assert.assertTrue(id == (int) id && id >= 100 && id < 10000);
    }
  }
}
