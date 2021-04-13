package sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.implementation;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Company;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Product;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductType;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductTypeCategory;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.CompanyRepository;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.ProductRepository;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.ProductTypeRepository;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.implementation.CompanyRepositoryIml;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.implementation.ProductRepositoryImpl;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.implementation.ProductTypeRepositoryImpl;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductService;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService
{
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public ProductServiceImpl(ProductRepositoryImpl productRepository, ProductTypeRepositoryImpl productTypeRepository, CompanyRepositoryIml companyRepository)
    {
        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Product addNewProduct(@NotNull Product newProduct)
    {
        Product product = this.productRepository.addNewProduct(newProduct);

        Company company = this.companyRepository.getCompanyById(product.getCompany()).get();

        this.companyRepository.addProductForCompany(company,product.getProductId());

        return product;
    }

    @Override
    public Product updateProduct(@NotNull Product productToUpdate)
    {
        return this.productRepository.updateProduct(productToUpdate);
    }

    @Override
    public Optional<Product> getProductById(@NotNull ObjectId productId)
    {
        return this.productRepository.getProductById(productId);
    }

    @Override
    public List<Product> getAllProductOfCurrentAgent(@NotNull ObjectId currentAgent)
    {
        return this.productRepository.getAllProductOfCurrentAgent(currentAgent);
    }

    @Override
    public List<Product> searchProductByName(@NotNull ObjectId agentId, @NotNull String search)
    {
        return this.productRepository.searchProductByName(agentId,search);
    }

    @Override
    public List<Product> getProductByType(@NotNull ObjectId productTypeId)
    {
        return this.productRepository.getProductByType(productTypeId);
    }

    @Override
    public List<Product> getAllProductByTypes(@NotNull List<ObjectId> idOfProductTypes)
    {
        return this.productRepository.getAllProductByTypes(idOfProductTypes);
    }

    @Override
    public List<Product> getAllProductById(@NotNull List<ObjectId> idOfProducts)
    {
        return this.productRepository.getAllProductById(idOfProducts);
    }

    @Override
    public List<Product> getAllProductByProductTypeCategory(@NotNull ObjectId currentAgent, @NotNull ProductTypeCategory productTypeCategory)
    {
        List<ProductType> allProductTypesByCategory = this.productTypeRepository.getProductTypeByCategory(currentAgent,productTypeCategory);

        List<ObjectId> idOfProductTypes = new LinkedList<>();

        for(ProductType productType : allProductTypesByCategory)
        {
            idOfProductTypes.add(productType.getProductTypeId());
        }

        return this.productRepository.getAllProductByTypes(idOfProductTypes);
    }
}
