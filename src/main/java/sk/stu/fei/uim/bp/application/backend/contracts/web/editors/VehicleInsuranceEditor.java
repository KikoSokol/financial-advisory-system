package sk.stu.fei.uim.bp.application.backend.contracts.web.editors;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.BeanValidator;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
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
import sk.stu.fei.uim.bp.application.backend.client.web.events.phycicalPersonEvents.PhysicalPersonDeleteEvent;
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
import sk.stu.fei.uim.bp.application.backend.contracts.domain.VehicleInsurance;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.InsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.LifeInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.VehicleInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.lifeInsuranceEvents.LifeInsuranceCancelEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.lifeInsuranceEvents.LifeInsuranceSaveEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.lifeInsuranceEvents.LifeInsuranceUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.vehicleInsuranceEvents.VehicleInsuranceCancelEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.vehicleInsuranceEvents.VehicleInsuranceDeleteEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.vehicleInsuranceEvents.VehicleInsuranceSaveEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.vehicleInsuranceEvents.VehicleInsuranceUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.file.components.FileAttachmentsAddView;
import sk.stu.fei.uim.bp.application.backend.file.components.FileAttachmentsViewer;
import sk.stu.fei.uim.bp.application.backend.file.utils.FileAttachment;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;
import sk.stu.fei.uim.bp.application.validarors.messages.ContractValidatorsMessages;

import java.math.BigDecimal;
import java.util.List;


@Tag("vehicle-insurance-editor")
@JsModule("./views/contracts/editors/vehicle-insurance-editor.js")
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class VehicleInsuranceEditor extends PolymerTemplate<VehicleInsuranceEditor.VehicleInsuranceEditorModel> {

    @Id("contractNumber")
    private TextField contractNumber;

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

    @Id("numberOfVehicle")
    private TextField numberOfVehicle;

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

    @Id("product")
    private Select<ProductDto> product;

    @Id("dateOfStart")
    private DatePicker dateOfStart;


    private final ObjectId currentAgent = new ObjectId("601b6300dbf3207494372a20");

    private final SearchClientView searchOwnerView;
    private final SearchClientView searchInsuredView;

    private final ClientService clientService;
    private final ClientConverter clientConverter;

    private final ProductService productService;
    private final ProductConverter productConverter;

    private final ConfirmDialog confirmDialog;
    private final ConfirmDialog deleteConfirmDialog;

    private Dialog searchWindow;

    private boolean isNew;
    private VehicleInsuranceDto vehicleInsuranceDto;
    private final BeanValidationBinder<VehicleInsuranceDto> binder = new BeanValidationBinder<>(VehicleInsuranceDto.class);
    @Id("delete")
    private Button delete;


    @Autowired
    public VehicleInsuranceEditor(SearchClientView searchOwnerView,
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

        this.deleteConfirmDialog = new ConfirmDialog("Naozaj si prajete vymazať toto poistenie?","Údaje tohto poistenia budú natrvalo odstránené !!!","Vymazať",confirmEvent -> delete(),"Zrušiť",cancelEvent -> closeDeleteConfirmDialog());
        this.deleteConfirmDialog.setConfirmButtonTheme("error primary");

        this.addOwner.addClickListener(event -> showSearchWindow(this.searchOwnerView));
        this.addInsured.addClickListener(event -> showSearchWindow(this.searchInsuredView));
        this.equalsClients.addClickListener(event -> setEqualsClient());

        this.save.addClickListener(event -> validateAndSave());
        this.cancel.addClickListener(event -> this.confirmDialog.open());
        this.delete.addClickListener(event -> this.deleteConfirmDialog.open());

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

        List<Product> productList = this.productService.getAllProductByProductTypeCategory(this.currentAgent, ProductTypeCategory.NON_LIFE_CAR_INSURANCE);
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
                .withValidator(new BeanValidator(VehicleInsuranceDto.class,"contractNumber"))
                .bind(VehicleInsuranceDto::getContractNumber,VehicleInsuranceDto::setContractNumber);

        this.binder.forField(product)
                .withValidator(new BeanValidator(VehicleInsuranceDto.class,"product"))
                .withValidator(product -> product.getProductType().getCategory() == ProductTypeCategory.NON_LIFE_CAR_INSURANCE, ContractValidatorsMessages.CONTRACT_PRODUCT_VEHICLE_INSURANCE_MEESAGE)
                .bind(VehicleInsuranceDto::getProduct,VehicleInsuranceDto::setProduct);

        this.binder.forField(dateOfStart)
                .withValidator(new BeanValidator(VehicleInsuranceDto.class,"dateOfStart"))
                .withValidator(date -> date != null, ContractValidatorsMessages.CONTRACT_DOCUMENT_DATE_OF_START_NOT_NULL_MESSAGE)
                .withValidator(date -> anniversaryDate.getValue() == null ? true : date.isBefore(this.anniversaryDate.getValue()),ContractValidatorsMessages.CONTRACT_DOCUMENT_DATE_OF_START_BEFORE_ANNIVERSARY_DATE_MESSAGE)
                .bind(VehicleInsuranceDto::getDateOfStart,VehicleInsuranceDto::setDateOfStart);

        this.binder.forField(payment)
                .withValidator(new BeanValidator(VehicleInsuranceDto.class,"payment"))
                .bind(vehicleInsuranceDto1 -> new BigDecimal(String.valueOf(vehicleInsuranceDto1.getPayment()))
                        ,(o, s) -> o.setPayment(s.doubleValue()));

        this.binder.forField(anniversaryDate)
                .withValidator(new BeanValidator(VehicleInsuranceDto.class,"anniversaryDate"))
                .withValidator(date -> date != null, ContractValidatorsMessages.INSURANCE_ANNIVERSARY_DATE_NOT_NULL_MESSAGE)
                .withValidator(date -> dateOfStart.getValue() == null ? true : date.isAfter(dateOfStart.getValue()),ContractValidatorsMessages.INSURANCE_ANNIVERSARY_DATE_AFTER_DATE_OF_START_MESSAGE)
                .bind(VehicleInsuranceDto::getAnniversaryDate,VehicleInsuranceDto::setAnniversaryDate);

        this.binder.forField(paymentFrequency)
                .withValidator(new BeanValidator(VehicleInsuranceDto.class,"paymentFrequency"))
                .bind(VehicleInsuranceDto::getPaymentFrequency,VehicleInsuranceDto::setPaymentFrequency);

        this.binder.forField(dateOfEnd)
                .bind(VehicleInsuranceDto::getDateOfEnd,VehicleInsuranceDto::setDateOfEnd);

        this.binder.forField(note)
                .bind(VehicleInsuranceDto::getNote,VehicleInsuranceDto::setNote);

        this.binder.forField(numberOfVehicle)
                .withValidator(new BeanValidator(VehicleInsuranceDto.class,"numberOfVehicle"))
                .bind(VehicleInsuranceDto::getNumberOfVehicle,VehicleInsuranceDto::setNumberOfVehicle);

    }

    public void setVehicleInsuranceDto(VehicleInsuranceDto vehicleInsuranceDto, boolean isNew, List<FileAttachment> attachmentList)
    {
        this.isNew = isNew;
        this.vehicleInsuranceDto = vehicleInsuranceDto;
        this.binder.readBean(vehicleInsuranceDto);

        this.delete.setEnabled(!this.isNew);

        if(isNew)
        {
            this.ownerInfo.setVisible(false);
            this.insuredInfo.setVisible(false);
            enableEqualsButton(true);
        }
        else
        {
            setInfoAboutOwner(vehicleInsuranceDto.getOwner());
            setInfoAboutInsured(vehicleInsuranceDto.getInsured());
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

        this.vehicleInsuranceDto.setOwner(clientDto);
        setInfoAboutOwner(this.vehicleInsuranceDto.getOwner());
        closeSearchWindow();
    }

    private void setInsured(SearchGetChoosedClientEvent event)
    {
        Client client = event.getClient();
        ClientDto clientDto = this.clientConverter.convertClientToClientDto(client);

        this.vehicleInsuranceDto.setInsured(clientDto);
        setInfoAboutInsured(this.vehicleInsuranceDto.getInsured());
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
        if(this.vehicleInsuranceDto.getOwner() != null)
        {
            this.vehicleInsuranceDto.setInsured(this.vehicleInsuranceDto.getOwner());
            setInfoAboutInsured(this.vehicleInsuranceDto.getOwner());
        }
        else if(this.vehicleInsuranceDto.getInsured() != null)
        {
            this.vehicleInsuranceDto.setOwner(this.vehicleInsuranceDto.getInsured());
            this.setInfoAboutOwner(this.vehicleInsuranceDto.getInsured());
        }
    }

    private void validateAndSave()
    {
        boolean correct = true;
        if(this.vehicleInsuranceDto.getOwner() == null)
        {
            correct = false;
            showErrorMessage(ContractValidatorsMessages.CONTRACT_OWNER_NOT_NULL_MESSAGE);
        }

        if(this.vehicleInsuranceDto.getInsured() == null)
        {
            correct = false;
            showErrorMessage(ContractValidatorsMessages.INSURANCE_INSURED_NOT_NULL_MESSAGE);
        }


        try
        {
            binder.writeBean(this.vehicleInsuranceDto);
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
            fireEvent(new VehicleInsuranceSaveEvent(this,this.vehicleInsuranceDto,this.attachmentsAdd.getAddFiles()));
        }
        else
            fireEvent(new VehicleInsuranceUpdateEvent(this,this.vehicleInsuranceDto,this.attachmentsAdd.getAddFiles()));
    }

    private void showErrorMessage(String errorText)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showErrorMessage(errorText);
    }


    public void clear()
    {
        this.vehicleInsuranceDto = null;
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
        fireEvent(new VehicleInsuranceCancelEvent(this,null,null));
    }

    private void delete()
    {
        if(!this.isNew)
            fireEvent(new VehicleInsuranceDeleteEvent(this,this.vehicleInsuranceDto,null));
    }

    private void closeConfirmDialog()
    {
        if(this.confirmDialog.isOpened())
        {
            this.confirmDialog.close();
        }
    }

    private void closeDeleteConfirmDialog()
    {
        if(this.deleteConfirmDialog.isOpened())
        {
            this.confirmDialog.close();
        }
    }


    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener)
    {
        return getEventBus().addListener(eventType,listener);
    }


    public interface VehicleInsuranceEditorModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
