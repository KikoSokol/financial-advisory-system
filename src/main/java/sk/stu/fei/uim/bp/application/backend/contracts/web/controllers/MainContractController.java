package sk.stu.fei.uim.bp.application.backend.contracts.web.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stu.fei.uim.bp.application.backend.contracts.service.ContractService;
import sk.stu.fei.uim.bp.application.backend.contracts.service.implementation.ContractServiceImpl;
import sk.stu.fei.uim.bp.application.backend.contracts.web.ContractConverter;
import sk.stu.fei.uim.bp.application.backend.contracts.web.ContractMainView;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainContractController
{
    private ContractMainView contractMainView;
    private ObjectId currentAgentId;

    private ContractService contractService;
    private ContractConverter contractConverter;

    @Autowired
    public MainContractController()
    {

    }

    public void init(ContractMainView contractMainView,
                     ContractConverter contractConverter,
                     ContractServiceImpl contractService,
                     ObjectId currentAgentId)
    {
        this.contractMainView = contractMainView;
        this.currentAgentId = currentAgentId;
        this.contractConverter = contractConverter;
        this.contractService = contractService;
    }


    public ContractMainView getContractMainView() {
        return contractMainView;
    }

    public void setContractMainView(ContractMainView contractMainView) {
        this.contractMainView = contractMainView;
    }

    public ObjectId getCurrentAgentId() {
        return currentAgentId;
    }

    public void setCurrentAgentId(ObjectId currentAgentId) {
        this.currentAgentId = currentAgentId;
    }

    public ContractConverter getContractConverter() {
        return contractConverter;
    }

    public void setContractConverter(ContractConverter contractConverter) {
        this.contractConverter = contractConverter;
    }

    public ContractService getContractService() {
        return contractService;
    }

    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }
}
