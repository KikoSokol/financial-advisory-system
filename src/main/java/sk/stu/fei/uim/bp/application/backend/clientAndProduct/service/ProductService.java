package sk.stu.fei.uim.bp.application.backend.clientAndProduct.service;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.domain.Product;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.domain.ProductTypeCategory;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ProductService
{
    Product addNewProduct(@NotNull Product newProduct);

    Product updateProduct(@NotNull Product productToUpdate);

    Optional<Product> getProductById(@NotNull ObjectId productId);

    List<Product> getAllProductOfCurrentAgent(@NotNull ObjectId currentAgent);

    List<Product> searchProductByName(@NotNull ObjectId agentId, @NotNull String search);

    List<Product> getProductByType(@NotNull ObjectId productTypeId);

    List<Product> getAllProductByTypes(@NotNull List<ObjectId> idOfProductTypes);

    List<Product> getAllProductById(@NotNull List<ObjectId> idOfProducts);

    List<Product> getAllProductByProductTypeCategory(@NotNull ObjectId currentAgent, @NotNull ProductTypeCategory productTypeCategory);
}
