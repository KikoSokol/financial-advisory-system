package sk.stu.fei.uim.bp.application.backend.client.web.controlers;

import org.bson.types.ObjectId;

import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;
import sk.stu.fei.uim.bp.application.backend.client.service.PhysicalPersonService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.PhysicalPersonDto;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;


public class PhysicalPersonController
{
    private final PhysicalPersonService service;

    private boolean isNew;
    private PhysicalPerson physicalPerson;
    private PhysicalPersonDto physicalPersonDto;

    public PhysicalPersonController(ClientServiceImpl clientService)
    {
        this.service = clientService;
        this.clear();
    }

    public PhysicalPersonDto addNewPhysicalPerson(ObjectId currentAgent)
    {
        this.isNew = true;
        this.physicalPerson = new PhysicalPerson();
        this.physicalPerson.setAgent(currentAgent);
        this.physicalPerson.setAddress(new Address());
        this.physicalPersonDto = new PhysicalPersonDto();

        return this.physicalPersonDto;
    }

    public PhysicalPersonDto updatePhysicalPerson(PhysicalPerson physicalPerson)
    {
        this.isNew = false;
        this.physicalPerson = physicalPerson;
        this.physicalPersonDto = new PhysicalPersonDto(this.physicalPerson);

        return this.physicalPersonDto;
    }

    public PhysicalPerson doSaveNewPhysicalPerson(PhysicalPersonDto physicalPersonDto) throws Exception
    {
        this.physicalPersonDto = physicalPersonDto;

        FileWrapper front = this.physicalPersonDto.getFrontSideOfPersonCard();
        FileWrapper back = this.physicalPersonDto.getBackSideOfPersonCard();

        try
        {
            this.physicalPerson = this.physicalPersonDto.toPhysicalPerson(this.physicalPerson);
            if(front != null && back != null)
            {
                return service.addNewPhysicalPerson(this.physicalPerson,front,back);
            }
            else if((front == null && back != null) || (front != null && back == null))
                throw new Exception("Nebola zadaná jedná strana kópie OP");

            return service.addNewPhysicalPerson(this.physicalPerson);

        }
        catch (Exception exception)
        {
            System.out.println("Nepodarilo sa pridať novú FO (PhysicalPersonController)");
            throw exception;
        }
    }


    public void clear()
    {
        this.isNew = false;
        this.physicalPersonDto = null;
        this.physicalPerson = null;
    }

}
