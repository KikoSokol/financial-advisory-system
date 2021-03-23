package sk.stu.fei.uim.bp.application.backend.client.web.controlers;

import org.bson.types.ObjectId;

import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;
import sk.stu.fei.uim.bp.application.backend.client.service.PhysicalPersonService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.ClientMainView;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.PhysicalPersonDto;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.PhysicalPersonEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.events.phycicalPersonEvents.PhysicalPersonCancelEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.phycicalPersonEvents.PhysicalPersonSaveEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.phycicalPersonEvents.PhysicalPersonUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;


public class PhysicalPersonController extends MainClientController
{
    private final PhysicalPersonService service;

    private boolean isNew;
    private PhysicalPerson physicalPerson;
    private PhysicalPersonDto physicalPersonDto;

    public PhysicalPersonController(ClientMainView clientMainView, ObjectId currentAgentId, ClientServiceImpl clientService)
    {
        super(clientMainView,currentAgentId);
        this.service = clientService;
        initActionOfEditor();
        this.clear();
    }

    private void initActionOfEditor()
    {
        PhysicalPersonEditor physicalPersonEditor = this.clientMainView.getPhysicalPersonEditor();
        physicalPersonEditor.addListener(PhysicalPersonSaveEvent.class,this::doSaveNewPhysicalPerson);
        physicalPersonEditor.addListener(PhysicalPersonUpdateEvent.class,this::doUpdatePhysicalPerson);
        physicalPersonEditor.addListener(PhysicalPersonCancelEvent.class,this::cancelEdit);
    }

    private void openEditor(PhysicalPersonDto physicalPersonDto, boolean isNew)
    {
        PhysicalPersonEditor editor = super.clientMainView.getPhysicalPersonEditor();
        editor.setPhysicalPersonDto(physicalPersonDto,isNew);
        super.clientMainView.showMainWindow(editor);
    }

    public void addNewPhysicalPerson()
    {
        this.isNew = true;

        this.physicalPerson = new PhysicalPerson();
        this.physicalPerson.setAgent(super.currentAgentId);
        this.physicalPerson.setAddress(new Address());
        this.physicalPersonDto = new PhysicalPersonDto();

        openEditor(this.physicalPersonDto, this.isNew);
    }



    public void updatePhysicalPerson(PhysicalPerson physicalPerson)
    {
        this.isNew = false;
        this.physicalPerson = physicalPerson;
        this.physicalPersonDto = new PhysicalPersonDto(this.physicalPerson);

        openEditor(this.physicalPersonDto,this.isNew);
    }


    private void doSaveNewPhysicalPerson(PhysicalPersonSaveEvent event)
    {
        this.physicalPersonDto = event.getPhysicalPersonDto();

        FileWrapper front = this.physicalPersonDto.getFrontSideOfPersonCard();
        FileWrapper back = this.physicalPersonDto.getBackSideOfPersonCard();

        try
        {
            this.physicalPerson = this.physicalPersonDto.toPhysicalPerson(this.physicalPerson);
            if(front != null && back != null)
            {
                service.addNewPhysicalPerson(this.physicalPerson,front,back);
            }
            else if((front == null && back != null) || (front != null && back == null))
            {
                throw new Exception("Nebola zadaná jedná strana kópie OP");
            }
            else
                service.addNewPhysicalPerson(this.physicalPerson);

            successOperation("Nový klient bol úspešne pridaný");

        }
        catch (Exception exception)
        {
            System.out.println("Nepodarilo sa pridať novú FO (PhysicalPersonController)");
        }
    }



    private void doUpdatePhysicalPerson(PhysicalPersonUpdateEvent event)
    {
        this.physicalPersonDto = event.getPhysicalPersonDto();

        FileWrapper front = this.physicalPersonDto.getFrontSideOfPersonCard();
        FileWrapper back = this.physicalPersonDto.getBackSideOfPersonCard();

        try
        {
            this.physicalPerson = this.physicalPersonDto.toPhysicalPerson(this.physicalPerson);
            if(front != null && back != null)
            {
                service.updatePhysicalPerson(this.physicalPerson,front,back);
            }
            else if((front == null && back != null) || (front != null && back == null))
            {
                throw new Exception("Nebola zadaná jedná strana kópie OP");
            }
            else
                service.updatePhysicalPerson(this.physicalPerson);

            successOperation("Údaje klienta boli úspešne zmenené");

        }
        catch (Exception exception)
        {
            System.out.println("Nepodarilo sa pridať novú FO (PhysicalPersonController)");
        }

    }

    private void cancelEdit(PhysicalPersonCancelEvent event)
    {
        this.clear();
    }






    public void clear()
    {
        super.clientMainView.getPhysicalPersonEditor().clear();
        super.clientMainView.closeMainWindow();
        this.isNew = false;
        this.physicalPersonDto = null;
        this.physicalPerson = null;
    }


    private void successOperation(String successText)
    {
        this.clear();
        super.clientMainView.refreshTable();
        super.clientMainView.showSuccessOperationClientNotification(successText);
    }

}
