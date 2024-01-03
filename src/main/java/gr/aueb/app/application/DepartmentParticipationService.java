package gr.aueb.app.application;

import gr.aueb.app.domain.DepartmentParticipation;
import gr.aueb.app.persistence.DepartmentParticipationRepository;
import gr.aueb.app.representation.DepartmentParticipationMapper;
import gr.aueb.app.representation.DepartmentParticipationRepresentation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@RequestScoped
public class DepartmentParticipationService {
    @Inject
    DepartmentParticipationRepository departmentParticipationRepository;

    @Inject
    DepartmentParticipationMapper departmentParticipationMapper;

    @Transactional
    public DepartmentParticipation create(DepartmentParticipationRepresentation representation) {
        try {
            DepartmentParticipation newDepartmentParticipation = departmentParticipationMapper.toModel(representation);
            departmentParticipationRepository.persist(newDepartmentParticipation);
            return newDepartmentParticipation;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public DepartmentParticipation updateAttendance(Integer departmentParticipationId, Integer attendance) {
        try {
            DepartmentParticipation departmentParticipation = departmentParticipationRepository.findById(departmentParticipationId);
            departmentParticipation.setAttendance(attendance);
            departmentParticipationRepository.getEntityManager().merge(departmentParticipation);
            return departmentParticipation;
        } catch (Exception e) {
            throw e;
        }
    }
}
