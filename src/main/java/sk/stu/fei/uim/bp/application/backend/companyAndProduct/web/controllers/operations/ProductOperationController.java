package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.controllers.operations;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Company;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Product;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductType;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.CompanyService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductTypeService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.CompanyDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductTypeDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.editors.ProductEditor;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productEvents.ProductCancelEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productEvents.ProductSaveEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productEvents.ProductUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productTypeEvents.ProductTypeCancelEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.views.ProductView;

import java.util.List;
import java.util.Optional;

public class ProductOperationController
{
    private final ProductService productService;
    private final ProductTypeService productTypeService;
    private final CompanyService companyService;

    private final ProductView productView;
    private final ObjectId currentAgentId;

    private boolean isNew;
    private Product product;
    private ProductDto productDto;


    public ProductOperationController(ProductService productService, ProductTypeService productTypeService, CompanyService companyService , ProductView productView, ObjectId currentAgentId)
    {
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.companyService = companyService;
        this.productView = productView;
        this.currentAgentId = currentAgentId;

        this.initActionOfEditor();
        this.clear();
    }

    private void initActionOfEditor()
    {
        ProductEditor productEditor = this.productView.getProductEditor();

        productEditor.addListener(ProductSaveEvent.class,this::doSaveNewProduct);
        productEditor.addListener(ProductUpdateEvent.class,this::doUpdateProduct);
        productEditor.addListener(ProductCancelEvent.class,this::cancelEdit);
    }

    private void openEditor(ProductDto productDto, boolean isNew)
    {
        ProductEditor productEditor = this.productView.getProductEditor();

        List<ProductTypeDto> productTypeDtos = this.productView.getListOfProductTypeDto();
        List<CompanyDto> companyDtos = this.productView.getListOfCompanyDto();

        productEditor.setProductDto(productDto,isNew,productTypeDtos,companyDtos);
        this.productView.showWindow(productEditor);
    }


    public void addNewProduct()
    {
        this.isNew = true;

        this.product = new Product();
        this.product.setAgent(this.currentAgentId);
        this.productDto = new ProductDto(this.product);

        openEditor(this.productDto,this.isNew);
    }

    public void updateProduct(Product product)
    {
        this.isNew = false;
        this.product = product;
        this.productDto = new ProductDto(this.product,getProductTypeOfProduct(this.product),getCompanyOfProduct(this.product));

        openEditor(this.productDto,this.isNew);
    }


    private void doSaveNewProduct(ProductSaveEvent event)
    {
        this.productDto = event.getProductDto();

        try
        {
            this.product = this.productDto.toProduct(this.product);
            this.productService.addNewProduct(this.product);
            successOperation("Nový produkt bol úspešne pridaný");
        }
        catch (Exception exception)
        {
            this.productView.showErrorMessage("Nový produkt sa nepodarilo pridať. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }

    private void doUpdateProduct(ProductUpdateEvent event)
    {
        this.productDto = event.getProductDto();

        try
        {
            this.product = this.productDto.toProduct(this.product);
            this.productService.updateProduct(this.product);
            successOperation("Údaje produktu boli úspešne zmenené");
        }
        catch (Exception exception)
        {
            this.productView.showErrorMessage("Nepodarilo sa zmeniť údaje. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }


    private void cancelEdit(ProductCancelEvent cancelEvent)
    {
        this.clear();
    }

    private void successOperation(String successText)
    {
        this.clear();
        this.productView.refreshTable();
        this.productView.showSuccessOperationNotification(successText);
    }

    public void clear()
    {
        this.productView.getProductEditor().clear();
        this.productView.closeWindow();
        this.isNew = false;
        this.product = null;
        this.productDto = null;
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

}
