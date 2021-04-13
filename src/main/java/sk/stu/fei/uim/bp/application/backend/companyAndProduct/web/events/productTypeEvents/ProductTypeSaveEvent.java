package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productTypeEvents;


import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductTypeDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.editors.ProductTypeEditor;

public class ProductTypeSaveEvent extends ProductTypeEditorEvent
{
    public ProductTypeSaveEvent(ProductTypeEditor source, ProductTypeDto productTypeDto)
    {
        super(source,productTypeDto);
    }

}
