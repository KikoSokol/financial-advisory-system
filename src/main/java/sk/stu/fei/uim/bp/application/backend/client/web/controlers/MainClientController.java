package sk.stu.fei.uim.bp.application.backend.client.web.controlers;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.client.web.ClientMainView;

public abstract class MainClientController
{
    protected final ClientMainView clientMainView;
    protected final ObjectId currentAgentId;// = new ObjectId("601b6300dbf3207494372a20");

    public MainClientController(ClientMainView clientMainView, ObjectId currentAgentId)
    {
        this.clientMainView = clientMainView;
        this.currentAgentId = currentAgentId;
    }


}
