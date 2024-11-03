package gr.aueb.app.application;

import gr.aueb.app.domain.*;
import gr.aueb.app.persistence.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@RequestScoped
public class CourseDeclarationService {
    @Inject
    CourseRepository courseRepository;

    @Inject
    AcademicYearRepository academicYearRepository;

    @Inject
    CourseDeclarationRepository courseDeclarationRepository;

    @Transactional
    public void upload(Workbook workbook, Integer academicYearId) {
        Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Skip the header row

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String courseCode = String.valueOf((int) row.getCell(0).getNumericCellValue());
            Integer declaration = (int) row.getCell(1).getNumericCellValue();

            Course course = courseRepository.findCourseByCode(courseCode);
            AcademicYear academicYear = academicYearRepository.findById(academicYearId);

            CourseDeclaration courseDeclaration = new CourseDeclaration(declaration, course, academicYear);
            courseDeclarationRepository.persist(courseDeclaration);
        }
    }

    @Transactional
    public List<CourseDeclaration> findAll() {
        return courseDeclarationRepository.findAllWithDetails();
    }

    @Transactional
    public CourseDeclaration findSpecific(Integer courseId, Integer academicYearId) {
        return courseDeclarationRepository.findSpecific(courseId, academicYearId);
    }

    @Transactional
    public List<CourseDeclaration> findAllInSameYear(Integer academicYearId) {
        return courseDeclarationRepository.findAllInSameYear(academicYearId);
    }
}
