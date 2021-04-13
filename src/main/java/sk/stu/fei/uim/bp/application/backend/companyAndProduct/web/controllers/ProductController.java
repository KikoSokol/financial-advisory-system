package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.controllers;


import com.vaadin.flow.data.provider.ListDataProvider;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Company;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Product;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductType;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.CompanyService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductTypeService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.filters.ProductFilter;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.views.ProductView;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductController
{
    private final ProductService productService;
    private final ProductTypeService productTypeService;
    private final CompanyService companyService;

    private final ProductView productView;
    private final ObjectId currentAgentId;

    private List<ProductDto> allProducts;
    private final ListDataProvider<ProductDto> productDataProvider;
    private final ProductFilter productFilter;

    public ProductController(ProductService productService, ProductTypeService productTypeService, CompanyService companyService, ProductView productView, ObjectId currentAgentId)
    {
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.companyService = companyService;
        this.productView = productView;
        this.currentAgentId = currentAgentId;
        this.productFilter = new ProductFilter();

        this.allProducts = loadDataToTable();
        this.productDataProvider = new ListDataProvider<>(this.allProducts);

        this.productView.getProductTable().setDataProvider(this.productDataProvider);

        addFilterToProductDataProvider();
    }

    private List<ProductDto> loadDataToTable()
    {
        List<Product> allProducts = this.productService.getAllProductOfCurrentAgent(this.currentAgentId);

        return convertProductListToDtoList(allProducts);
    }

    private List<ProductDto> convertProductListToDtoList(List<Product> products)
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

    public void refreshTableData()
    {
        this.allProducts.clear();
        this.allProducts.addAll(loadDataToTable());
        this.productDataProvider.refreshAll();

    }

    private void addFilterToProductDataProvider()
    {
        this.productDataProvider.setFilter(productDto -> this.productFilter.test(productDto));
    }

    public Optional<Product> getProductById(ObjectId productId)
    {
        return this.productService.getProductById(productId);
    }


    public ProductFilter getProductFilter() {
        return this.productFilter;
    }

    public void filterDataInTable()
    {
        this.productDataProvider.refreshAll();
    }


}
