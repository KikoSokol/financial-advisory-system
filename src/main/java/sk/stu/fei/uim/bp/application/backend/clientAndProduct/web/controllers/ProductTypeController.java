package sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.controllers;

import com.vaadin.flow.data.provider.ListDataProvider;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.domain.ProductType;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.service.ProductTypeService;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.dto.ProductTypeDto;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.filters.ProductTypeFilter;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.views.ProductTypeView;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class ProductTypeController
{
    private final ProductTypeService productTypeService;

    private final ProductTypeView productTypeView;
    private final ObjectId currentAgentId;

    private List<ProductTypeDto> allProductTypes;
    private final ListDataProvider<ProductTypeDto> productTypeDataProvider;
    private final ProductTypeFilter productTypeFilter;


    public ProductTypeController(ProductTypeService productTypeService,ProductTypeView productTypeView, ObjectId currentAgentId)
    {
        this.productTypeService = productTypeService;
        this.productTypeView = productTypeView;
        this.currentAgentId = currentAgentId;
        this.productTypeFilter = new ProductTypeFilter();

        this.allProductTypes = loadDataToTable();
        this.productTypeDataProvider = new ListDataProvider<>(this.allProductTypes);

        this.productTypeView.getProductTypeTable().setDataProvider(this.productTypeDataProvider);

        addFilterToProductTypeDataProvider();
    }

    private List<ProductTypeDto> loadDataToTable()
    {
        List<ProductType> allProductType = this.productTypeService.getAllProductTypeOfCurrentAgent(this.currentAgentId);

        return convertProductTypeListToDtoList(allProductType);
    }

    private List<ProductTypeDto> convertProductTypeListToDtoList(List<ProductType> productTypes)
    {
        List<ProductTypeDto> productTypeDtos = new LinkedList<>();

        for(ProductType productType : productTypes)
        {
            productTypeDtos.add(new ProductTypeDto(productType));
        }

        return productTypeDtos;
    }

    public void refreshTableData()
    {
        this.allProductTypes.clear();
        this.allProductTypes.addAll(loadDataToTable());
        this.productTypeDataProvider.refreshAll();
    }

    private void addFilterToProductTypeDataProvider()
    {
        this.productTypeDataProvider.setFilter(productTypeDto -> this.productTypeFilter.test(productTypeDto));
    }

    public Optional<ProductType> getProductTypeById(ObjectId productTypeId)
    {
        return this.productTypeService.getProductTypeById(productTypeId);
    }

    public ProductTypeFilter getProductTypeFilter()
    {
        return this.productTypeFilter;
    }

    public void filterDataInTable()
    {
        this.productTypeDataProvider.refreshAll();
    }




}
