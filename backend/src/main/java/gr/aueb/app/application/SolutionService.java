package gr.aueb.app.application;

import gr.aueb.app.domain.Examination;
import gr.aueb.app.domain.Solution;
import gr.aueb.app.domain.Supervision;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.persistence.ExaminationRepository;
import gr.aueb.app.persistence.SupervisionRepository;
import gr.aueb.app.persistence.SupervisorRepository;
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
    SolverManager<Solution, Long> solverManager;

    @Inject
    SolutionManager<Solution, HardSoftScore> solutionManager;

    @Transactional
    public void solve(Integer examinationPeriodId) {
        List <Examination> examinationList = examinationRepository.findAllInSamePeriod(examinationPeriodId);
        if(examinationList.isEmpty()) {
            throw new BadRequestException("No examinations have been uploaded");
        }

        // for each examination add a function that based on criteria finds requiredSupervisors
        // now just have 3 on each
        supervisionList = examinationList.stream()
                .peek(examination -> examination.setRequiredSupervisors(3))
                .flatMap(examination -> {
                    List<Supervision> supervisions = new ArrayList<>();
                    for (int i = 0; i < examination.getRequiredSupervisors(); i++) {
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
