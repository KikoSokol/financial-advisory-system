package sk.stu.fei.uim.bp.application.backend.contracts.web.editors;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.validator.BeanValidator;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.ClientConverter;
import sk.stu.fei.uim.bp.application.backend.client.web.components.PhysicalPersonCard;
import sk.stu.fei.uim.bp.application.backend.client.web.components.SearchClientView;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientDto;
import sk.stu.fei.uim.bp.application.backend.client.web.events.searchClientEvent.SearchClientCancelEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.searchClientEvent.SearchGetChoosedClientEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.table.TableClientItem;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Product;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductTypeCategory;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.implementation.ProductServiceImpl;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.ProductConverter;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.PaymentFrequency;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.LifeInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.lifeInsuranceEvents.LifeInsuranceCancelEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.lifeInsuranceEvents.LifeInsuranceSaveEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.lifeInsuranceEvents.LifeInsuranceUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.file.components.FileAttachmentsAddView;
import sk.stu.fei.uim.bp.application.backend.file.components.FileAttachmentsViewer;
import sk.stu.fei.uim.bp.application.backend.file.utils.FileAttachment;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;
import sk.stu.fei.uim.bp.application.validarors.messages.ContractValidatorsMessages;

import java.math.BigDecimal;
import java.util.List;


@Tag("life-insurance-editor")
@JsModule("./views/contracts/editors/life-insurance-editor.js")
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LifeInsuranceEditor extends PolymerTemplate<LifeInsuranceEditor.LifeInsuranceEditorModel> {

    @Id("contractNumber")
    private TextField contractNumber;

    @Id("dateOfStart")
    private DatePicker dateOfStart;

    @Id("payment")
    private BigDecimalField payment;

    @Id("anniversaryDate")
    private DatePicker anniversaryDate;

    @Id("paymentFrequency")
    private Select<PaymentFrequency> paymentFrequency;

    @Id("dateOfEnd")
    private DatePicker dateOfEnd;

    @Id("note")
    private TextArea note;

    @Id("addOwner")
    private Button addOwner;

    @Id("ownerInfo")
    private PhysicalPersonCard ownerInfo;

    @Id("addInsured")
    private Button addInsured;

    @Id("equalsClients")
    private Button equalsClients;

    @Id("insuredInfo")
    private PhysicalPersonCard insuredInfo;

    @Id("cancel")
    private Button cancel;

    @Id("save")
    private Button save;

    @Id("attachmentsView")
    private FileAttachmentsViewer attachmentsView;

    @Id("attachmentsAdd")
    private FileAttachmentsAddView attachmentsAdd;

    private final ObjectId currentAgent = new ObjectId("601b6300dbf3207494372a20");

    private final SearchClientView searchOwnerView;
    private final SearchClientView searchInsuredView;

    private final ClientService clientService;
    private final ClientConverter clientConverter;

    private final ProductService productService;
    private final ProductConverter productConverter;


    private final ConfirmDialog confirmDialog;
    private Dialog searchWindow;

    private boolean isNew;
    private LifeInsuranceDto lifeInsuranceDto;
    private final BeanValidationBinder<LifeInsuranceDto> binder = new BeanValidationBinder<>(LifeInsuranceDto.class);
    @Id("product")
    private Select<ProductDto> product;
    @Id("delete")
    private Button delete;


    @Autowired
    public LifeInsuranceEditor(SearchClientView searchOwnerView,
                               SearchClientView searchInsuredView,
                               ClientServiceImpl clientService,
                               ProductServiceImpl productService,
                               ProductConverter productConverter,
                               ClientConverter clientConverter)
    {
        this.searchOwnerView = searchOwnerView;
        this.searchInsuredView = searchInsuredView;
        this.clientService = clientService;
        this.productService = productService;
        this.productConverter = productConverter;
        this.clientConverter = clientConverter;

        initSearchWindow();
        initProduct();
        initPaymentFrequency();
        initBindFields();
        initOwnerSearchAction();
        initInsuredSearchAction();


        this.isNew = false;

        this.confirmDialog = new ConfirmDialog("Naozaj si prajete zavrieť editor?","Všetky zmeny nebudú uložené !!!","Naozaj chcem odísť",confirmEvent -> exit(),"Chcem ostať",cancelEvent -> closeConfirmDialog());
        this.confirmDialog.setConfirmButtonTheme("error primary");


        this.addOwner.addClickListener(event -> showSearchWindow(this.searchOwnerView));
        this.addInsured.addClickListener(event -> showSearchWindow(this.searchInsuredView));
        this.equalsClients.addClickListener(event -> setEqualsClient());

        this.save.addClickListener(event -> validateAndSave());
        this.cancel.addClickListener(event -> this.confirmDialog.open());

    }

    private void initSearchWindow()
    {
        this.searchWindow = new Dialog();
        this.searchWindow.setModal(true);
        this.searchWindow.setCloseOnOutsideClick(false);
        this.searchWindow.setWidth("70%");
        this.searchWindow.setResizable(true);
    }

    private void initProduct()
    {

        List<Product> productList = this.productService.getAllProductByProductTypeCategory(this.currentAgent, ProductTypeCategory.LIFE_INSURANCE);
        List<ProductDto> productDtos = this.productConverter.convertProductListToProductDtoList(productList);

        this.product.setEmptySelectionAllowed(true);
        this.product.setEmptySelectionCaption("Produkt");
        this.product.setTextRenderer(ProductDto::getName);
        this.product.setItems(productDtos);


    }

    private void initPaymentFrequency()
    {
        this.paymentFrequency.setEmptySelectionAllowed(true);
        this.paymentFrequency.setEmptySelectionCaption("Vyber frekvenciu platby");
        this.paymentFrequency.setItems(PaymentFrequency.values());
        this.paymentFrequency.setTextRenderer(PaymentFrequency::getName);
    }

    private void initOwnerSearchAction()
    {
        this.searchOwnerView.addListener(SearchGetChoosedClientEvent.class,this::setOwner);
        this.searchOwnerView.addListener(SearchClientCancelEvent.class,this::cancelSearchOwner);
        this.searchOwnerView.setSearchAllClient();
    }

    private void initInsuredSearchAction()
    {
        this.searchInsuredView.addListener(SearchGetChoosedClientEvent.class,this::setInsured);
        this.searchInsuredView.addListener(SearchClientCancelEvent.class,this::cancelSearchInsured);
        this.searchInsuredView.setSearchAllClient();
    }

    private void initBindFields()
    {
        this.binder.forField(contractNumber)
                .withValidator(new BeanValidator(LifeInsuranceDto.class,"contractNumber"))
                .bind(LifeInsuranceDto::getContractNumber,LifeInsuranceDto::setContractNumber);

        this.binder.forField(product)
                .withValidator(new BeanValidator(LifeInsuranceDto.class,"product"))
                .withValidator(product -> product.getProductType().getCategory() == ProductTypeCategory.LIFE_INSURANCE, ContractValidatorsMessages.CONTRACT_PRODUCT_LIFE_INSURANCE_MEESAGE)
                .bind(LifeInsuranceDto::getProduct,LifeInsuranceDto::setProduct);

        this.binder.forField(dateOfStart)
                .withValidator(new BeanValidator(LifeInsuranceDto.class,"dateOfStart"))
                .withValidator(date -> date != null, ContractValidatorsMessages.CONTRACT_DOCUMENT_DATE_OF_START_NOT_NULL_MESSAGE)
                .withValidator(date -> anniversaryDate.getValue() == null ? true : date.isBefore(this.anniversaryDate.getValue()),ContractValidatorsMessages.CONTRACT_DOCUMENT_DATE_OF_START_BEFORE_ANNIVERSARY_DATE_MESSAGE)
                .bind(LifeInsuranceDto::getDateOfStart,LifeInsuranceDto::setDateOfStart);

        this.binder.forField(payment)
                .withValidator(new BeanValidator(LifeInsuranceDto.class,"payment"))
                .bind(lifeInsuranceDto1 -> new BigDecimal(String.valueOf(lifeInsuranceDto1.getPayment()))
                ,(o, s) -> o.setPayment(s.doubleValue()));

        this.binder.forField(anniversaryDate)
                .withValidator(new BeanValidator(LifeInsuranceDto.class,"anniversaryDate"))
                .withValidator(date -> date != null, ContractValidatorsMessages.INSURANCE_ANNIVERSARY_DATE_NOT_NULL_MESSAGE)
                .withValidator(date -> dateOfStart.getValue() == null ? true : date.isAfter(dateOfStart.getValue()),ContractValidatorsMessages.INSURANCE_ANNIVERSARY_DATE_AFTER_DATE_OF_START_MESSAGE)
                .bind(LifeInsuranceDto::getAnniversaryDate,LifeInsuranceDto::setAnniversaryDate);

        this.binder.forField(paymentFrequency)
                .withValidator(new BeanValidator(LifeInsuranceDto.class,"paymentFrequency"))
                .bind(LifeInsuranceDto::getPaymentFrequency,LifeInsuranceDto::setPaymentFrequency);

        this.binder.forField(dateOfEnd)
                .bind(LifeInsuranceDto::getDateOfEnd,LifeInsuranceDto::setDateOfEnd);

        this.binder.forField(note)
                .bind(LifeInsuranceDto::getNote,LifeInsuranceDto::setNote);



    }



    public void setLifeInsuranceDto(LifeInsuranceDto lifeInsuranceDto, boolean isNew, List<FileAttachment> attachmentList)
    {
        this.isNew = isNew;
        this.lifeInsuranceDto = lifeInsuranceDto;
        this.binder.readBean(lifeInsuranceDto);



        if(isNew)
        {
            this.ownerInfo.setVisible(false);
            this.insuredInfo.setVisible(false);
            enableEqualsButton(true);
        }
        else
        {
            setInfoAboutOwner(lifeInsuranceDto.getOwner());
            setInfoAboutInsured(lifeInsuranceDto.getInsured());
            enableEqualsButton(false);
        }

        if(isNew || attachmentList.isEmpty())
        {
            this.attachmentsView.setVisible(false);
        }
        else
        {
            this.attachmentsView.setVisible(true);
            this.attachmentsView.setFileAttachments(attachmentList);
        }
    }



    private void setInfoAboutOwner(ClientDto owner)
    {
        this.ownerInfo.init(new TableClientItem(owner));
        this.ownerInfo.setVisible(true);
    }

    private void setInfoAboutInsured(ClientDto insured)
    {
        this.insuredInfo.init(new TableClientItem(insured));
        this.insuredInfo.setVisible(true);
    }

    private void enableEqualsButton(boolean enable)
    {
        this.equalsClients.setEnabled(enable);
        this.equalsClients.setVisible(enable);
    }

    private void showSearchWindow(SearchClientView searchInsuredView)
    {
        this.searchWindow.add(searchInsuredView);
        this.searchWindow.open();
    }

    private void closeSearchWindow()
    {
        this.searchWindow.close();
        this.searchWindow.removeAll();
    }


    private void setOwner(SearchGetChoosedClientEvent event)
    {
        Client client = event.getClient();
        ClientDto clientDto = this.clientConverter.convertClientToClientDto(client);

        this.lifeInsuranceDto.setOwner(clientDto);
        setInfoAboutOwner(this.lifeInsuranceDto.getOwner());
        closeSearchWindow();
    }

    private void setInsured(SearchGetChoosedClientEvent event)
    {
        Client client = event.getClient();
        ClientDto clientDto = this.clientConverter.convertClientToClientDto(client);

        this.lifeInsuranceDto.setInsured(clientDto);
        setInfoAboutInsured(this.lifeInsuranceDto.getInsured());
        closeSearchWindow();
    }


    private void cancelSearchOwner(SearchClientCancelEvent event)
    {
        this.closeSearchWindow();
    }

    private void cancelSearchInsured(SearchClientCancelEvent event)
    {
        this.closeSearchWindow();
    }

    private void setEqualsClient()
    {
        if(this.lifeInsuranceDto.getOwner() != null)
        {
            this.lifeInsuranceDto.setInsured(this.lifeInsuranceDto.getOwner());
            setInfoAboutInsured(this.lifeInsuranceDto.getOwner());
        }
        else if(this.lifeInsuranceDto.getInsured() != null)
        {
            this.lifeInsuranceDto.setOwner(this.lifeInsuranceDto.getInsured());
            this.setInfoAboutOwner(this.lifeInsuranceDto.getInsured());
        }
    }


    private void validateAndSave()
    {
        boolean correct = true;
        if(this.lifeInsuranceDto.getOwner() == null)
        {
            correct = false;
            showErrorMessage(ContractValidatorsMessages.CONTRACT_OWNER_NOT_NULL_MESSAGE);
        }

        if(this.lifeInsuranceDto.getInsured() == null)
        {
            correct = false;
            showErrorMessage(ContractValidatorsMessages.INSURANCE_INSURED_NOT_NULL_MESSAGE);
        }

        
        try
        {
            binder.writeBean(this.lifeInsuranceDto);
            correct = true;
        } catch (ValidationException e) {
            correct = false;
        }

        if(correct)
            save();
        else
            showErrorMessage("Nepodarilo sa uložiť životné poistenie. Skontroluj správnosť údajov");
    }


    private void save()
    {
        if(this.isNew)
        {
            fireEvent(new LifeInsuranceSaveEvent(this,this.lifeInsuranceDto,this.attachmentsAdd.getAddFiles()));
        }
        else
            fireEvent(new LifeInsuranceUpdateEvent(this,this.lifeInsuranceDto,this.attachmentsAdd.getAddFiles()));
    }


    private void showErrorMessage(String errorText)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showErrorMessage(errorText);
    }


    public void clear()
    {
        this.lifeInsuranceDto = null;
        this.binder.setBean(null);
        this.binder.readBean(null);
        this.attachmentsView.clear();
        this.attachmentsAdd.clear();

        this.attachmentsView.setVisible(false);
        this.ownerInfo.setVisible(false);
        this.insuredInfo.setVisible(false);
    }


    private void exit()
    {
        fireEvent(new LifeInsuranceCancelEvent(this,null,null));
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

    public interface LifeInsuranceEditorModel extends TemplateModel {

    }
}
