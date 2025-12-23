import java.util.ArrayList;
import java.util.List;

public class CourseCatalog {

    private final List<Course> courses = new ArrayList<>();

    public void addCourse(Course course, Instructor instructor) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }

        if (instructor == null) {
            throw new IllegalArgumentException("Instructor cannot be null.");
        }

        if (courses.contains(course)) {
            throw new IllegalStateException("Course already exists in catalog: " + course.getCode());
        }
        course.setInstructor(instructor);
        courses.add(course);
    }

    public void addCourse(Course course) {
        throw new UnsupportedOperationException(
                "Instructor is required."
        );
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
