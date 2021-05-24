package sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Company;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository
{
    Company addCompany(@NotNull Company newCompany);

    boolean deleteCompany(@NotNull Company companyToDelete);

    Company updateCompany(@NotNull Company companyToUpdate);

    Optional<Company> getCompanyById(@NotNull ObjectId companyId);

    List<Company> getAllCompanyOfCurrentAgent(@NotNull ObjectId currentAgent);

    Company addProductForCompany(@NotNull Company company, @NotNull ObjectId productToAdd);

    Company removeProductFromCompany(@NotNull Company company, @NotNull ObjectId productToDelete);

    List<Company> getCompanyByNameOrByIco(@NotNull ObjectId agentId, @NotNull String search);
}
