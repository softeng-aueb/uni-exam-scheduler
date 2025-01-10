package gr.aueb.app.domain;

import static org.junit.jupiter.api.Assertions.*;

import io.quarkus.test.TestTransaction;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.optaplanner.core.api.solver.SolverStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SolutionTest {

    private Solution solution;

    @BeforeEach
    void setup() {
        Department department = new Department("CS");
        Course course = new Course("Algorithms", "CS101", department);
        AcademicYear academicYear = new AcademicYear("2023-2024", new AcademicYear());
        ExaminationPeriod examinationPeriod = new ExaminationPeriod(LocalDate.now(), Period.WINTER, academicYear);
        Examination examination = new Examination(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(13, 0), course, new HashSet<>(), examinationPeriod);
        Supervision supervision = new Supervision(examination);
        Supervisor supervisor = new Supervisor("John", "Doe", "O'Reily", "123456789", "john.doe@example.com", SupervisorCategory.EDIP, department);

        List<Supervision> supervisions = new ArrayList<>();
        supervisions.add(supervision);
        List<Supervisor> supervisors = new ArrayList<>();
        supervisors.add(supervisor);

        solution = new Solution(supervisors, supervisions);
    }

    @Test
    void testSolution() {
        assertNotNull(solution);
        assertEquals(1, solution.getSupervisionList().size());
        assertEquals(1, solution.getSupervisorList().size());
        assertNull(solution.getScore());
        assertNotNull(new Solution());

        solution.setSolverStatus(SolverStatus.NOT_SOLVING);
        assertEquals(SolverStatus.NOT_SOLVING, solution.getSolverStatus());
    }
}
