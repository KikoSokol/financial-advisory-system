package sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.controllers;

import com.vaadin.flow.data.provider.ListDataProvider;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.domain.Company;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.service.CompanyService;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.dto.CompanyDto;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.filters.CompanyFilter;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.views.CompanyView;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CompanyController 
{
    private final CompanyService companyService;

    private final CompanyView companyView;
    private final ObjectId currentAgentId;

    private List<CompanyDto> allComapanies;
    private final ListDataProvider<CompanyDto> companyDataProvider;
    private final CompanyFilter companyFileter;

    public CompanyController(CompanyService companyService, CompanyView companyView, ObjectId currentAgentId)
    {
        this.companyService = companyService;
        this.companyView = companyView;
        this.currentAgentId = currentAgentId;
        this.companyFileter = new CompanyFilter();

        this.allComapanies = loadDataToTable();
        this.companyDataProvider = new ListDataProvider<>(this.allComapanies);

        this.companyView.getCompanyTable().setDataProvider(this.companyDataProvider);

        addFilterToCompanyDataProvider();
        
    }

    private List<CompanyDto> loadDataToTable()
    {
        List<Company> allCompany = this.companyService.getAllCompanyByCurrentAgent(this.currentAgentId);

        return convertCompanyListToDtoList(allCompany);
    }

    private List<CompanyDto> convertCompanyListToDtoList(List<Company> companies)
    {
        List<CompanyDto> companyDtos = new LinkedList<>();

        for(Company company : companies)
        {
            companyDtos.add(new CompanyDto(company));
        }

        return companyDtos;
    }

    public void refreshTableData()
    {
        this.allComapanies.clear();
        this.allComapanies.addAll(loadDataToTable());
        this.companyDataProvider.refreshAll();
    }

    private void addFilterToCompanyDataProvider()
    {
        this.companyDataProvider.setFilter(companyDto -> this.companyFileter.test(companyDto));
    }

    public Optional<Company> getCompanyById(ObjectId companyId) {
        return this.companyService.getCompanyById(companyId);
    }

    public CompanyFilter getCompanyFilter()
    {
        return this.companyFileter;
    }

    public void filterDataInTable()
    {
        this.companyDataProvider.refreshAll();
    }




}
