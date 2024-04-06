package gr.aueb.app.representation;

import gr.aueb.app.domain.Course;
import gr.aueb.app.domain.Department;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.persistence.DepartmentRepository;
import org.mapstruct.*;

import jakarta.inject.Inject;
import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {DepartmentMapper.class})
public abstract class CourseMapper {
    @Inject
    DepartmentRepository departmentRepository;
    public abstract CourseRepresentation toRepresentation(Course course);

    public abstract List<CourseRepresentation> toRepresentationList(List<Course> courses);

    @Mapping(target = "id", ignore = true)
    public abstract Course toModel(CourseRepresentation representation);

    @AfterMapping
    protected void connectToDepartment(CourseRepresentation representation, @MappingTarget Course course) {
        if (representation.department != null && representation.department.id != null) {
            Department department = departmentRepository.findById(representation.department.id);
            if (department == null) {
                throw new RuntimeException();
            }
            course.setDepartment(department);
        }
    }
}
