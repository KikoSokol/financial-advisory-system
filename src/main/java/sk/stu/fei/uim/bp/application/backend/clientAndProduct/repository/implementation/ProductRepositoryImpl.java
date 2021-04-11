package sk.stu.fei.uim.bp.application.backend.clientAndProduct.repository.implementation;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.domain.Product;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.repository.ProductRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository
{
    private final MongoOperations mongoOperations;

    @Autowired
    public ProductRepositoryImpl(MongoTemplate mongoOperations)
    {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Product addNewProduct(@NotNull Product newProduct)
    {
        return this.mongoOperations.insert(newProduct);
    }

    @Override
    public void deleteProduct(@NotNull Product productToDelete)
    {
        this.mongoOperations.remove(productToDelete);
    }

    @Override
    public Product updateProduct(@NotNull Product productToUpdate)
    {
        return this.mongoOperations.save(productToUpdate);
    }

    @Override
    public Optional<Product> getProductById(@NotNull ObjectId productId)
    {
        Product product = this.mongoOperations.findById(productId,Product.class);

        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> getAllProductOfCurrentAgent(@NotNull ObjectId currentAgent)
    {
        Criteria criteria = new Criteria("agent");
        criteria.is(currentAgent);

        Query query = new Query(criteria);

        return this.mongoOperations.find(query,Product.class);
    }

    @Override
    public List<Product> searchProductByName(@NotNull ObjectId agentId, @NotNull String search)
    {
        Criteria byName = new Criteria("name");
        byName.regex(search,"i");

        Criteria byAgent = new Criteria("agent");
        byAgent.is(agentId);

        Criteria criteria = new Criteria();
        criteria.andOperator(byAgent,byName);

        Query query = new Query(criteria);

        return this.mongoOperations.find(query,Product.class);
    }

    @Override
    public List<Product> getProductByType(@NotNull ObjectId productTypeId)
    {
        Criteria criteria = new Criteria("productType");
        criteria.is(productTypeId);

        Query query = new Query(criteria);

        return this.mongoOperations.find(query,Product.class);
    }

    @Override
    public List<Product> getAllProductByTypes(@NotNull List<ObjectId> idOfProductTypes)
    {
        Criteria criteria = new Criteria("productType");
        criteria.in(idOfProductTypes);

        Query query = new Query(criteria);

        return this.mongoOperations.find(query,Product.class);
    }

    @Override
    public List<Product> getAllProductById(@NotNull List<ObjectId> idOfProducts)
    {
        Criteria criteria = new Criteria("productId");
        criteria.in(idOfProducts);

        Query query = new Query(criteria);

        return this.mongoOperations.find(query,Product.class);
    }







}
