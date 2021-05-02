package sk.stu.fei.uim.bp.application.backend.contracts.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.ClientConverter;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Product;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.implementation.ProductServiceImpl;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.ProductConverter;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.ContractDocument;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.LifeInsurance;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.NonLifeInsurance;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.VehicleInsurance;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.*;
import sk.stu.fei.uim.bp.application.backend.contracts.web.table.TableContractItem;

import java.util.LinkedList;
import java.util.List;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContractConverter
{

    private final ClientService clientService;
    private final ClientConverter clientConverter;
    private final ProductConverter productConverter;
    private final ProductService productService;

    @Autowired
    public ContractConverter(ClientServiceImpl clientService, ClientConverter clientConverter, ProductConverter productConverter, ProductServiceImpl productService)
    {
        this.clientService = clientService;
        this.clientConverter = clientConverter;
        this.productConverter = productConverter;
        this.productService = productService;
    }


    public List<TableContractItem> convertListOfContractDocumentToListOfTableContractItem(List<ContractDocument> contractDocuments)
    {
        List<TableContractItem> tableContractItems = new LinkedList<>();

        for(ContractDocument contractDocument : contractDocuments)
        {
            TableContractItem tableContractItem = new TableContractItem(this.convertContractDocumentToContractDocumentDto(contractDocument));
            tableContractItems.add(tableContractItem);
        }

        return tableContractItems;
    }

    public ContractDocumentDto convertContractDocumentToContractDocumentDto(ContractDocument contractDocument)
    {
        if(contractDocument instanceof LifeInsurance)
        {
            return this.convertLifeInsuranceToLifeInsuranceDto((LifeInsurance) contractDocument);
        }
        else if(contractDocument instanceof VehicleInsurance)
        {
            return this.convertVehicleInsuranceToVehicleInsuranceDto((VehicleInsurance) contractDocument);
        }
        else
        {
            return this.convertNonLifeInsuranceToLifeInsuranceDto((NonLifeInsurance) contractDocument);
        }


    }

    public LifeInsuranceDto convertLifeInsuranceToLifeInsuranceDto(LifeInsurance lifeInsurance)
    {
        ClientDto owner = this.clientConverter.convertClientToClientDto(this.clientService.getClientById(lifeInsurance.getOwner()).get());
        ClientDto insured = this.clientConverter.convertClientToClientDto(this.clientService.getClientById(lifeInsurance.getInsured()).get());
        ProductDto product = this.productConverter.convertProductToProductDto(this.productService.getProductById(lifeInsurance.getProduct()).get());

        return new LifeInsuranceDto(lifeInsurance,owner,insured,product);
    }

    public NonLifeInsuranceDto convertNonLifeInsuranceToLifeInsuranceDto(NonLifeInsurance nonLifeInsurance)
    {
        ClientDto owner = this.clientConverter.convertClientToClientDto(this.clientService.getClientById(nonLifeInsurance.getOwner()).get());
        ClientDto insured = this.clientConverter.convertClientToClientDto(this.clientService.getClientById(nonLifeInsurance.getInsured()).get());
        ProductDto product = this.productConverter.convertProductToProductDto(this.productService.getProductById(nonLifeInsurance.getProduct()).get());

        return new NonLifeInsuranceDto(nonLifeInsurance,owner,insured,product);
    }

    public VehicleInsuranceDto convertVehicleInsuranceToVehicleInsuranceDto(VehicleInsurance vehicleInsurance)
    {
        ClientDto owner = this.clientConverter.convertClientToClientDto(this.clientService.getClientById(vehicleInsurance.getOwner()).get());
        ClientDto insured = this.clientConverter.convertClientToClientDto(this.clientService.getClientById(vehicleInsurance.getInsured()).get());
        ProductDto product = this.productConverter.convertProductToProductDto(this.productService.getProductById(vehicleInsurance.getProduct()).get());

        return new VehicleInsuranceDto(vehicleInsurance,owner,insured,product);
    }



}
