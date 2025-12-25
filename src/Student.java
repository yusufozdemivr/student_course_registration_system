import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student implements Registrable {

    private String id;
    private String name;


    private final List<Registration> registrations = new ArrayList<>();

    public Student(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Student id cannot be null/blank.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Student name cannot be null/blank.");
        }
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

        Registration registration = new Registration(this, course);

        if (registrations.contains(registration)) {
            throw new IllegalStateException(
                    "Student is already registered to this course: " + course.getCode()
            );
        }

        registrations.add(registration);
    }

    @Override
    public void dropCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }

        Registration registration = new Registration(this, course);
        boolean removed = registrations.remove(registration);


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

    public double calculateGPA() {
        double totalPoints = 0.0;
        double totalCredits = 0;

        for (Registration r : registrations) {
            Double gp = r.getGradePoint();
            if (gp == null) {
                continue;
            }
            int credit = r.getCourse().getCredit();
            totalPoints += gp * credit;
            totalCredits += credit;
        }
        if (totalPoints == 0) return 0.0;
        return totalPoints / totalCredits;
    }

    public void setGradePoint(Course course, double gradePoint) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }

        Registration key = new Registration(this, course);
        int idx = registrations.indexOf(key);

        if (idx < 0) {
            throw new IllegalStateException("Student is not registered to this course: " + course.getCode());
        }

        registrations.get(idx).setGradePoint(gradePoint);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
