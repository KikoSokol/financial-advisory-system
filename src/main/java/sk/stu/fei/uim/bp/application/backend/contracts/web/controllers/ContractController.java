package sk.stu.fei.uim.bp.application.backend.contracts.web.controllers;

import com.vaadin.flow.data.provider.ListDataProvider;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.ContractDocument;
import sk.stu.fei.uim.bp.application.backend.contracts.service.implementation.ContractServiceImpl;
import sk.stu.fei.uim.bp.application.backend.contracts.web.ContractConverter;
import sk.stu.fei.uim.bp.application.backend.contracts.web.ContractMainView;
import sk.stu.fei.uim.bp.application.backend.contracts.web.filters.ContractFilter;
import sk.stu.fei.uim.bp.application.backend.contracts.web.table.TableContractItem;
import java.util.List;
import java.util.Optional;

public class ContractController extends MainContractController
{

    private final List<TableContractItem> allContracts;
    private final ListDataProvider<TableContractItem> contractDataProvider;
    private final ContractFilter contractFilter;


    public ContractController(ContractMainView contractMainView, ObjectId currentAgentId, ContractServiceImpl contractService, ContractConverter contractConverter)
    {
        super.init(contractMainView, contractConverter, contractService ,currentAgentId);

        this.contractFilter = new ContractFilter();

        this.allContracts = loadDataToTable();
        this.contractDataProvider = new ListDataProvider<>(this.allContracts);

        super.getContractMainView().getContractTable().setDataProvider(this.contractDataProvider);

        addFilterToProductDataProvider();
    }


    private List<TableContractItem> loadDataToTable()
    {
        List<ContractDocument> allContract = super.getContractService().getAllCurrentVersionContractDocumentOfAgent(super.getCurrentAgentId());

        return super.getContractConverter().convertListOfContractDocumentToListOfTableContractItem(allContract);
    }

    public void refreshTableData()
    {
        this.allContracts.clear();
        this.allContracts.addAll(loadDataToTable());
        this.contractDataProvider.refreshAll();
    }

    private void addFilterToProductDataProvider()
    {
        this.contractDataProvider.setFilter(tableContractItem -> this.contractFilter.test(tableContractItem));
    }

    public Optional<ContractDocument> getContractDocumentById(ObjectId contractDocumentId)
    {
        return super.getContractService().getCurrentVersionContractDocumentById(contractDocumentId);
    }

    public ContractFilter getContractFilter()
    {
        return this.contractFilter;
    }

    public void filterDataInTable()
    {
        this.contractDataProvider.refreshAll();
    }



}
