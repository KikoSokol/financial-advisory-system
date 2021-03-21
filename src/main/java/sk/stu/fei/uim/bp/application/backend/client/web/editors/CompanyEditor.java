package sk.stu.fei.uim.bp.application.backend.client.web.editors;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.components.PhysicalPersonCard;
import sk.stu.fei.uim.bp.application.backend.client.web.components.SearchClientView;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientCompanyDto;
import sk.stu.fei.uim.bp.application.backend.client.web.events.clientCompanyEvents.ClientCompanySaveEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.searchClientEvent.SearchGetChoosedClientEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.table.TableClientItem;
import sk.stu.fei.uim.bp.application.validarors.messages.ClientValidatorsMessages;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * A Designer generated component for the company-editor template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("company-editor")
@JsModule("./views/client/company-editor.js")
public class CompanyEditor extends PolymerTemplate<CompanyEditor.CompanyEditorModel> {

    @Id("businessName")
    private TextField businessName;

    @Id("ico")
    private TextField ico;

    @Id("dic")
    private TextField dic;

    @Id("dicDph")
    private TextField dicDph;

    @Id("businessObject")
    private TextField businessObject;

    @Id("street")
    private TextField street;

    @Id("numberOfHouse")
    private TextField numberOfHouse;

    @Id("city")
    private TextField city;

    @Id("postalCode")
    private TextField postalCode;

    @Id("state")
    private TextField state;

    @Id("note")
    private TextArea note;

    @Id("addManager")
    private Button addManager;

    @Id("managerTable")
    private Grid<TableClientItem> managerTable;

//    @Id("searchedManagerTable")
//    private Grid searchedManagerTable;

    @Id("cancel")
    private Button cancel;

    @Id("save")
    private Button save;

    @Id("seachManager")
    private SearchClientView seachManager;

    private final ClientService clientService;

    private List<Client> managers = new LinkedList<>();



    private ClientCompanyDto clientCompanyDto;
    private final BeanValidationBinder<ClientCompanyDto> binder = new BeanValidationBinder<>(ClientCompanyDto.class);



    public CompanyEditor(ClientServiceImpl clientService) {
        this.clientService = clientService;


        binder.forField(businessName)
                .withValidator(name -> name.length() > 0, ClientValidatorsMessages.BUSINESS_NAME_MESSAGE_NOT_BLANK)
                .bind(ClientCompanyDto::getBusinessName,ClientCompanyDto::setBusinessName);

        binder.forField(ico)
                .withValidator(ic -> ic.length() > 0,ClientValidatorsMessages.ICO_MESSAGE_NOT_BLANK)
                .withValidator(ic -> ic.matches("[0-9]{8}"),ClientValidatorsMessages.ICO_MESSAGE_FORMAT)
                .bind(ClientCompanyDto::getIco,ClientCompanyDto::setIco);

        binder.forField(dic)
                .withValidator(dicText -> dicText.length() > 0, ClientValidatorsMessages.DIC_MESSAGE_NOT_BLANK)
                .withValidator(dicText -> dicText.matches("[0-9]{10}"),ClientValidatorsMessages.DIC_MESSAGE_FORMAT)
                .bind(ClientCompanyDto::getDic,ClientCompanyDto::setDic);

        binder.forField(dicDph)
                .withValidator(dicDphText -> dicDphText.length() > 0, ClientValidatorsMessages.DIC_DPH_MESSAGE_NOT_BLANK)
                .withValidator(dicDphText -> dicDphText.matches("^SK[0-9]{10}"),ClientValidatorsMessages.DIC_DPH_MESSAGE_FORMAT)
                .bind(ClientCompanyDto::getDicDph,ClientCompanyDto::setDicDph);

        binder.forField(businessObject)
                .withValidator(object -> object.length() > 0,ClientValidatorsMessages.BUSINESS_OBJECT_MESSAGE_NOT_BLANK)
                .bind(ClientCompanyDto::getBusinessObject,ClientCompanyDto::setBusinessObject);

        binder.forField(street)
                .withValidator(st -> st.length() > 0,ClientValidatorsMessages.STREET_MESSAGE_NOT_BLANK)
                .bind(ClientCompanyDto::getStreet,ClientCompanyDto::setStreet);

        binder.forField(numberOfHouse)
                .withValidator(number -> number.length() > 0, ClientValidatorsMessages.NUMBER_OF_HOUSE_MESSAGE_NOT_BLANK)
                .withValidator(number -> number.matches("[0-9]*[/]?[0-9]{1,}"),ClientValidatorsMessages.NUMBER_OF_HOUSE_MESSAGE_FORMAT)
                .bind(ClientCompanyDto::getNumberOfHouse,ClientCompanyDto::setNumberOfHouse);

        binder.forField(postalCode)
                .withValidator(code -> code.length() > 0, ClientValidatorsMessages.POSTAL_CODE_MESSAGE_NOT_BLANK)
                .withValidator(code -> code.matches("[0-9]{3}[\\s]?[0-9]{2}"),ClientValidatorsMessages.POSTAL_CODE_MESSAGE_FORMAT)
                .bind(ClientCompanyDto::getPostalCode,ClientCompanyDto::setPostalCode);

        binder.forField(city)
                .withValidator(c -> c.length() > 0, ClientValidatorsMessages.CITY_MESSAGE_NOT_BLANK)
                .withValidator(c -> c.matches("[^0-9]{1,}"),ClientValidatorsMessages.CITY_MESSAGE_FORMAT)
                .bind(ClientCompanyDto::getCity,ClientCompanyDto::setCity);

        binder.forField(state)
                .withValidator(s -> s.length() > 0, ClientValidatorsMessages.STATE_MESSAGE_NOT_BLANK)
                .withValidator(s -> s.matches("[^0-9]{1,}"),ClientValidatorsMessages.STATE_MESSAGE_FORMAT)
                .bind(ClientCompanyDto::getState,ClientCompanyDto::setState);

        binder.forField(note)
                .bind(ClientCompanyDto::getNote,ClientCompanyDto::setNote);

        initColumn();

        this.seachManager.setSearchPhysicalPerson();
        this.seachManager.setVisible(false);
        this.seachManager.addListener(SearchGetChoosedClientEvent.class,this::addManager);

        this.addManager.addClickListener(event -> this.seachManager.setVisible(true));

        this.save.addClickListener(event -> validateAndSave());



    }

    public void setClientCompanyDto(ClientCompanyDto clientCompanyDto)
    {
        this.clientCompanyDto = clientCompanyDto;
        this.binder.readBean(clientCompanyDto);
        this.setManagers(clientCompanyDto.getManagers());
    }

    private void setManagers(List<ObjectId> managersReferences)
    {
        for (ObjectId managersReference : managersReferences) {
            Optional<Client> client = this.clientService.getClientById(managersReference);
            this.managers.add(client.get());
        }
    }


    private void initColumn()
    {
        Grid.Column<TableClientItem> nameColumn = managerTable.addColumn(new ComponentRenderer<>(client ->
                new PhysicalPersonCard(client)));
        nameColumn.setHeader("Manažéry");
        nameColumn.setKey("managerColumn");
        nameColumn.setId("managerColumn");


    }

    private void addManager(SearchGetChoosedClientEvent event)
    {
        if(event.getClient() instanceof PhysicalPerson)
        {
            Client client = event.getClient();
            PhysicalPerson person = (PhysicalPerson) client;
            if(!this.managers.contains(person))
            {
                this.managers.add(person);
                updateManagerTable();
            }
            else
            {
                System.out.println("nepridalo rovnaku osobu");
            }

        }

    }


    private void updateManagerTable()
    {
        List<TableClientItem> list = new LinkedList<>();
        for (Client client : this.managers) {
            list.add(new TableClientItem(client));
        }

        this.managerTable.setItems(list);

    }

    private void validateAndSave()
    {
        boolean isAllCorrect = true;

        try {
            binder.writeBean(this.clientCompanyDto);
        } catch (ValidationException exception) {
            isAllCorrect = false;
        }

        if(this.managers.isEmpty())
        {
            isAllCorrect = false;
            //TODO: pridaj upozornenie že nebol zadaný žiaden manažer
        }
        else
            setManagersToClientCompanyDto();

        if(isAllCorrect)
            fireEvent(new ClientCompanySaveEvent(this,this.clientCompanyDto));
        else
        {
            //TODO:pridaj informáciu že sa nepodarilo pridať klienta do databázy
            System.out.println("Nepodarilo sa pridat klienta");
        }


    }


    private void setManagersToClientCompanyDto()
    {
        List<ObjectId> listOfManagers = new LinkedList<>();

        for (Client manager : this.managers) {
            listOfManagers.add(manager.getClientId());
        }
        this.clientCompanyDto.setManagers(listOfManagers);
    }


    public void clear()
    {
        this.managers.clear();
        this.clientCompanyDto = null;
        this.binder.setBean(null);
        this.binder.readBean(null);
        this.seachManager.clear();
    }



    public <T extends ComponentEvent<?>>Registration addListener(Class<T> eventType, ComponentEventListener<T> listener)
    {
        return getEventBus().addListener(eventType,listener);
    }




    /**
     * This model binds properties between CompanyEditor and company-editor
     */
    public interface CompanyEditorModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}