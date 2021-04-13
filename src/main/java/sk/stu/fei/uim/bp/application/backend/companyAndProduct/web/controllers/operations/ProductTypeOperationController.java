package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.controllers.operations;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductType;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductTypeService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductTypeDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.editors.ProductTypeEditor;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productTypeEvents.ProductTypeCancelEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productTypeEvents.ProductTypeSaveEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productTypeEvents.ProductTypeUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.views.ProductTypeView;


public class ProductTypeOperationController
{
    private final ProductTypeService productTypeService;

    private final ProductTypeView productTypeView;
    private final ObjectId currentAgentId;

    private boolean isNew;
    private ProductType productType;
    private ProductTypeDto productTypeDto;


    public ProductTypeOperationController(ProductTypeService productTypeService, ProductTypeView productTypeView, ObjectId currentAgentId)
    {
        this.productTypeService = productTypeService;
        this.productTypeView = productTypeView;
        this.currentAgentId = currentAgentId;
        this.initActionOfEditor();
        this.clear();
    }


    private void initActionOfEditor()
    {
        ProductTypeEditor productTypeEditor = this.productTypeView.getProductTypeEditor();

        productTypeEditor.addListener(ProductTypeSaveEvent.class,this::doSaveNewProductType);
        productTypeEditor.addListener(ProductTypeUpdateEvent.class,this::doUpdateProductType);
        productTypeEditor.addListener(ProductTypeCancelEvent.class,this::cancelEdit);
    }

    private void openEditor(ProductTypeDto productTypeDto, boolean isNew)
    {
        ProductTypeEditor editor = this.productTypeView.getProductTypeEditor();
        editor.setProductTypeDto(productTypeDto,isNew);
        this.productTypeView.showWindow(editor);
    }


    public void addNewProductType()
    {
        this.isNew = true;

        this.productType = new ProductType();
        this.productType.setAgent(this.currentAgentId);
        this.productTypeDto = new ProductTypeDto(this.productType);

        openEditor(this.productTypeDto,this.isNew);
    }

    public void updateProductType(ProductType productType)
    {
        this.isNew = false;
        this.productType = productType;
        this.productTypeDto = new ProductTypeDto(this.productType);

        openEditor(this.productTypeDto,this.isNew);
    }

    private void doSaveNewProductType(ProductTypeSaveEvent saveEvent)
    {
        this.productTypeDto = saveEvent.getProductTypeDto();

        try
        {
            this.productType = this.productTypeDto.toProductType(this.productType);
            this.productTypeService.addNewProductType(this.productType);
            successOperation("Nový typ produktu bol úspešne pridaný");
        }
        catch (Exception exception)
        {
            this.productTypeView.showErrorMessage("Nový typ produktu sa nepodarilo pridať. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }

    private void doUpdateProductType(ProductTypeUpdateEvent updateEvent)
    {
        this.productTypeDto = updateEvent.getProductTypeDto();

        try
        {
            this.productType = this.productTypeDto.toProductType(this.productType);
            this.productTypeService.updateProductType(this.productType);
            successOperation("Údaje typu produktu boli úspešne zmenené");
        }
        catch (Exception exception)
        {
            this.productTypeView.showErrorMessage("Nepodarilo sa zneniť údaje. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }

    private void cancelEdit(ProductTypeCancelEvent cancelEvent)
    {
        this.clear();
    }


    private void successOperation(String successText)
    {
        this.clear();
        this.productTypeView.refreshTable();
        this.productTypeView.showSuccessOperationNotification(successText);
    }

    public void clear()
    {
        this.productTypeView.getProductTypeEditor().clear();
        this.productTypeView.closeWindow();
        this.isNew = false;
        this.productType = null;
        this.productTypeDto = null;
    }


}
