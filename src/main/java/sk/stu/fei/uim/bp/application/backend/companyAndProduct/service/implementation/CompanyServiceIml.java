package sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.implementation;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Company;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Product;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.CompanyRepository;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.ProductRepository;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.implementation.CompanyRepositoryIml;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.implementation.ProductRepositoryImpl;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.CompanyService;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.ContractDocument;
import sk.stu.fei.uim.bp.application.backend.contracts.repository.ContractDocumentRepository;
import sk.stu.fei.uim.bp.application.backend.contracts.repository.implementation.ContractDocumentRepositoryImpl;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceIml implements CompanyService
{
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;
    private final ContractDocumentRepository contractDocumentRepository;

    @Autowired
    public CompanyServiceIml(CompanyRepositoryIml companyRepository,
                             ProductRepositoryImpl productRepository,
                             ContractDocumentRepositoryImpl contractDocumentRepository)
    {
        this.companyRepository = companyRepository;
        this.productRepository = productRepository;
        this.contractDocumentRepository = contractDocumentRepository;
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
    public boolean deleteCompany(@NotNull Company companyToDelete)
    {
        if(isAbleToDelete(companyToDelete))
            return deleteCompanyWithAllProduct(companyToDelete);

        return false;
    }

    private boolean deleteCompanyWithAllProduct(Company companyToDelete)
    {
        List<Product> productsOfCompany = this.productRepository.getAllProductById(companyToDelete.getProducts());

        boolean correctDeleted = this.companyRepository.deleteCompany(companyToDelete);

        if(!correctDeleted)
        {
            return false;
        }

        for(Product product : productsOfCompany)
        {
            this.productRepository.deleteProduct(product);
        }

        return true;
    }

    private boolean isAbleToDelete(Company companyToDelete)
    {
        if(!haveThisCompanyProducts(companyToDelete))
            return true;
        else if(haveThisCompanyProductWhichHaveContract(companyToDelete))
            return false;

        return true;

    }

    private boolean haveThisCompanyProducts(Company company)
    {
        return !company.getProducts().isEmpty();
    }

    private boolean haveThisCompanyProductWhichHaveContract(Company company)
    {
        List<Product> productsOfCompany = this.productRepository.getAllProductById(company.getProducts());

        for(Product product : productsOfCompany)
        {
            if(existsContractWithThisProduct(product))
                return true;
        }

        return false;
    }

    private boolean existsContractWithThisProduct(Product product)
    {
        List<ContractDocument> contract = this.contractDocumentRepository.getAllCurrentVersionOfContractDocumentByProductId(product.getProductId());

        return !contract.isEmpty();
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
