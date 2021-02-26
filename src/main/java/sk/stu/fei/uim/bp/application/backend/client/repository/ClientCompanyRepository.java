package sk.stu.fei.uim.bp.application.backend.client.repository;

import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import sk.stu.fei.uim.bp.application.backend.client.domain.ClientCompany;

public interface ClientCompanyRepository
{
    ClientCompany addManagerToClientCompany(@NotNull ClientCompany clientCompany, @NotNull ObjectId physicalPerson);

    ClientCompany removeManagerFromClientCompany(@NotNull ClientCompany clientCompany, @NotNull ObjectId physicalPerson);
}
