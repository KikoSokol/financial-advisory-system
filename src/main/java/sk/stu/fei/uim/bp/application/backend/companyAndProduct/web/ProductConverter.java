package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Company;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Product;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductType;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.CompanyService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductTypeService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.implementation.CompanyServiceIml;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.implementation.ProductTypeServiceImpl;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProductConverter
{

    private final ProductTypeService productTypeService;
    private final CompanyService companyService;

    @Autowired
    public ProductConverter(ProductTypeServiceImpl productTypeService, CompanyServiceIml companyService)
    {
        this.productTypeService = productTypeService;
        this.companyService = companyService;
    }


    public List<ProductDto> convertProductListToProductDtoList(List<Product> products)
    {
        List<ProductDto> productDtos = new LinkedList<>();

        for(Product product : products)
        {
            productDtos.add(new ProductDto(product,getProductTypeOfProduct(product),getCompanyOfProduct(product)));
        }

        return productDtos;
    }



    private ProductType getProductTypeOfProduct(Product product)
    {
        Optional<ProductType> productTypeOfProduct = this.productTypeService.getProductTypeById(product.getProductType());

        return productTypeOfProduct.get();
    }

    private Company getCompanyOfProduct(Product product)
    {
        Optional<Company> companyOfProduct = this.companyService.getCompanyById(product.getCompany());

        return companyOfProduct.get();
    }

    public ProductDto convertProductToProductDto(Product product)
    {
        return new ProductDto(product,getProductTypeOfProduct(product),getCompanyOfProduct(product));
    }
}
