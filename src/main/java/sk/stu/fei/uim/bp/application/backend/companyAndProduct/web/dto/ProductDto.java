package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto;

import lombok.Data;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Company;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Product;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductType;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductDto
{
    private ObjectId productId;

    @NotNull(message = "Typ produktu je povinné pole")
    private ProductTypeDto productType;

    @NotBlank(message = "Meno produktu je povinné pole")
    private String name;

    @NotNull(message = "Spoločnosť je povinné pole")
    private CompanyDto company;



    public ProductDto(Product product, ProductType productType, Company company)
    {
        setProductId(product.getProductId());
        setProductType(new ProductTypeDto(productType));
        setName(product.getName());
        setCompany(new CompanyDto(company));
    }

    public ProductDto(Product product)
    {
        setProductId(product.getProductId());
    }

    public Product toProduct(Product product)
    {
        product.setProductId(this.productId);
        product.setProductType(this.productType.getProductTypeId());
        product.setName(this.name);
        product.setCompany(this.company.getCompanyId());

        return product;
    }



}
