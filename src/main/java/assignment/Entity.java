package assignment;

public class Entity implements Comparable<Entity> {

    private int id;
    private String name;
    private String description;

    public Entity() {
    }

    public Entity(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        Entity e = (Entity) o;
        return this.name.equals(e.getName()) && this.description.equals(e.getDescription());
    }

    @Override
    public int compareTo(Entity o) {
        return this.id - o.getId();
    }
}
