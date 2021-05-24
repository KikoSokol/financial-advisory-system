package sk.stu.fei.uim.bp.application.backend.contracts.web.controllers;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.Contract;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.VehicleInsurance;
import sk.stu.fei.uim.bp.application.backend.contracts.service.implementation.ContractServiceImpl;
import sk.stu.fei.uim.bp.application.backend.contracts.web.ContractConverter;
import sk.stu.fei.uim.bp.application.backend.contracts.web.ContractMainView;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.VehicleInsuranceDto;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.VehicleInsuranceEditor;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.vehicleInsuranceEvents.VehicleInsuranceCancelEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.vehicleInsuranceEvents.VehicleInsuranceDeleteEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.vehicleInsuranceEvents.VehicleInsuranceSaveEvent;
import sk.stu.fei.uim.bp.application.backend.contracts.web.events.vehicleInsuranceEvents.VehicleInsuranceUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.file.utils.FileAttachment;
import java.util.LinkedList;
import java.util.List;

public class VehicleInsuranceController extends MainContractController
{
    private boolean isNew;
    private Contract contract;
    private VehicleInsurance vehicleInsurance;
    private VehicleInsuranceDto vehicleInsuranceDto;

    public VehicleInsuranceController(ContractMainView contractMainView,
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
        VehicleInsuranceEditor vehicleInsuranceEditor = super.getContractMainView().getVehicleInsuranceEditor();

        vehicleInsuranceEditor.addListener(VehicleInsuranceSaveEvent.class,this::doSaveNewVehicleInsurance);
        vehicleInsuranceEditor.addListener(VehicleInsuranceUpdateEvent.class,this::doUpdateVehicleInsurance);
        vehicleInsuranceEditor.addListener(VehicleInsuranceCancelEvent.class,this::cancelEdit);
        vehicleInsuranceEditor.addListener(VehicleInsuranceDeleteEvent.class,this::doDeleteVehicleInsurance);
    }

    private void openEditor(VehicleInsuranceDto vehicleInsuranceDto, boolean isNew, List<FileAttachment> attachmentList)
    {
        VehicleInsuranceEditor vehicleInsuranceEditor = super.getContractMainView().getVehicleInsuranceEditor();

        vehicleInsuranceEditor.setVehicleInsuranceDto(vehicleInsuranceDto,isNew,attachmentList);
        super.getContractMainView().showWindow(vehicleInsuranceEditor);
    }


    public void addNewVehicleInsurance()
    {
        this.isNew = true;

        this.contract = null;
        this.vehicleInsurance = new VehicleInsurance();
        this.vehicleInsuranceDto = new VehicleInsuranceDto(this.vehicleInsurance,null,null,null);

        openEditor(this.vehicleInsuranceDto,this.isNew,new LinkedList<FileAttachment>());
    }

    public void updateVehicleInsurance(VehicleInsurance vehicleInsurance)
    {
        this.isNew = false;

        this.contract = super.getContractService().getContractByContractNumber(vehicleInsurance.getContractNumber()).get();

        this.vehicleInsurance = vehicleInsurance;

        this.vehicleInsuranceDto = super.getContractConverter().convertVehicleInsuranceToVehicleInsuranceDto(vehicleInsurance);

        List<FileAttachment> fileAttachments = new LinkedList<>(super.getContractService().getFileAttachmentsOfContract(this.contract));

        openEditor(this.vehicleInsuranceDto,this.isNew,fileAttachments);
    }

    private void doSaveNewVehicleInsurance(VehicleInsuranceSaveEvent event)
    {
        this.vehicleInsuranceDto = event.getVehicleInsuranceDto();

        try
        {
            this.vehicleInsurance = this.vehicleInsuranceDto.toVehicleInsurance(this.vehicleInsurance);

            if(event.getAttachments().isEmpty())
            {
                super.getContractService().addNewContractDocument(this.vehicleInsurance,super.getCurrentAgentId());
            }
            else
            {
                super.getContractService().addNewContractDocument(this.vehicleInsurance,event.getAttachments(),super.getCurrentAgentId());
            }
            successOperation("Nové poistenie auta bolo úspešne pridané");
        }
        catch (Exception exception)
        {
            super.getContractMainView().showErrorMessage("Nové poistenie auta sa nepodarilo pridať. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }


    private void doUpdateVehicleInsurance(VehicleInsuranceUpdateEvent event)
    {
        this.vehicleInsuranceDto = event.getVehicleInsuranceDto();

        try
        {
            this.vehicleInsurance = this.vehicleInsuranceDto.toVehicleInsurance(this.vehicleInsurance);

            if(event.getAttachments().isEmpty())
            {
                super.getContractService().updateContractDocument(this.contract,this.vehicleInsurance);
            }
            else
            {
                super.getContractService().updateContractDocument(this.contract,this.vehicleInsurance,event.getAttachments());
            }
            successOperation("Údaje poistenia auta boli úspešne zmenené");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            super.getContractMainView().showErrorMessage("Nepodarilo sa zmeniť údaje. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }

    }

    private void doDeleteVehicleInsurance(VehicleInsuranceDeleteEvent event)
    {
        boolean correctDeleted = super.getContractService().deleteContract(this.contract);

        if(correctDeleted)
        {
            successOperation("Údaje boli vymazané");
        }
        else
            super.getContractMainView().showErrorMessage("Údaje nebolo možné vymazať");
    }

    private void cancelEdit(VehicleInsuranceCancelEvent event)
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
        super.getContractMainView().getVehicleInsuranceEditor().clear();
        super.getContractMainView().closeWindow();
        this.isNew = false;
        this.contract = null;
        this.vehicleInsurance = null;
        this.vehicleInsuranceDto = null;
    }




}
