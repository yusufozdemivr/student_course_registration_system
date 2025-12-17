import java.util.Objects;

public class Registration {

    private final Student student;
    private final Course course;

    public Registration(Student student, Course course) {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student and Course cannot be null");
        }
        this.student = student;
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Registration)) return false;
        Registration other = (Registration) obj;
        return student.equals(other.student)
                && course.equals(other.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course);
    }

    @Override
    public String toString() {
        return "Registration [student=" + student + ", course=" + course + "]";
    }
}
