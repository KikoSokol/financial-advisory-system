package sk.stu.fei.uim.bp.application.backend.client.repository;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;

import java.util.List;

public interface PhysicalPersonRepository
{

    PhysicalPerson addClientCompanyForPhysicalPerson(PhysicalPerson physicalPerson, ObjectId clientCompanyToAdd);

    PhysicalPerson removeClientCompanyFromPhysicalPerson(PhysicalPerson physicalPerson, ObjectId clientCompanyToRemove);

    List<PhysicalPerson> getPhysicalPersonByNameOrBySurnameOrByEmailOrByPersonalNumber(String search);
}
