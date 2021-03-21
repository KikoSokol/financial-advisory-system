package sk.stu.fei.uim.bp.application.backend.client.web.events.searchClientEvent;

import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.web.SearchClientView;

public abstract class SearchClientEvent extends ComponentEvent<SearchClientView>
{
    private Client client;

    protected SearchClientEvent(SearchClientView source, Client client)
    {
        super(source,false);
        this.client = client;
    }

    public Client getClient()
    {
        return this.client;
    }
}
