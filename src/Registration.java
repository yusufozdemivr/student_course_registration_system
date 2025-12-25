import java.util.Objects;

public class Registration {

    private final Student student;
    private final Course course;
    private double gradePoint;

    public Registration(Student student, Course course) {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student and Course cannot be null");
        }
        this.student = student;
        this.course = course;
        this.gradePoint = 0.0;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public double getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(double gradePoint) {
        if (gradePoint == 0.0) {
            this.gradePoint = 0.0;
            return;
        }
        if (gradePoint < 0.0 || gradePoint > 4.0) {
            throw new IllegalArgumentException("Grade point must be between 0.0 and 4.0");
        }
        this.gradePoint = gradePoint;
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
