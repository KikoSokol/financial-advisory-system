package sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.implementation;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductType;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductTypeCategory;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.ProductTypeRepository;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository.implementation.ProductTypeRepositoryImpl;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductTypeService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class ProductTypeServiceImpl implements ProductTypeService
{
    private final ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeServiceImpl(ProductTypeRepositoryImpl productTypeRepository)
    {
        this.productTypeRepository = productTypeRepository;
    }

    @Override
    public ProductType addNewProductType(@NotNull ProductType newProductType)
    {
        return this.productTypeRepository.addNewProductType(newProductType);
    }

    @Override
    public ProductType updateProductType(@NotNull ProductType productTypeToUpdate)
    {
        return this.productTypeRepository.updateProductType(productTypeToUpdate);
    }

    @Override
    public Optional<ProductType> getProductTypeById(@NotNull ObjectId productTypeId)
    {
        return this.productTypeRepository.getProductTypeById(productTypeId);
    }

    @Override
    public List<ProductType> getAllProductTypeOfCurrentAgent(@NotNull ObjectId currentAgent)
    {
        return this.productTypeRepository.getAllProductTypeOfCurrentAgent(currentAgent);
    }

    @Override
    public List<ProductType> searchProductTypeByName(@NotNull ObjectId currentAgent, @NotNull String search)
    {
        return this.productTypeRepository.searchProductTypeByName(currentAgent,search);
    }

    @Override
    public List<ProductType> getProductTypeByCategory(@NotNull ObjectId currentAgent,@NotNull ProductTypeCategory productTypeCategory)
    {
        return this.productTypeRepository.getProductTypeByCategory(currentAgent,productTypeCategory);
    }
}
