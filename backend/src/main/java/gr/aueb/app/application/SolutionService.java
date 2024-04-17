package gr.aueb.app.application;

import gr.aueb.app.domain.Examination;
import gr.aueb.app.domain.Solution;
import gr.aueb.app.domain.Supervision;
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
        // now just have 2 on each
        supervisionList = examinationList.stream()
                .peek(examination -> examination.setRequiredSupervisors(2))
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
                this::save);
    }

    @Transactional
    protected Solution findById(Long id) {
        if (!SINGLETON_SOLUTION_ID.equals(id)) {
            throw new IllegalStateException("There is no solution with id (" + id + ").");
        }
        // Occurs in a single transaction, so each initialized lesson references the same timeslot/room instance
        // that is contained by the timeTable's timeslotList/roomList.
        return new Solution(
                supervisorRepository.listAll(),
                supervisionList);
    }

    @Transactional
    protected void save(Solution solution) {
        for (Supervision supervision : solution.getSupervisionList()) {
            System.out.println(supervision.getSupervisor().toString());
            // TODO this is awfully naive: optimistic locking causes issues if called by the SolverManager
            Supervision attachedSupervision = supervisionRepository.findById(supervision.getId());
            attachedSupervision.setSupervisor(supervision.getSupervisor());
        }
    }

    public void setExaminationPeriodId(Integer examinationPeriodId) {
        this.examinationPeriodId = examinationPeriodId;
    }

    public SolverStatus getSolverStatus() {
        return solverManager.getSolverStatus(SINGLETON_SOLUTION_ID);
    }
}
