package gr.aueb.app.application;

import gr.aueb.app.domain.*;
import gr.aueb.app.persistence.*;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolutionManager;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SolutionService {
    public static final Long SINGLETON_SOLUTION_ID = 1L;
    private Integer examinationPeriodId = 0;
    private List<Supervision> supervisionList;
    @Inject
    SupervisionRepository supervisionRepository;

    @Inject
    SupervisorRepository supervisorRepository;

    @Inject
    ExaminationRepository examinationRepository;

    @Inject
    CourseDeclarationService courseDeclarationService;

    @Inject
    CourseAttendanceService courseAttendanceService;

    @Inject
    SolverManager<Solution, Long> solverManager;

    @Inject
    SolutionManager<Solution, HardSoftScore> solutionManager;

    @Transactional
    public void solve(Integer examinationPeriodId) {
        //
        List<Supervision> foundSupervisions = supervisionRepository.findAllInSamePeriod(examinationPeriodId);
        if(!foundSupervisions.isEmpty()) {
            throw new BadRequestException("Already scheduled supervisions");
        }
        List<Examination> examinationList = examinationRepository.findAllInSamePeriod(examinationPeriodId);
        if(examinationList.isEmpty()) {
            throw new BadRequestException("No examinations have been uploaded");
        }
        List<CourseDeclaration> declarationList = courseDeclarationService.findAllInSameYear(examinationList.get(0).getExaminationPeriod().getAcademicYear().getId());
        // in order for the calculations/solving to be correct there must be uploaded as many examinations as declarations
        if(declarationList.isEmpty()) {
            throw new BadRequestException("No declarations have been uploaded");
        }

        // set estimations
        for(Examination examination : examinationList) {
            CourseDeclaration courseDeclaration = courseDeclarationService.findSpecific(examination.getCourse().getId(), examination.getExaminationPeriod().getAcademicYear().getId());
            examination.setDeclaration(courseDeclaration);
            Double previousPercentage = getPreviousPercentage(examination);
            examination.setEstimatedAttendance(previousPercentage);
        }

        // for each examination create as many supervisions as the estimatedNeeded supervisors
        // check if estimated are more than max
        supervisionList = examinationList.stream()
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

//        return supervisionList;

        // instantiate solution
        setExaminationPeriodId(examinationPeriodId);
        solverManager.solveAndListen(SINGLETON_SOLUTION_ID,
                this::findById,
                this::saveSolution,
                this::saveBestSolution,
                null);
    }

    @Transactional
    protected Solution findById(Long id) {
        System.out.println("STARTING SOLVING.....");
        if (!SINGLETON_SOLUTION_ID.equals(id)) {
            throw new IllegalStateException("There is no solution with id (" + id + ").");
        }

        return new Solution(
                supervisorRepository.findAllWithDetails(),
                supervisionList);
    }

    //@Transactional
    protected void saveSolution(Solution solution) {
        System.out.println("SAVING.....");
        for (Supervision supervision : solution.getSupervisionList()) {
            //System.out.println(supervision.getSupervisor().toString());
        }
    }

    @Transactional
    protected void saveBestSolution(Solution solution) {
        System.out.println("SAVING BEST.....");
        for (Supervision supervision : solution.getSupervisionList()) {
            //System.out.println(supervision.getSupervisor().getId());
            // TODO this is naive: optimistic locking causes issues if called by the SolverManager
            Supervision attachedSupervision = supervisionRepository.findById(supervision.getId());
            Supervisor attachedSupervisor = supervisorRepository.findById(supervision.getSupervisor().getId());
            attachedSupervision.setSupervisor(attachedSupervisor);
        }
    }

    public void setExaminationPeriodId(Integer examinationPeriodId) {
        this.examinationPeriodId = examinationPeriodId;
    }

    public SolverStatus getSolverStatus() {
        return solverManager.getSolverStatus(SINGLETON_SOLUTION_ID);
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
