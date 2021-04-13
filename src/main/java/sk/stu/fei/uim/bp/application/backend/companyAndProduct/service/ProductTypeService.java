package sk.stu.fei.uim.bp.application.backend.companyAndProduct.service;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductType;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductTypeCategory;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ProductTypeService
{
    ProductType addNewProductType(@NotNull ProductType newProductType);

    ProductType updateProductType(@NotNull ProductType productTypeToUpdate);

    Optional<ProductType> getProductTypeById(@NotNull ObjectId productTypeId);

    List<ProductType> getAllProductTypeOfCurrentAgent(@NotNull ObjectId currentAgent);

    List<ProductType> searchProductTypeByName(@NotNull ObjectId currentAgent, @NotNull String search);

    List<ProductType> getProductTypeByCategory(@NotNull ObjectId currentAgent,@NotNull ProductTypeCategory productTypeCategory);
}
