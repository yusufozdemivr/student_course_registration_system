import java.util.ArrayList;
import java.util.List;

public class CourseCatalog {

    private final List<Course> courses = new ArrayList<>();

    public void addCourse(Course course, Instructor instructor) {
        if (course == null) {
            throw new IllegalArgumentException("Ders bos olamaz.");
        }

        if (instructor == null) {
            throw new IllegalArgumentException("Egitmen bos olamaz.");
        }

        if (courses.contains(course)) {
            throw new IllegalStateException("Ders programda zaten bulunuyor. " + course.getCode());
        }


        if (course.getTimeSlot() == null) {
            throw new IllegalArgumentException("Ders zaman araligi bos olamaz.");
        }

        for (Course existing : courses) {
            if (existing.getInstructor() != null
                    && existing.getInstructor().equals(instructor)
                    && existing.getTimeSlot() != null
                    && existing.getTimeSlot().conflictsWith(course.getTimeSlot())) {

                throw new IllegalStateException(
                        "Egitmenin ders zamani cakisiyor " + instructor.getName()
                                + " dersini zaten veriyor " + existing.getCode()
                                +  existing.getTimeSlot()
                );
            }
        }


        course.setInstructor(instructor);
        courses.add(course);
    }

    public void addCourse(Course course) {
        throw new UnsupportedOperationException(
                "Egitmen gereklidir."
        );
    }

    public void removeCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Ders bos olamaz.");
        }
        courses.remove(course);
    }

    public List<Course> listCourses() {
        return new ArrayList<>(courses);
    }
}
