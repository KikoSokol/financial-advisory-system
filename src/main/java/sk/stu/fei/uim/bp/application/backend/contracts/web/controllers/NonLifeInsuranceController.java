package sk.stu.fei.uim.bp.application.backend.contracts.web.controllers;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.Contract;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.NonLifeInsurance;
import sk.stu.fei.uim.bp.application.backend.contracts.service.implementation.ContractServiceImpl;
import sk.stu.fei.uim.bp.application.backend.contracts.web.ContractConverter;
import sk.stu.fei.uim.bp.application.backend.contracts.web.ContractMainView;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.NonLifeInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.NonLifeInsuranceEditor;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.nonLifeInsuracneEvents.NonLifeInsuranceCancelEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.nonLifeInsuracneEvents.NonLifeInsuranceDeleteEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.nonLifeInsuracneEvents.NonLifeInsuranceSaveEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.nonLifeInsuracneEvents.NonLifeInsuranceUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.file.utils.FileAttachment;

import java.util.LinkedList;
import java.util.List;

public class NonLifeInsuranceController extends MainContractController
{
    private boolean isNew;
    private Contract contract;
    private NonLifeInsurance nonLifeInsurance;
    private NonLifeInsuranceDto nonLifeInsuranceDto;

    public NonLifeInsuranceController(ContractMainView contractMainView,
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
        NonLifeInsuranceEditor nonLifeInsuranceEditor = super.getContractMainView().getNonLifeInsuranceEditor();

        nonLifeInsuranceEditor.addListener(NonLifeInsuranceSaveEvent.class,this::doSaveNewNonLifeInsurance);
        nonLifeInsuranceEditor.addListener(NonLifeInsuranceUpdateEvent.class,this::doUpdateNonLifeInsurance);
        nonLifeInsuranceEditor.addListener(NonLifeInsuranceCancelEvent.class,this::cancelEdit);
        nonLifeInsuranceEditor.addListener(NonLifeInsuranceDeleteEvent.class,this::doDeleteNonLifeInsurance);
    }

    private void openEditor(NonLifeInsuranceDto nonLifeInsuranceDto, boolean isNew, List<FileAttachment> attachmentList)
    {
        NonLifeInsuranceEditor nonLifeInsuranceEditor = super.getContractMainView().getNonLifeInsuranceEditor();

        nonLifeInsuranceEditor.setNonLifeInsuranceDto(nonLifeInsuranceDto,isNew,attachmentList);

        super.getContractMainView().showWindow(nonLifeInsuranceEditor);
    }


    public void addNewNonLifeInsurance()
    {
        this.isNew = true;

        this.contract = null;
        this.nonLifeInsurance = new NonLifeInsurance();
        this.nonLifeInsuranceDto = new NonLifeInsuranceDto(this.nonLifeInsurance,null,null,null);

        openEditor(this.nonLifeInsuranceDto,this.isNew,new LinkedList<FileAttachment>());
    }

    public void updateNonLifeInsurance(NonLifeInsurance nonLifeInsurance)
    {
        this.isNew = false;

        this.contract = super.getContractService().getContractByContractNumber(nonLifeInsurance.getContractNumber()).get();

        this.nonLifeInsurance = nonLifeInsurance;

        this.nonLifeInsuranceDto = super.getContractConverter().convertNonLifeInsuranceToLifeInsuranceDto(this.nonLifeInsurance);

        List<FileAttachment> fileAttachments = new LinkedList<>(super.getContractService().getFileAttachmentsOfContract(this.contract));

        openEditor(this.nonLifeInsuranceDto,this.isNew,fileAttachments);
    }

    private void doSaveNewNonLifeInsurance(NonLifeInsuranceSaveEvent event)
    {
        this.nonLifeInsuranceDto = event.getNonLifeInsuranceDto();

        try
        {
            this.nonLifeInsurance = this.nonLifeInsuranceDto.toNonLifeInsurance(this.nonLifeInsurance);

            if(event.getAttachments().isEmpty())
            {
                super.getContractService().addNewContractDocument(this.nonLifeInsurance,super.getCurrentAgentId());
            }
            else
            {
                super.getContractService().addNewContractDocument(this.nonLifeInsurance,event.getAttachments(),super.getCurrentAgentId());
            }
            successOperation("Nové neživotné poistenie bolo úspešne pridané");
        }
        catch (Exception exception)
        {
            super.getContractMainView().showErrorMessage("Nové neživotné poistenie sa nepodarilo pridať. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }


    private void doUpdateNonLifeInsurance(NonLifeInsuranceUpdateEvent event)
    {
        this.nonLifeInsuranceDto = event.getNonLifeInsuranceDto();

        try
        {
            this.nonLifeInsurance = this.nonLifeInsuranceDto.toNonLifeInsurance(this.nonLifeInsurance);

            if(event.getAttachments().isEmpty())
            {
                super.getContractService().updateContractDocument(this.contract,this.nonLifeInsurance);
            }
            else
            {
                super.getContractService().updateContractDocument(this.contract,this.nonLifeInsurance,event.getAttachments());
            }
            successOperation("Údaje neživotného poistenia boli úspešne zmenené");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            super.getContractMainView().showErrorMessage("Nepodarilo sa zmeniť údaje. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }

    }

    private void doDeleteNonLifeInsurance(NonLifeInsuranceDeleteEvent event)
    {
        boolean correctDeleted = super.getContractService().deleteContract(this.contract);

        if(correctDeleted)
        {
            successOperation("Údaje boli vymazané");
        }
        else
            super.getContractMainView().showErrorMessage("Údaje nebolo možné vymazať");
    }

    private void cancelEdit(NonLifeInsuranceCancelEvent event)
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
        super.getContractMainView().getNonLifeInsuranceEditor().clear();
        super.getContractMainView().closeWindow();
        this.isNew = false;
        this.contract = null;
        this.nonLifeInsurance = null;
        this.nonLifeInsuranceDto = null;
    }



}
