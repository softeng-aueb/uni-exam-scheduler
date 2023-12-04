package gr.aueb.app.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupervisionTest {
    private Supervision supervision;

    @BeforeEach
    void setUp() {
        supervision = new Supervision(true, new Examination(), new Supervisor());
    }

    @Test
    void testCreateSupervision() {
        assertNotNull(supervision);
        assertTrue(supervision.getLead());
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
        supervision.setLead(false);
        supervision.setPresent(true);

        assertEquals(1, supervision.getId());
        assertEquals(supervisor, supervision.getSupervisor());
        assertEquals(examination, supervision.getExamination());
        assertFalse(supervision.getLead());
        assertTrue(supervision.getPresent());
    }
}
