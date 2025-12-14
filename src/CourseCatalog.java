import java.util.ArrayList;
import java.util.List;

public class CourseCatalog {

    private final List<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        if (courses.contains(course)) {
            throw new IllegalStateException("Course already exists in catalog: " + course.getCode());
        }
        courses.add(course);
    }

    public void removeCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        courses.remove(course);
    }

    public List<Course> listCourses() {
        return new ArrayList<>(courses);
    }
}
