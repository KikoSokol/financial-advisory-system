package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.filters;

import org.apache.commons.lang3.StringUtils;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductTypeCategory;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.CompanyDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductTypeDto;

public class ProductFilter
{
    private String name;
    private ProductTypeDto productType;
    private ProductTypeCategory productTypeCategory;
    private CompanyDto company;

    public ProductFilter()
    {
        setName("");
        setProductType(null);
        setProductTypeCategory(null);
        setCompany(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductTypeDto getProductType() {
        return productType;
    }

    public void setProductType(ProductTypeDto productType) {
        this.productType = productType;
    }

    public ProductTypeCategory getProductTypeCategory() {
        return productTypeCategory;
    }

    public void setProductTypeCategory(ProductTypeCategory productTypeCategory) {
        this.productTypeCategory = productTypeCategory;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }


    public boolean test(ProductDto productDto)
    {
        if(this.name.length() > 0 && !StringUtils.containsIgnoreCase(productDto.getName(),this.name))
        {
            return false;
        }

        if(this.productType != null && !this.productType.equals(productDto.getProductType()))
        {
            return false;
        }

        if(this.productTypeCategory != null && !this.productTypeCategory.equals(productDto.getProductType().getCategory()))
        {
            return false;
        }

        if(this.company != null && !this.company.equals(productDto.getCompany()))
        {
            return false;
        }

        return true;
    }
}
