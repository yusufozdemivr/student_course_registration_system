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

}

