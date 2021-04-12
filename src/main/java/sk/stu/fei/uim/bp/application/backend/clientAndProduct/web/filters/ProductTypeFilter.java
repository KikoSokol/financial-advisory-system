package sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.filters;

import org.apache.commons.lang3.StringUtils;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.domain.ProductTypeCategory;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.dto.ProductTypeDto;


public class ProductTypeFilter
{
    private String name;
    private ProductTypeCategory productTypeCategory;

    public ProductTypeFilter()
    {
        setName("");
        setProductTypeCategory(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductTypeCategory getProductTypeCategory() {
        return productTypeCategory;
    }

    public void setProductTypeCategory(ProductTypeCategory productTypeCategory) {
        this.productTypeCategory = productTypeCategory;
    }


    public boolean test(ProductTypeDto productTypeDto)
    {
        if(this.name.length() > 0 && !StringUtils.containsIgnoreCase(productTypeDto.getName(),this.name))
        {
            return false;
        }

        if(this.productTypeCategory != null && this.productTypeCategory != productTypeDto.getCategory())
        {
            return false;
        }

        return true;
    }

}
