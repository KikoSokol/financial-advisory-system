package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.editors;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.CompanyDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductTypeDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productEvents.ProductCancelEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productEvents.ProductSaveEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productEvents.ProductUpdateEvent;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;
import sk.stu.fei.uim.bp.application.validarors.messages.ProductTypeValidatorsMessages;
import sk.stu.fei.uim.bp.application.validarors.messages.ProductValidatorsMessages;

import java.util.List;


@Tag("product-editor")
@JsModule("./views/product/product-editor.js")
public class ProductEditor extends PolymerTemplate<ProductEditor.ProductEditorModel> {

    @Id("name")
    private TextField name;
    @Id("productType")
    private Select<ProductTypeDto> productType;
    @Id("company")
    private Select<CompanyDto> company;
    @Id("cancel")
    private Button cancel;
    @Id("save")
    private Button save;

    private final ConfirmDialog confirmDialog;

    private boolean isNew;
    private ProductDto productDto;
    private final BeanValidationBinder<ProductDto> binder = new BeanValidationBinder<>(ProductDto.class);


    public ProductEditor()
    {
        initProductType();
        initCompany();
        isNew = false;

        this.confirmDialog = new ConfirmDialog("Naozaj si prajete zavrieť editor?","Všetky zmeny nebudú uložené !!!","Naozaj chcem odísť",confirmEvent -> exit(),"Chcem ostať",cancelEvent -> closeConfirmDialog());
        this.confirmDialog.setConfirmButtonTheme("error primary");

        this.save.addClickListener(event -> validateAndSave());
        this.cancel.addClickListener(event-> this.confirmDialog.open());

        binder.forField(name)
                .withValidator(n -> n.length() > 0, ProductValidatorsMessages.PRODUCT_NAME_NOT_BLANK_MESSAGE)
                .bind(ProductDto::getName,ProductDto::setName);

        binder.forField(productType)
                .withValidator(type -> type != null, ProductValidatorsMessages.PRODUCT_TYPE_NOT_NULL_MESSAGE)
                .bind(ProductDto::getProductType,ProductDto::setProductType);

        binder.forField(company)
                .withValidator(c -> c != null, ProductValidatorsMessages.PRODUCT_COMPANY_NOT_NULL_MESSAGE)
                .bind(ProductDto::getCompany,ProductDto::setCompany);
    }

    private void initProductType()
    {
        this.productType.setEmptySelectionAllowed(true);
        this.productType.setEmptySelectionCaption("Vyber typ produktu");
        this.productType.setTextRenderer(ProductTypeDto::getName);
    }

    private void initCompany()
    {
        this.company.setEmptySelectionAllowed(true);
        this.company.setEmptySelectionCaption("Vyber spločnosť");
        this.company.setTextRenderer(CompanyDto::getName);
    }

    public void setProductDto(ProductDto productDto, boolean isNew, List<ProductTypeDto> productTypeDtos, List<CompanyDto> companyDtos)
    {
        this.productType.setItems(productTypeDtos);
        this.company.setItems(companyDtos);

        this.isNew = isNew;
        this.productDto = productDto;
        this.binder.readBean(this.productDto);
    }

    private void validateAndSave()
    {
        try
        {
            binder.writeBean(this.productDto);
            save();
        } catch (ValidationException e) {
            showErrorMessage("Nepodarilo sa uložiť produkt! Skontroluj správnosť údajov.");
        }
    }

    private void save()
    {
        if(this.isNew)
            fireEvent(new ProductSaveEvent(this,this.productDto));
        else
            fireEvent(new ProductUpdateEvent(this,this.productDto));
    }

    private void showErrorMessage(String errorText)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showErrorMessage(errorText);
    }

    public void clear()
    {
        this.isNew = false;
        this.binder.setBean(null);
        this.binder.readBean(null);
    }

    private void exit()
    {
        fireEvent(new ProductCancelEvent(this,null));
    }

    private void closeConfirmDialog()
    {
        if(this.confirmDialog.isOpened())
        {
            this.confirmDialog.close();
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener)
    {
        return getEventBus().addListener(eventType,listener);
    }


    public interface ProductEditorModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
