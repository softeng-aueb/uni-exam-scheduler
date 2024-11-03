package gr.aueb.app.application;

import gr.aueb.app.domain.Department;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class DepartmentServiceTest {

    @Inject
    DepartmentService departmentService;

    @Test
    @TestTransaction
    @Transactional
    public void testFindAll() {
        List<Department> foundDepartments = departmentService.findAll();

        assertEquals(8, foundDepartments.size());
    }
}
