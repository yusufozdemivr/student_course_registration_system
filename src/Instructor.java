import java.util.Objects;

public class Instructor {

    private final String id;
    private final String name;

    public Instructor(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Egitmen ID'si girilmek zorundadir.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Egitmen adi girilmek zorundadir.");
        }
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instructor)) return false;
        Instructor that = (Instructor) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
