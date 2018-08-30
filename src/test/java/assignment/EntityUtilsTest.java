package assignment;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class EntityUtilsTest {

    @Test
    public void deepClone_shouldCloneGraph_scenario1() {

        Entity e1 = new Entity(1, "e1", "");
        Entity e2 = new Entity(2, "e2", "");
        Entity e3 = new Entity(3, "e3", "");
        Entity e4 = new Entity(4, "e4", "");
        List<Entity> entities = new ArrayList<>();
        entities.addAll(Arrays.asList(e1, e2, e3, e4));

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
                    // TODO: mock generateId and test the new entities
                    break;
            }
        }
    }

    @Test
    public void deepClone_shouldCloneGraph_scenario2_cyclic() {

        Entity e1 = new Entity(1, "e1", "");
        Entity e2 = new Entity(2, "e2", "");
        Entity e3 = new Entity(3, "e3", "");
        List<Entity> entities = new ArrayList<>();
        entities.addAll(Arrays.asList(e1, e2, e3));

        Link l1 = new Link(e1, e2);
        Link l2 = new Link(e2, e3);
        Link l3 = new Link(e3, e1);
        List<Link> links = new ArrayList<>();
        links.add(l1);
        links.add(l2);
        links.add(l3);

        EntityUtils.deepClone(entities, links, 1);

        Assert.assertEquals(6, entities.size());
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
                    Assert.assertEquals(1, (int) entry.getValue());
                    break;
                case 2:
                    Assert.assertEquals(1, (int) entry.getValue());
                    break;
                case 3:
                    Assert.assertEquals(2, (int) entry.getValue());
                    break;
                default:
                    // TODO: mock generateId and test the new entities
                    break;
            }
        }
    }

    @Test
    public void deepClone_shouldCloneGraph_twoEntities() {

        Entity e1 = new Entity(1, "e1", "");
        Entity e2 = new Entity(2, "e2", "");
        List<Entity> entities = new ArrayList<>();
        entities.add(e1);

        Link l1 = new Link(e1, e2);
        Link l2 = new Link(e1, e2);

        List<Link> links = new ArrayList<>();
        links.add(l1);
        links.add(l2);

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
                    // TODO: mock generateId and test the new entities
                    break;
            }
        }
    }

    @Test(expected = EntityException.class)
    public void deepClone_shouldThrowException_whenAnEntityIsNull() {

        Entity e1 = new Entity(1, "e1", "");
        Entity e2 = new Entity(2, "e2", "");
        Entity e3 = new Entity(3, "e3", "");
        List<Entity> entities = new ArrayList<>();
        entities.addAll(Arrays.asList(e1, e2, null));

        Link l1 = new Link(e1, e2);
        Link l2 = new Link(e2, e3);
        Link l3 = new Link(e3, e1);
        List<Link> links = new ArrayList<>();
        links.add(l1);
        links.add(l2);
        links.add(l3);

        EntityUtils.deepClone(entities, links, 1);
    }

    @Test(expected = EntityException.class)
    public void deepClone_shouldThrowException_whenALinkIsInvalid() {

        Entity e1 = new Entity(1, "e1", "");
        Entity e2 = new Entity(2, "e2", "");
        Entity e3 = new Entity(3, "e3", "");
        List<Entity> entities = new ArrayList<>();
        entities.addAll(Arrays.asList(e1, e2, e3));

        Link l1 = new Link(e1, e2);
        Link l2 = new Link(e2, e3);
        Link l3 = new Link(e3, null);
        List<Link> links = new ArrayList<>();
        links.add(l1);
        links.add(l2);
        links.add(l3);

        EntityUtils.deepClone(entities, links, 1);
    }

    @Test
    public void generateNewId_shouldGenerateAnIntegerBetween100and10000() {
        for (int i = 0; i < 10000; i++) {
            int id = EntityUtils.generateNewId();
            Assert.assertTrue(id >= 100 && id < 10000);
        }
    }
}
