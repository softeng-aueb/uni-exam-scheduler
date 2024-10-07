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
    CourseAttendanceRepository courseAttendanceRepository;

    @Inject
    CourseDeclarationRepository courseDeclarationRepository;

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
        List<CourseDeclaration> declarationList = courseDeclarationRepository.findAllInSameYear(examinationList.get(0).getExaminationPeriod().getAcademicYear().getId());
        // in order for the calculations/solving to be correct there must be uploaded as many examinations as declarations
        if(declarationList.isEmpty() || examinationList.size() != declarationList.size()) {
            throw new BadRequestException("No declarations have been uploaded");
        }

        // set estimations
        for(Examination examination : examinationList) {
            examination.setDeclaration(courseDeclarationRepository);
            examination.setEstimatedAttendance(courseDeclarationRepository, courseAttendanceRepository);
        }

        // for each examination create as many supervisions as the estimatedNeeded supervisors
        // check if estimated are more than max
        supervisionList = examinationList.stream()
                .flatMap(examination -> {
                    List<Supervision> supervisions = new ArrayList<>();
                    for (int i = 0; i < examination.getEstimatedSupervisors(); i++) {
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
            System.out.println(supervision.getSupervisor().toString());
        }
    }

    @Transactional
    protected void saveBestSolution(Solution solution) {
        System.out.println("SAVING BEST.....");
        for (Supervision supervision : solution.getSupervisionList()) {
            System.out.println(supervision.getSupervisor().getId());
            // TODO this is awfully naive: optimistic locking causes issues if called by the SolverManager
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
}
