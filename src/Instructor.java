public class Instructor {

    private final String id;
    private final String name;

    public Instructor(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Instructor id cannot be null/blank.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Instructor name cannot be null/blank.");
        }
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
