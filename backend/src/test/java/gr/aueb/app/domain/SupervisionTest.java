package gr.aueb.app.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupervisionTest {
    private Supervision supervision;

    @BeforeEach
    void setUp() {
        supervision = new Supervision(new Examination(), new Supervisor());
    }

    @Test
    void testCreateSupervision() {
        assertNotNull(supervision);
        assertTrue(supervision.getIsPresent());
        assertFalse(supervision.getIsLead());
        assertNotNull(supervision.getExamination());
        assertNotNull(supervision.getSupervisor());
    }

    @Test
    void testSettersAndGetters() {
        Examination examination = new Examination();
        Supervisor supervisor = new Supervisor();
        supervision.setSupervisor(supervisor);
        supervision.setExamination(examination);
        supervision.setId(1);
        supervision.setIsLead(true);
        supervision.setIsPresent(false);

        assertEquals(1, supervision.getId());
        assertEquals(supervisor, supervision.getSupervisor());
        assertEquals(examination, supervision.getExamination());
        assertTrue(supervision.getIsLead());
        assertFalse(supervision.getIsPresent());
    }
}
