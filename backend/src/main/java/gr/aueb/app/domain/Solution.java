package gr.aueb.app.domain;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;

import java.util.List;

@PlanningSolution
public class Solution {
    @ProblemFactCollectionProperty
    @ValueRangeProvider
    private List<Supervisor> supervisorList;

    @PlanningEntityCollectionProperty
    private List<Supervision> supervisionList;

    @PlanningScore
    private HardSoftScore score;

    // Ignored by OptaPlanner, used by the UI to display solve or stop solving button
    private SolverStatus solverStatus;

    // No-arg constructor required for OptaPlanner
    public Solution() {
    }

    public Solution(List<Supervisor> supervisorList, List<Supervision> supervisionList) {
        this.supervisorList = supervisorList;
        this.supervisionList = supervisionList;
    }

    // ************************************************************************
    // Getters and setters
    // ************************************************************************

    public List<Supervisor> getSupervisorList() {
        return supervisorList;
    }

    public List<Supervision> getSupervisionList() {
        return supervisionList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public SolverStatus getSolverStatus() {
        return solverStatus;
    }

    public void setSolverStatus(SolverStatus solverStatus) {
        this.solverStatus = solverStatus;
    }
}
