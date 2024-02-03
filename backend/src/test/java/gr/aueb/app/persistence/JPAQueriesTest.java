package gr.aueb.app.persistence;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import gr.aueb.app.domain.Examination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class JPAQueriesTest {

    @Inject
    EntityManager em;

    @SuppressWarnings("unchecked")
    @Test
    @TestTransaction
    public void simpleQuery() {
        int EXPECTED_AUCTIONS_NUMBER = 6;
        Query query = em.createQuery("select e from Examination e");
        List<Examination> results = query.getResultList();
        Assertions.assertEquals(EXPECTED_AUCTIONS_NUMBER, results.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    @TestTransaction
    public void restrictionQuery() {
        int EXPECTED_NUMBER_CONTAINING_TERM = 2;
        Query query = em.createQuery("select e from Examination e where e.startDate >= :start and e.startDate < :end")
                .setParameter("start", LocalDate.of(2023, 1, 1))
                .setParameter("end", LocalDate.of(2023, 2, 1));
        List<Examination> results = query.getResultList();
        Assertions.assertEquals(EXPECTED_NUMBER_CONTAINING_TERM,results.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    @TestTransaction
    public void implicitJoin() {
        int EXPECTED_AUCTIONS_WITH_CATEGORY = 3;
        Query query = em.createQuery("select e from Examination e where e.course.courseCode like 'CS%'");
        List<Examination> results = query.getResultList();
        Assertions.assertEquals(EXPECTED_AUCTIONS_WITH_CATEGORY,results.size());
    }
}