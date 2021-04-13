package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto;

import lombok.Data;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductType;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductTypeCategory;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductTypeDto
{
    private ObjectId productTypeId;

    @NotNull(message = "Kategória  je povinné pole")
    private ProductTypeCategory category;

    @NotBlank(message = "Meno typu produktu je povinné pole")
    private String name;


    public ProductTypeDto(ProductType productType)
    {
        setProductTypeId(productType.getProductTypeId());
        setCategory(productType.getCategory());
        setName(productType.getName());
    }

    public ProductType toProductType(ProductType productType)
    {
        productType.setProductTypeId(this.productTypeId);
        productType.setCategory(this.category);
        productType.setName(this.name);

        return productType;
    }

    public String getCategoryName()
    {
        return this.category.getName();
    }


}
