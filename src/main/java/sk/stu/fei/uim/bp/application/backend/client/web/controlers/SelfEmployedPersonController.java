package sk.stu.fei.uim.bp.application.backend.client.web.controlers;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.client.domain.SelfEmployedPerson;
import sk.stu.fei.uim.bp.application.backend.client.service.SelfEmployedPersonService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.SelfEmployedPersonDto;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;

public class SelfEmployedPersonController
{
    private final SelfEmployedPersonService service;

    private boolean isNew;
    private SelfEmployedPerson selfEmployedPerson;
    private SelfEmployedPersonDto selfEmployedPersonDto;

    public SelfEmployedPersonController(ClientServiceImpl clientService)
    {
        this.service = clientService;
        this.clear();

    }

    public SelfEmployedPersonDto addNewSelfEmployedPerson(ObjectId currentAgent)
    {
        this.isNew = true;
        this.selfEmployedPerson = new SelfEmployedPerson();
        this.selfEmployedPerson.setAgent(currentAgent);
        this.selfEmployedPerson.setAddress(new Address());

        this.selfEmployedPersonDto = new SelfEmployedPersonDto();

        return this.selfEmployedPersonDto;
    }

    public SelfEmployedPersonDto updateSelfEmployedPerson(SelfEmployedPerson selfEmployedPerson)
    {
        this.isNew = false;
        this.selfEmployedPerson = selfEmployedPerson;
        this.selfEmployedPersonDto = new SelfEmployedPersonDto(this.selfEmployedPerson);

        return this.selfEmployedPersonDto;
    }


    public SelfEmployedPerson doSaveNewSelfEmployedPerson(SelfEmployedPersonDto selfEmployedPersonDto) throws Exception {
        this.selfEmployedPersonDto = selfEmployedPersonDto;

        FileWrapper front = this.selfEmployedPersonDto.getFrontSideOfPersonCard();
        FileWrapper back = this.selfEmployedPersonDto.getBackSideOfPersonCard();

        try {
            this.selfEmployedPerson = this.selfEmployedPersonDto.toSelfEmployedPerson(this.selfEmployedPerson);
            if(front != null && back !=null)
            {
                return service.addNewSelfEmployedPerson(this.selfEmployedPerson, front, back);
            }
            else if((front == null && back != null) || (front != null && back == null))
                throw new Exception("Nebola zadaná jedná strana kópie OP");

            return service.addNewSelfEmployedPerson(this.selfEmployedPerson);

        }catch (Exception exception)
        {
            System.out.println("Nepodarilo sa pridať novú Živnostníka (SelfEmployedPersonController)");
            throw exception;
        }
    }


    public void clear()
    {
        this.isNew = false;
        this.selfEmployedPerson = null;
        this.selfEmployedPersonDto = null;

    }
}
