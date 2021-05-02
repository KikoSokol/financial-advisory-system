package sk.stu.fei.uim.bp.application.backend.client.web.events.searchClientEvent;

import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.web.components.SearchClientView;

public class SearchClientCancelEvent extends SearchClientEvent
{
    public SearchClientCancelEvent(SearchClientView source, Client client) {
        super(source, client);
    }
}
