import java.util.Objects;

public class Course {

    private String code;
    private String name;
    private int credit;

    private Instructor instructor;

    public Course(String code, String name, int credit) {
        this.code = code;
        this.name = name;
        this.credit = credit;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getCredit() {
        return credit;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("instructor cannot be null");
        }
        this.instructor = instructor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(code, course.code);
    }

    @Override
    public int hashCode(){
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        String ins = (instructor == null) ? "UNASSIGNED" : instructor.getName();
        return code + " - " + name + " (" + credit + " cr, " + ins + ")";
    }


}
