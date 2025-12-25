import java.util.ArrayList;
import java.util.List;

public class InstructorCatalog {
    private final List<Instructor> instructors = new ArrayList<>();

    public void addInstructor(Instructor instructor) {
        if (instructor == null) throw new IllegalArgumentException("Egitmen kismi bos olamaz. ");
        if (instructors.contains(instructor)) {
            throw new IllegalStateException("Egitmen zaten bulunuyor. " + instructor.getId());
        }
        instructors.add(instructor);
    }

    public List<Instructor> listInstructors() {
        return new ArrayList<>(instructors);
    }

    public void removeInstructor(Instructor instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("Egitmen ismi bos olamaz.");
        }
        if (!instructors.contains(instructor)) {
            throw new IllegalArgumentException("Aranan egitmen bulunamadi.");
        }
        instructors.remove(instructor);
    }


}
