import java.util.ArrayList;
import java.util.List;

public class InstructorCatalog {
    private final List<Instructor> instructors = new ArrayList<>();

    public void addInstructor(Instructor instructor) {
        if (instructor == null) throw new IllegalArgumentException("Instructor cannot be null.");
        if (instructors.contains(instructor)) {
            throw new IllegalStateException("Instructor already exists: " + instructor.getId());
        }
        instructors.add(instructor);
    }

    public List<Instructor> listInstructors() {
        return new ArrayList<>(instructors);
    }

    public void removeInstructor(Instructor instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor cannot be null");
        }
        if (!instructors.contains(instructor)) {
            throw new IllegalArgumentException("Instructor not found");
        }
        instructors.remove(instructor);
    }


}
