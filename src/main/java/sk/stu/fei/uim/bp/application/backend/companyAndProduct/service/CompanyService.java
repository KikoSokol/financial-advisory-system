package sk.stu.fei.uim.bp.application.backend.companyAndProduct.service;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Company;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CompanyService
{
    Company addNewCompany(@NotNull Company newCompany);

    Company updateCompany(@NotNull Company companyToUpdate);

    Optional<Company> getCompanyById(@NotNull ObjectId companyId);

    List<Company> getAllCompanyByCurrentAgent(@NotNull ObjectId agentId);

    List<Company> getCompanyByNameOrByIco(@NotNull ObjectId agentId, @NotNull String search);
}
