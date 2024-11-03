package gr.aueb.app.application;

import gr.aueb.app.domain.*;
import gr.aueb.app.persistence.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.optaplanner.benchmark.api.PlannerBenchmarkFactory;
import org.optaplanner.benchmark.api.PlannerBenchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BenchmarkService {

    @Inject
    ExaminationRepository examinationRepository;

    @Inject
    SupervisionRepository supervisionRepository;

    @Inject
    SupervisorRepository supervisorRepository;

    @Inject
    CourseDeclarationService courseDeclarationService;

    @Inject
    CourseAttendanceService courseAttendanceService;

    @Transactional
    public Solution initializeBenchmarkProblem(Integer examinationPeriodId) {
        List<Examination> examinationList = examinationRepository.findAllInSamePeriod(examinationPeriodId);
        System.out.println(examinationList.size());

        // set estimations
        for(Examination examination : examinationList) {
            CourseDeclaration courseDeclaration = courseDeclarationService.findSpecific(examination.getCourse().getId(), examination.getExaminationPeriod().getAcademicYear().getId());
            examination.setDeclaration(courseDeclaration);
            Double previousPercentage = getPreviousPercentage(examination);
            examination.setEstimatedAttendance(previousPercentage);
        }
        // Create the solution object (similar to findById method)
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

    private Double getPreviousPercentage(Examination examination) {
        // get declaration and attendance from 2 years back
        AcademicYear previousOneYear = examination.getExaminationPeriod().getAcademicYear().getPreviousYear();
        AcademicYear previousTwoYears = previousOneYear != null ? previousOneYear.getPreviousYear() : null;
        CourseDeclaration previousOneDeclaration = previousOneYear != null ? courseDeclarationService.findSpecific(examination.getCourse().getId(), previousOneYear.getId()) : null;
        CourseDeclaration previousTwoDeclaration = previousTwoYears != null ? courseDeclarationService.findSpecific(examination.getCourse().getId(), previousTwoYears.getId()) : null;
        CourseAttendance previousOneAttendance = previousOneYear != null ? courseAttendanceService.findSpecific(examination.getCourse().getId(), previousOneYear.getId(), examination.getExaminationPeriod().getPeriod()) : null;
        CourseAttendance previousTwoAttendance = previousTwoYears != null ? courseAttendanceService.findSpecific(examination.getCourse().getId(), previousTwoYears.getId(), examination.getExaminationPeriod().getPeriod()) : null;

        // estimation formula
        // if previousOne/TwoYear data is missing assuming that 4/5 was attended
        Double previousOnePercentage = ( previousOneDeclaration == null || previousOneAttendance == null ) ? (double) 4/5 : (double) previousOneAttendance.getAttendance()/previousOneDeclaration.getDeclaration();
        Double previousTwoPercentage = ( previousTwoDeclaration == null || previousTwoAttendance == null ) ? (double) 4/5 : (double) previousTwoAttendance.getAttendance()/previousTwoDeclaration.getDeclaration();
        return previousOnePercentage + previousTwoPercentage;
    }
}
