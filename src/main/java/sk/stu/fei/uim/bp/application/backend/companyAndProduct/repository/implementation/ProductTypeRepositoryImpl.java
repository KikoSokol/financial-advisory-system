package sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.implementation;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductType;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductTypeCategory;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.ProductTypeRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductTypeRepositoryImpl implements ProductTypeRepository
{
    private final MongoOperations mongoOperations;

    @Autowired
    public ProductTypeRepositoryImpl(MongoTemplate mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public ProductType addNewProductType(@NotNull ProductType newProductType)
    {
        return this.mongoOperations.insert(newProductType);
    }

    @Override
    public void deleteProductType(@NotNull ProductType productTypeToDelete)
    {
        this.mongoOperations.remove(productTypeToDelete);
    }

    @Override
    public ProductType updateProductType(@NotNull ProductType productTypeToUpdate)
    {
        return this.mongoOperations.save(productTypeToUpdate);
    }

    @Override
    public Optional<ProductType> getProductTypeById(@NotNull ObjectId productTypeId)
    {
        ProductType productType = this.mongoOperations.findById(productTypeId,ProductType.class);

        return Optional.ofNullable(productType);
    }

    @Override
    public List<ProductType> getAllProductTypeOfCurrentAgent(@NotNull ObjectId currentAgent)
    {
        Criteria criteria = new Criteria("agent");
        criteria.is(currentAgent);

        Query query = new Query(criteria);

        return this.mongoOperations.find(query,ProductType.class);
    }

    @Override
    public List<ProductType> searchProductTypeByName(@NotNull ObjectId currentAgent, @NotNull String search)
    {
        Criteria byAgent = new Criteria("agent");
        byAgent.is(currentAgent);

        Criteria byName = new Criteria("name");
        byName.regex(search,"i");

        Criteria allCriteria = new Criteria();
        allCriteria.andOperator(byAgent,byName);

        Query query = new Query(allCriteria);

        return this.mongoOperations.find(query,ProductType.class);
    }

    @Override
    public List<ProductType> getProductTypeByCategory(@NotNull ObjectId currentAgent,@NotNull ProductTypeCategory productTypeCategory)
    {

        Criteria byAgent = new Criteria("agent");
        byAgent.is(currentAgent);

        Criteria byProductTypeCategory = new Criteria("category");
        byProductTypeCategory.is(productTypeCategory);

        Criteria allCriteria = new Criteria();
        allCriteria.andOperator(byAgent,byProductTypeCategory);

        Query query = new Query(allCriteria);

        return this.mongoOperations.find(query,ProductType.class);
    }
}
