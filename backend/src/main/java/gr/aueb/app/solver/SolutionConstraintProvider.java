package gr.aueb.app.solver;

import gr.aueb.app.application.SupervisionRuleService;
import gr.aueb.app.application.SupervisorPreferenceService;
import gr.aueb.app.domain.Supervision;
import gr.aueb.app.domain.SupervisionRule;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.domain.SupervisorPreference;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import static org.optaplanner.core.api.score.stream.ConstraintCollectors.*;

import java.time.LocalTime;

import static org.optaplanner.core.api.score.stream.Joiners.*;

public class SolutionConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                // Hard constraints
                overlappingSupervisionsConflict(constraintFactory),
                sameExaminationConflict(constraintFactory),
                // Soft constraints
                supervisionRuleLimit(constraintFactory),
                supervisorDateExclusion(constraintFactory),
                supervisorTimePreference(constraintFactory)
        };
    }

    Constraint overlappingSupervisionsConflict(ConstraintFactory constraintFactory) {
        // Hard constraint: Assigned supervisions to the same supervisor should not overlap
        return constraintFactory.forEachUniquePair(Supervision.class, equal(Supervision::getSupervisor))
                .filter((s1, s2) -> !s1.getId().equals(s2.getId())) // Exclude self-joins
                .filter((s1, s2) -> s1.getExamination().getDate().equals(s2.getExamination().getDate()))
                .filter((s1, s2) -> hasOverlap(s1, s2))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Supervisor Overlap");
    }

    Constraint sameExaminationConflict(ConstraintFactory constraintFactory) {
        // Hard constraint: A supervisor can be assigned only once in the same examination
        return constraintFactory.forEachUniquePair(Supervision.class,
                    equal(Supervision::getSupervisor),
                    equal(Supervision::getExamination))
                .filter((s1, s2) -> !s1.getId().equals(s2.getId())) // Exclude self-joins
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Same Examination");
    }

    Constraint supervisionRuleLimit(ConstraintFactory constraintFactory) {
        // Soft constraint: Supervisor should not have more than his type limit
        return constraintFactory.forEach(Supervision.class)
                .groupBy((supervision) -> supervision.getExamination().getExaminationPeriod().getId(),
                        (supervision) -> supervision.getSupervisor(), count())
                .filter((examinationPeriodId, supervisor, count) -> {
                    SupervisionRule rule = findSupervisionRule(examinationPeriodId, supervisor);
                    return rule != null && count > rule.getNumOfSupervisions();
                })
                .penalize(HardSoftScore.ONE_SOFT, (examinationPeriodId, supervisor, count) -> {
                    SupervisionRule rule = findSupervisionRule(examinationPeriodId, supervisor);
                    return count - rule.getNumOfSupervisions();
                })
                .asConstraint("Supervisor type limit exceeded");
    }

    Constraint supervisorDateExclusion(ConstraintFactory constraintFactory) {
        // Soft constraint: a supervisor has set specific dates to be excluded
        return constraintFactory.forEach(Supervision.class)
                .filter((supervision) -> {
                    SupervisorPreference preference = findSupervisorPreference(supervision.getSupervisor().getId(), supervision.getExamination().getExaminationPeriod().getId());
                    return preference != null && preference.getExcludeDates().contains(supervision.getExamination().getDate());
                })
                .penalize(HardSoftScore.ofSoft(50))
                .asConstraint("Supervisor Date Exclusion");
    }

    Constraint supervisorTimePreference(ConstraintFactory constraintFactory) {
        // Soft constraint: a supervisor does not prefer to supervise at this timeline
        return constraintFactory.forEach(Supervision.class)
                .filter((supervision) -> {
                    SupervisorPreference preference = findSupervisorPreference(supervision.getSupervisor().getId(), supervision.getExamination().getExaminationPeriod().getId());
                    return preference != null && hasOverlapGeneral(supervision.getExamination().getStartTime(), supervision.getExamination().getEndTime(), preference.getStartTime(), preference.getEndTime());
                })
                .penalize(HardSoftScore.ofSoft(2))
                .asConstraint("Supervisor Time Preference");
    }

    boolean hasOverlap(Supervision s1, Supervision s2) {
        LocalTime s1ExamStartTime = s1.getExamination().getStartTime();
        LocalTime s1ExamEndTime = s1.getExamination().getEndTime();
        LocalTime s2ExamStartTime = s2.getExamination().getStartTime();
        LocalTime s2ExamEndTime = s2.getExamination().getEndTime();

        return (s1ExamStartTime.isBefore(s2ExamEndTime) || s1ExamStartTime.equals(s2ExamEndTime))
                && (s1ExamEndTime.isAfter(s2ExamStartTime) || s1ExamEndTime.equals(s2ExamStartTime));
    }

    boolean hasOverlapGeneral(LocalTime startTime1, LocalTime endTime1, LocalTime startTime2, LocalTime endTime2) {
        return (startTime1.isBefore(endTime2) || startTime1.equals(endTime2))
                && (endTime1.isAfter(startTime2) || endTime1.equals(startTime2));
    }

    private SupervisionRule findSupervisionRule(Integer examinationPeriodId, Supervisor supervisor) {
        return SupervisionRuleService.findSupervisionRule(examinationPeriodId, supervisor);
    }

    private SupervisorPreference findSupervisorPreference(Integer supervisorId, Integer examinationPeriod) {
        return SupervisorPreferenceService.findSpecificForSolver(supervisorId, examinationPeriod);
    }
}
