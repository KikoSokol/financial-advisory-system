package sk.stu.fei.uim.bp.application.backend.contracts.web.controllers;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.Contract;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.LifeInsurance;
import sk.stu.fei.uim.bp.application.backend.contracts.service.implementation.ContractServiceImpl;
import sk.stu.fei.uim.bp.application.backend.contracts.web.ContractConverter;
import sk.stu.fei.uim.bp.application.backend.contracts.web.ContractMainView;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.LifeInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.LifeInsuranceEditor;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.lifeInsuranceEvents.LifeInsuranceCancelEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.lifeInsuranceEvents.LifeInsuranceSaveEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.lifeInsuranceEvents.LifeInsuranceUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.file.utils.FileAttachment;

import java.util.LinkedList;
import java.util.List;

public class LifeInsuranceController extends MainContractController
{
    private boolean isNew;
    private Contract contract;
    private LifeInsurance lifeInsurance;
    private LifeInsuranceDto lifeInsuranceDto;

    public LifeInsuranceController(ContractMainView contractMainView,
                                   ContractConverter contractConverter,
                                   ContractServiceImpl contractService,
                                   ObjectId currentAgentId)
    {
        super.init(contractMainView,contractConverter,contractService,currentAgentId);

        this.initActionOfEditor();
        this.clear();
    }


    private void initActionOfEditor()
    {
        LifeInsuranceEditor lifeInsuranceEditor = super.getContractMainView().getLifeInsuranceEditor();

        lifeInsuranceEditor.addListener(LifeInsuranceSaveEvent.class,this::doSaveNewLifeInsurance);
        lifeInsuranceEditor.addListener(LifeInsuranceUpdateEvent.class,this::doUpdateLifeInsurance);
        lifeInsuranceEditor.addListener(LifeInsuranceCancelEvent.class,this::cancelEdit);
    }

    private void openEditor(LifeInsuranceDto lifeInsuranceDto, boolean isNew, List<FileAttachment> attachmentList)
    {
        LifeInsuranceEditor lifeInsuranceEditor = super.getContractMainView().getLifeInsuranceEditor();

        lifeInsuranceEditor.setLifeInsuranceDto(lifeInsuranceDto,isNew,attachmentList);
        super.getContractMainView().showWindow(lifeInsuranceEditor);
    }


    public void addNewLifeInsurance()
    {
        this.isNew = true;

        this.contract = null;
        this.lifeInsurance = new LifeInsurance();
        this.lifeInsuranceDto = new LifeInsuranceDto(this.lifeInsurance,null,null,null);

        openEditor(this.lifeInsuranceDto,this.isNew,new LinkedList<FileAttachment>());
    }

    public void updateLifeInsurance(LifeInsurance lifeInsurance)
    {
        this.isNew = false;

        this.contract = super.getContractService().getContractByContractNumber(lifeInsurance.getContractNumber()).get();

        this.lifeInsurance = lifeInsurance;

        this.lifeInsuranceDto = super.getContractConverter().convertLifeInsuranceToLifeInsuranceDto(lifeInsurance);

        List<FileAttachment> fileAttachments = new LinkedList<>(super.getContractService().getFileAttachmentsOfContract(this.contract));

        openEditor(this.lifeInsuranceDto,this.isNew,fileAttachments);
    }


    private void doSaveNewLifeInsurance(LifeInsuranceSaveEvent event)
    {
        this.lifeInsuranceDto = event.getLifeInsuranceDto();

        try
        {
            this.lifeInsurance = this.lifeInsuranceDto.toLifeInsurance(this.lifeInsurance);

            if(event.getAttachments().isEmpty())
            {
                super.getContractService().addNewContractDocument(this.lifeInsurance,super.getCurrentAgentId());
            }
            else
            {
                super.getContractService().addNewContractDocument(this.lifeInsurance,event.getAttachments(),super.getCurrentAgentId());
            }
            successOperation("Nové životné poistenie bolo úspešne pridané");
        }
        catch (Exception exception)
        {
            super.getContractMainView().showErrorMessage("Nové životné poistenie sa nepodarilo pridať. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }


    private void doUpdateLifeInsurance(LifeInsuranceUpdateEvent event)
    {
        this.lifeInsuranceDto = event.getLifeInsuranceDto();

        try
        {
            this.lifeInsurance = this.lifeInsuranceDto.toLifeInsurance(this.lifeInsurance);

            if(event.getAttachments().isEmpty())
            {
                super.getContractService().updateContractDocument(this.contract,this.lifeInsurance);
            }
            else
            {
                super.getContractService().updateContractDocument(this.contract,this.lifeInsurance,event.getAttachments());
            }
            successOperation("Údaje životného poistenia boli úspešne zmenené");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            super.getContractMainView().showErrorMessage("Nepodarilo sa zmeniť údaje. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }

    }


    private void cancelEdit(LifeInsuranceCancelEvent event)
    {
        this.clear();
    }

    private void successOperation(String successText)
    {
        this.clear();
        super.getContractMainView().refreshTable();
        super.getContractMainView().showSuccessOperationNotification(successText);
    }


    public void clear()
    {
        super.getContractMainView().getLifeInsuranceEditor().clear();
        super.getContractMainView().closeWindow();
        this.isNew = false;
        this.contract = null;
        this.lifeInsurance = null;
        this.lifeInsuranceDto = null;
    }



}
