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

    protected int getTotalCredits() {
        int sum = 0;
        for (Course c : registeredCourses) {
            sum += c.getCredit();
        }
        return sum;
    }

    public double calculateTuition() {
        double baseFee = 500.0;
        double feePerCredit = 100.0;
        return baseFee + getTotalCredits() * feePerCredit;
    }

}
