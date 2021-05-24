package sk.stu.fei.uim.bp.application.backend.companyAndProduct.repository;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Product;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ProductRepository
{
    Product addNewProduct(@NotNull Product newProduct);

    boolean deleteProduct(@NotNull Product productToDelete);

    Product updateProduct(@NotNull Product productToUpdate);

    Optional<Product> getProductById(@NotNull ObjectId productId);

    List<Product> getAllProductOfCurrentAgent(@NotNull ObjectId currentAgent);

    List<Product> searchProductByName(@NotNull ObjectId agentId, @NotNull String search);

    List<Product> getProductByType(@NotNull ObjectId productTypeId);

    List<Product> getAllProductByTypes(@NotNull List<ObjectId> idOfProductTypes);

    List<Product> getAllProductById(@NotNull List<ObjectId> idOfProducts);

    List<Product> getAllProductByProductType(@NotNull ObjectId productId);
}
