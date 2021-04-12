package sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.editors;

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

import sk.stu.fei.uim.bp.application.backend.clientAndProduct.domain.ProductTypeCategory;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.dto.ProductTypeDto;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.events.productTypeEvents.ProductTypeCancelEvent;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.events.productTypeEvents.ProductTypeSaveEvent;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.events.productTypeEvents.ProductTypeUpdateEvent;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;
import sk.stu.fei.uim.bp.application.validarors.messages.ProductTypeValidatorsMessages;


@Tag("product-type-editor")
@JsModule("./views/product/product-type-editor.js")
public class ProductTypeEditor extends PolymerTemplate<ProductTypeEditor.ProductTypeEditorModel> {

    @Id("name")
    private TextField name;
    @Id("category")
    private Select<ProductTypeCategory> category;
    @Id("cancel")
    private Button cancel;
    @Id("save")
    private Button save;

    private final ConfirmDialog confirmDialog;

    private boolean isNew;
    private ProductTypeDto productTypeDto;
    private final BeanValidationBinder<ProductTypeDto> binder = new BeanValidationBinder<>(ProductTypeDto.class);



    public ProductTypeEditor()
    {
        isNew = false;

        this.confirmDialog = new ConfirmDialog("Naozaj si prajete zavrieť editor?","Všetky zmeny nebudú uložené !!!","Naozaj chcem odísť",confirmEvent -> exit(),"Chcem ostať",cancelEvent -> closeConfirmDialog());
        this.confirmDialog.setConfirmButtonTheme("error primary");

        save.addClickListener(click -> validateAndSave());
        cancel.addClickListener(click -> this.confirmDialog.open());


        initCategory();

        binder.forField(category)
                .withValidator(category -> category != null, ProductTypeValidatorsMessages.PRODUCT_TYPE_CATEGORY_NOT_NULL_MESSAGE)
                .bind(ProductTypeDto::getCategory,ProductTypeDto::setCategory);

        binder.forField(name)
                .withValidator(n -> n.length() > 0, ProductTypeValidatorsMessages.PRODUCT_TYPE_NAME_NOT_BLANK_MESSAGES)
                .bind(ProductTypeDto::getName,ProductTypeDto::setName);



    }

    private void initCategory()
    {
        this.category.setEmptySelectionAllowed(true);
        this.category.setEmptySelectionCaption("Vyber kategóriu");
        this.category.setItems(ProductTypeCategory.values());
        this.category.setTextRenderer(ProductTypeCategory::getName);
    }

    public void setProductTypeDto(ProductTypeDto productTypeDto,boolean isNew)
    {
        this.isNew = isNew;
        this.productTypeDto = productTypeDto;
        this.binder.readBean(productTypeDto);
    }

    private void validateAndSave()
    {
        try
        {
            binder.writeBean(this.productTypeDto);
            save();
        } catch (ValidationException e) {
            showErrorMessage("Nepodarilo sa uložiť typ produktu! Skontroluj správnosť údajov.");
        }
    }

    private void save()
    {
        if(isNew)
            fireEvent(new ProductTypeSaveEvent(this,this.productTypeDto));
        else
            fireEvent(new ProductTypeUpdateEvent(this,this.productTypeDto));
    }



    private void showErrorMessage(String errorText)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showErrorMessage(errorText);
    }

    public void clear()
    {
        this.productTypeDto = null;
        this.binder.setBean(null);
        this.binder.readBean(null);
    }

    private void exit()
    {
        fireEvent(new ProductTypeCancelEvent(this,null));
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



    public interface ProductTypeEditorModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
