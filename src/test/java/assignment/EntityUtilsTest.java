package assignment;

import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

@RunWith(JMockit.class)
public class EntityUtilsTest {

    int nextId;

    @Test
    public void deepClone_shouldCloneGraph_scenario1() {

        new MockUp<Random>() {
            @Mock
            int nextInt(int bound) {
                return nextId++;
            }
        };

        nextId = -100 + 5;

        Entity e1 = new Entity(1, "e1", "");
        Entity e2 = new Entity(2, "e2", "");
        Entity e3 = new Entity(3, "e3", "");
        Entity e4 = new Entity(4, "e4", "");
        Entity e5 = new Entity(5, "e2", "");
        Entity e6 = new Entity(6, "e3", "");
        Entity e7 = new Entity(7, "e4", "");

        Link l1 = new Link(e1, e2);
        Link l2 = new Link(e1, e3);
        Link l3 = new Link(e2, e3);
        Link l4 = new Link(e3, e4);
        Link l5 = new Link(e1, e5);
        Link l6 = new Link(e5, e6);
        Link l7 = new Link(e6, e7);

        List<Entity> entities = new ArrayList<>();
        entities.addAll(Arrays.asList(e1, e2, e3, e4));
        List<Link> links = new ArrayList<>();
        links.addAll(Arrays.asList(l1, l2, l3, l4));

        EntityUtils.deepClone(entities, links, 2);

        List<Entity> expectedEntities = new ArrayList<>();
        expectedEntities.addAll(Arrays.asList(e1, e2, e3, e4, e5, e6, e7));
        List<Link> expectedLinks = new ArrayList<>();
        expectedLinks.addAll(Arrays.asList(l1, l2, l3, l4, l5, l6, l7));

        Collections.sort(expectedEntities);
        Collections.sort(entities);
        Collections.sort(expectedLinks);
        Collections.sort(links);
        Assert.assertArrayEquals(expectedEntities.toArray(), entities.toArray());
        Assert.assertArrayEquals(expectedLinks.toArray(), links.toArray());
    }

    @Test
    public void deepClone_shouldCloneGraph_scenario2_cyclic() {

        new MockUp<Random>() {
            @Mock
            int nextInt(int bound) {
                return nextId++;
            }
        };

        nextId = -100 + 4;

        Entity e1 = new Entity(1, "e1", "");
        Entity e2 = new Entity(2, "e2", "");
        Entity e3 = new Entity(3, "e3", "");
        Entity e4 = new Entity(4, "e1", "");
        Entity e5 = new Entity(5, "e2", "");
        Entity e6 = new Entity(6, "e3", "");

        Link l1 = new Link(e1, e2);
        Link l2 = new Link(e2, e3);
        Link l3 = new Link(e3, e1);
        Link l4 = new Link(e4, e5);
        Link l5 = new Link(e5, e6);
        Link l6 = new Link(e6, e4);
        Link l7 = new Link(e3, e4);

        List<Entity> entities = new ArrayList<>();
        entities.addAll(Arrays.asList(e1, e2, e3));


        List<Link> links = new ArrayList<>();
        links.addAll(Arrays.asList(l1, l2, l3));

        EntityUtils.deepClone(entities, links, 1);

        List<Entity> expectedEntities = new ArrayList<>();
        expectedEntities.addAll(Arrays.asList(e1, e2, e3, e4, e5, e6));
        List<Link> expectedLinks = new ArrayList<>();
        expectedLinks.addAll(Arrays.asList(l1, l2, l3, l4, l5, l6, l7));

        Collections.sort(expectedEntities);
        Collections.sort(entities);
        Collections.sort(expectedLinks);
        Collections.sort(links);
        Assert.assertArrayEquals(expectedEntities.toArray(), entities.toArray());
        Assert.assertArrayEquals(expectedLinks.toArray(), links.toArray());
    }

    @Test
    public void deepClone_shouldCloneGraph_twoEntities() {

        new MockUp<Random>() {
            @Mock
            int nextInt(int bound) {
                return nextId++;
            }
        };

        nextId = -100 + 3;

        Entity e1 = new Entity(1, "e1", "");
        Entity e2 = new Entity(2, "e2", "");
        Entity e3 = new Entity(3, "e1", "");
        Entity e4 = new Entity(4, "e2", "");
        List<Entity> entities = new ArrayList<>();
        entities.addAll(Arrays.asList(e1, e2));

        Link l1 = new Link(e1, e2);
        Link l2 = new Link(e3, e4);

        List<Link> links = new ArrayList<>();
        links.add(l1);

        EntityUtils.deepClone(entities, links, 1);

        List<Entity> expectedEntities = new ArrayList<>();
        expectedEntities.addAll(Arrays.asList(e1, e2, e3, e4));
        List<Link> expectedLinks = new ArrayList<>();
        expectedLinks.addAll(Arrays.asList(l1, l2));

        Collections.sort(expectedEntities);
        Collections.sort(entities);
        Collections.sort(expectedLinks);
        Collections.sort(links);
        Assert.assertArrayEquals(expectedEntities.toArray(), entities.toArray());
        Assert.assertArrayEquals(expectedLinks.toArray(), links.toArray());
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
        links.addAll(Arrays.asList(l1, l2, l3));

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
        links.addAll(Arrays.asList(l1, l2, l3));

        EntityUtils.deepClone(entities, links, 1);
    }

    @Test
    public void generateNewId_shouldGenerateAnIntegerBetween100and10000() {
        for (int i = 0; i < 10000; i++) {
            int id = EntityUtils.generateNewId();
            Assert.assertTrue(id == (int) id && id >= 100 && id < 10000);
        }
    }
}
