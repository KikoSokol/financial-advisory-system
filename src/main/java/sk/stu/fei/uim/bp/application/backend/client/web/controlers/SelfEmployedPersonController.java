package sk.stu.fei.uim.bp.application.backend.client.web.controlers;


import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.client.domain.SelfEmployedPerson;
import sk.stu.fei.uim.bp.application.backend.client.service.SelfEmployedPersonService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.ClientMainView;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.SelfEmployedPersonDto;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.SelfEmployedPersonEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.events.selfEmployedPersonEvents.SelfEmployedPersonCancelEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.selfEmployedPersonEvents.SelfEmployedPersonSaveEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.selfEmployedPersonEvents.SelfEmployedPersonUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;

public class SelfEmployedPersonController extends MainClientController
{
    private final SelfEmployedPersonService service;

    private boolean isNew;
    private SelfEmployedPerson selfEmployedPerson;
    private SelfEmployedPersonDto selfEmployedPersonDto;

    public SelfEmployedPersonController(ClientMainView clientMainView, ObjectId currentAgentId, ClientServiceImpl clientService)
    {
        super(clientMainView,currentAgentId);
        this.service = clientService;
        initActionOfEditor();
        this.clear();

    }

    private void initActionOfEditor()
    {
        SelfEmployedPersonEditor selfEmployedPersonEditor = this.clientMainView.getSelfEmployedPersonEditor();
        selfEmployedPersonEditor.addListener(SelfEmployedPersonSaveEvent.class,this::doSaveNewSelfEmployedPerson);
        selfEmployedPersonEditor.addListener(SelfEmployedPersonUpdateEvent.class,this::doUpdateSelfEmployedPerson);
        selfEmployedPersonEditor.addListener(SelfEmployedPersonCancelEvent.class,this::cancelEdit);
    }

    private void openEditor(SelfEmployedPersonDto selfEmployedPersonDto, boolean isNew)
    {
        SelfEmployedPersonEditor editor = super.clientMainView.getSelfEmployedPersonEditor();
        editor.setSelfEmployedPersonDto(selfEmployedPersonDto,isNew);
        this.clientMainView.showMainWindow(editor);
    }

    public void addNewSelfEmployedPerson()
    {
        this.isNew = true;

        this.selfEmployedPerson = new SelfEmployedPerson();
        this.selfEmployedPerson.setAgent(super.currentAgentId);
        this.selfEmployedPerson.setAddress(new Address());
        this.selfEmployedPersonDto = new SelfEmployedPersonDto();

        openEditor(this.selfEmployedPersonDto,this.isNew);
    }


    public void updateSelfEmployedPerson(SelfEmployedPerson selfEmployedPerson)
    {
        this.isNew = false;
        this.selfEmployedPerson = selfEmployedPerson;
        this.selfEmployedPersonDto = new SelfEmployedPersonDto(this.selfEmployedPerson);

        openEditor(this.selfEmployedPersonDto,this.isNew);
    }


    private void doSaveNewSelfEmployedPerson(SelfEmployedPersonSaveEvent event)
    {
        this.selfEmployedPersonDto = event.getSelfEmployedPersonDto();

        FileWrapper front = this.selfEmployedPersonDto.getFrontSideOfPersonCard();
        FileWrapper back = this.selfEmployedPersonDto.getBackSideOfPersonCard();

        try {

            this.selfEmployedPerson = this.selfEmployedPersonDto.toSelfEmployedPerson(this.selfEmployedPerson);

            if(front != null && back !=null)
            {
                service.addNewSelfEmployedPerson(this.selfEmployedPerson, front, back);
            }
            else if((front == null && back != null) || (front != null && back == null))
            {
                throw new Exception("Nebola zadaná jedná strana kópie OP");
            }
            else
                service.addNewSelfEmployedPerson(this.selfEmployedPerson);

            this.clear();
            super.clientMainView.refreshTable();

        }catch (Exception exception)
        {
            System.out.println("Nepodarilo sa pridať novú Živnostníka (SelfEmployedPersonController)");
        }
    }



    private void doUpdateSelfEmployedPerson(SelfEmployedPersonUpdateEvent event)
    {
        this.selfEmployedPersonDto = event.getSelfEmployedPersonDto();

        FileWrapper front = this.selfEmployedPersonDto.getFrontSideOfPersonCard();
        FileWrapper back = this.selfEmployedPersonDto.getBackSideOfPersonCard();

        try {

            this.selfEmployedPerson = this.selfEmployedPersonDto.toSelfEmployedPerson(this.selfEmployedPerson);

            if(front != null && back !=null)
            {
                service.updateSelfEmplyedPerson(this.selfEmployedPerson, front, back);
            }
            else if((front == null && back != null) || (front != null && back == null))
            {
                throw new Exception("Nebola zadaná jedná strana kópie OP");
            }
            else
                service.updateSelfEmplyedPerson(this.selfEmployedPerson);

            this.clear();
            super.clientMainView.refreshTable();

        }catch (Exception exception)
        {
            System.out.println("Nepodarilo sa pridať novú Živnostníka (SelfEmployedPersonController)");
        }
    }

    private void cancelEdit(SelfEmployedPersonCancelEvent event)
    {
        this.clear();
    }


    public void clear()
    {
        super.clientMainView.getSelfEmployedPersonEditor().clear();
        super.clientMainView.closeMainWindow();
        this.isNew = false;
        this.selfEmployedPerson = null;
        this.selfEmployedPersonDto = null;

    }
}
