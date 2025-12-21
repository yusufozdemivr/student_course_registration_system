import java.util.ArrayList;
import java.util.List;

public class Student implements Registrable {

    private String id;
    private String name;


    private final List<Registration> registrations = new ArrayList<>();

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public void registerCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }

        Registration reg = new Registration(this, course);
        if (registrations.contains(reg)) {
            throw new IllegalStateException("Student is already registered to this course: " + course.getCode());
        }

        registrations.add(reg);
    }

    @Override
    public void dropCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }

        Registration reg = new Registration(this, course);
        boolean removed = registrations.remove(reg);


        if (!removed) throw new IllegalStateException("Course not found: " + course.getCode());
    }

    public List<Course> getRegisteredCourses() {
        List<Course> courses = new ArrayList<>();
        for (Registration r : registrations) {
            courses.add(r.getCourse());
        }
        return courses;
    }

    protected int getTotalCredits() {
        int sum = 0;
        for (Course c : getRegisteredCourses()) {
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
