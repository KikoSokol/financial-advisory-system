package sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.implementation;

import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Company;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.CompanyRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public class CompanyRepositoryIml implements CompanyRepository
{
    private final MongoOperations mongoOperations;

    @Autowired
    public CompanyRepositoryIml(MongoTemplate mongoOperations)
    {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Company addCompany(@NotNull Company newCompany)
    {
        return this.mongoOperations.insert(newCompany);
    }

    @Override
    public boolean deleteCompany(@NotNull Company companyToDelete)
    {
        DeleteResult deleteResult = this.mongoOperations.remove(companyToDelete);

        return deleteResult.wasAcknowledged();
    }

    @Override
    public Company updateCompany(@NotNull Company companyToUpdate)
    {
        return this.mongoOperations.save(companyToUpdate);
    }

    @Override
    public Optional<Company> getCompanyById(@NotNull ObjectId companyId)
    {
        Company company = this.mongoOperations.findById(companyId,Company.class);

        return Optional.ofNullable(company);
    }

    @Override
    public List<Company> getAllCompanyOfCurrentAgent(@NotNull ObjectId currentAgent)
    {
        Criteria criteria = new Criteria("agent");
        criteria.is(currentAgent);

        Query query = new Query(criteria);

        return this.mongoOperations.find(query,Company.class);
    }

    @Override
    public Company addProductForCompany(@NotNull Company company, @NotNull ObjectId productToAdd)
    {
        Update update = new Update();
        update.addToSet("products",productToAdd);

        return updateProductsOfCompany(company,update);
    }

    @Override
    public Company removeProductFromCompany(@NotNull Company company, @NotNull ObjectId productToDelete)
    {
        Update update = new Update();
        update.pull("products",productToDelete);

        return updateProductsOfCompany(company,update);
    }

    private Company updateProductsOfCompany(@NotNull Company company, Update update)
    {
        Criteria criteria = new Criteria("companyId");
        criteria.is(company.getCompanyId());

        Query query = new Query(criteria);

        return this.mongoOperations.update(Company.class)
                .matching(query)
                .apply(update)
                .withOptions(FindAndModifyOptions.options().returnNew(true))
                .findAndModifyValue();
    }

    @Override
    public List<Company> getCompanyByNameOrByIco(@NotNull ObjectId agentId,@NotNull String search)
    {
        Criteria byAgent = new Criteria("agent");
        byAgent.is(agentId);

        Criteria byName = new Criteria("name").regex(search,"i");
        Criteria byIco = new Criteria("ico").regex(search,"i");

        Criteria bySearch = new Criteria();
        bySearch.orOperator(byName,byIco);

        Criteria allCriteria = new Criteria();
        allCriteria.andOperator(byAgent,bySearch);

        Query query = new Query(allCriteria);

        return this.mongoOperations.find(query,Company.class);
    }

}
