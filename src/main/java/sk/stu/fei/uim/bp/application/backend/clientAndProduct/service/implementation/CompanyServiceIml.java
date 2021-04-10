package sk.stu.fei.uim.bp.application.backend.clientAndProduct.service.implementation;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.domain.Company;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.repository.CompanyRepository;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.repository.implementation.CompanyRepositoryIml;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.service.CompanyService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceIml implements CompanyService
{
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceIml(CompanyRepositoryIml companyRepository)
    {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company addNewCompany(@NotNull Company newCompany)
    {
        return this.companyRepository.addCompany(newCompany);
    }

    @Override
    public Company updateCompany(@NotNull Company companyToUpdate)
    {
        return this.companyRepository.updateCompany(companyToUpdate);
    }

    @Override
    public Optional<Company> getCompanyById(@NotNull ObjectId companyId)
    {
        return this.companyRepository.getCompanyById(companyId);
    }

    @Override
    public List<Company> getAllCompanyByCurrentAgent(@NotNull ObjectId agentId)
    {
        return this.companyRepository.getAllCompanyOfCurrentAgent(agentId);
    }

    @Override
    public List<Company> getCompanyByNameOrByIco(@NotNull ObjectId agentId, @NotNull String search)
    {
        return this.companyRepository.getCompanyByNameOrByIco(agentId,search);
    }

}
