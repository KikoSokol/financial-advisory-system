package sk.stu.fei.uim.bp.application.backend.client.web.components;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.ClientConverter;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.PhysicalPersonDto;
import sk.stu.fei.uim.bp.application.backend.client.web.events.searchClientEvent.SearchClientCancelEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.searchClientEvent.SearchGetChoosedClientEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.table.TableClientItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Tag("search-client-view")
@JsModule("./views/client/search-client-view.js")
public class SearchClientView extends PolymerTemplate<SearchClientView.SearchClientViewModel> {

    @Id("search")
    private TextField search;
    @Id("tableClient")
    private Grid<TableClientItem> tableClient;
    @Id("cancel")
    private Button cancel;
    @Id("add")
    private Button add;

    private String whatSearch;


    ClientService clientService;

    private final ClientConverter clientConverter;

    @Autowired
    public SearchClientView(ClientServiceImpl clientService, ClientConverter clientConverter) {
        this.clientService = clientService;
        this.clientConverter = clientConverter;

        initColumn();
        setSearch();

        cancel.addClickListener(event -> {
            this.search.setValue("");
            fireEvent(new SearchClientCancelEvent(this,null));
        });

        add.addClickListener(event -> {
            Optional<TableClientItem> selectedClientItem = this.tableClient.getSelectionModel().getFirstSelectedItem();

            if(selectedClientItem.isPresent())
            {
                Optional<Client> selectedClient = this.clientService.getClientById(selectedClientItem.get().getId());
                fireEvent(new SearchGetChoosedClientEvent(this,selectedClient.get()));
            }
        });

        this.tableClient.addItemDoubleClickListener(item -> {
            TableClientItem selectedClientItem = item.getItem();
            Optional<Client> selectedClient = this.clientService.getClientById(selectedClientItem.getId());
            fireEvent(new SearchGetChoosedClientEvent(this,selectedClient.get()));
        });
    }


    private void initColumn()
    {
        Grid.Column<TableClientItem> nameColumn = tableClient.addColumn(new ComponentRenderer<>(client ->
                new PhysicalPersonCard(client)));
        nameColumn.setHeader("Klient");
        nameColumn.setKey("clientColumn");
        nameColumn.setId("clientColumn");
    }

    private void setSearch()
    {
        search.addValueChangeListener(event-> {
            if(whatSearch.equals("PhysicalPerson"))
            {

                if(event.getValue().equals(""))
                {
                    setPhysicalPersonIntoTable(new LinkedList<>());
                }
                else {
                    setPhysicalPersonIntoTable(this.clientService.getPhysicalPersonByNameOrBySurnameOrByEmailOrByPersonalNumber(event.getValue()));

                }
            }
            else if(whatSearch.equals("AllClient"))
            {
                if(event.getValue().equals(""))
                {
                    setPhysicalPersonIntoTable(new LinkedList<>());
                }
                else
                {
                    setClientIntoTable(this.clientService.getClientByNameOrBySurnameOrByEmailOrByPersonalNumberOrByIcoOrByBusinessName(event.getValue()));
                }
            }
        });
        search.setValueChangeMode(ValueChangeMode.EAGER);
    }

    public void setSearchPhysicalPerson()
    {
        this.whatSearch = "PhysicalPerson";
    }

    public void setSearchAllClient()
    {
        this.whatSearch = "AllClient";
    }


    private void setPhysicalPersonIntoTable(List<PhysicalPerson> physicalPersons)
    {
        List<TableClientItem> list = new LinkedList<>();
        for (PhysicalPerson physicalPerson : physicalPersons) {
            list.add(new TableClientItem(new PhysicalPersonDto(physicalPerson)));
        }
        this.tableClient.setItems(list);
    }

    private void setClientIntoTable(List<Client> clients)
    {
        this.tableClient.setItems(this.clientConverter.convertListClientsToListOfTableClientItem(clients));
    }


    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener)
    {
        return getEventBus().addListener(eventType,listener);
    }

    public void clear()
    {
        this.search.setValue("");
        this.tableClient.setItems(new LinkedList<TableClientItem>());
    }



    public interface SearchClientViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
