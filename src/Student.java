import java.util.ArrayList;
import java.util.List;

public class Student {

    private String id;
    private String name;
    protected List<Course> registeredCourses = new ArrayList<>();

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public double calculateTuition() {
        return 1000.0;
    }
}
