package gr.aueb.app.application;

import gr.aueb.app.domain.Examination;
import gr.aueb.app.domain.Solution;
import gr.aueb.app.domain.Supervision;
import gr.aueb.app.persistence.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.optaplanner.benchmark.api.PlannerBenchmarkFactory;
import org.optaplanner.benchmark.api.PlannerBenchmark;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BenchmarkService {

    @Inject
    ExaminationRepository examinationRepository;

    @Inject
    CourseAttendanceRepository courseAttendanceRepository;

    @Inject
    CourseDeclarationRepository courseDeclarationRepository;

    @Inject
    SupervisionRepository supervisionRepository;

    @Inject
    SupervisorRepository supervisorRepository;

    @Transactional
    public Solution initializeBenchmarkProblem(Integer examinationPeriodId) {
        List<Examination> examinationList = examinationRepository.findAllInSamePeriod(examinationPeriodId);
        System.out.println(examinationList.size());

        // set estimations
        for(Examination examination : examinationList) {
            examination.setDeclaration(courseDeclarationRepository);
            examination.setEstimatedAttendance(courseDeclarationRepository, courseAttendanceRepository);
        }
        // Create the solution object (similar to your findById method)
        List<Supervision> supervisionList = examinationList.stream()
                .flatMap(examination -> {
                    List<Supervision> supervisions = new ArrayList<>();
                    Integer maxSupervisions = Math.min(examination.getEstimatedSupervisors(), examination.getMaxSupervisors());
                    for (int i = 0; i < maxSupervisions; i++) {
                        Supervision newSupervision = new Supervision(examination);
                        supervisionRepository.persist(newSupervision);
                        supervisions.add(newSupervision);
                    }
                    return supervisions.stream();
                })
                .collect(Collectors.toList());

        Solution solution = new Solution(
                supervisorRepository.findAllWithDetails(),
                supervisionList);  // Fetch all supervisions
        return solution;
    }

    // Method to run the benchmark
    public void runBenchmark(Integer examinationPeriodId) {
        // Initialize the problem instance manually
        Solution problem = initializeBenchmarkProblem(examinationPeriodId);

        // Load the SolverFactory from the solverConfig.xml
        PlannerBenchmarkFactory benchmarkFactory = PlannerBenchmarkFactory.createFromXmlResource("benchmarkConfig.xml");

        // Create the PlannerBenchmark and provide the manually initialized problem
        PlannerBenchmark plannerBenchmark = benchmarkFactory.buildPlannerBenchmark(problem);

        // Run the benchmark
        plannerBenchmark.benchmarkAndShowReportInBrowser();
    }
}
