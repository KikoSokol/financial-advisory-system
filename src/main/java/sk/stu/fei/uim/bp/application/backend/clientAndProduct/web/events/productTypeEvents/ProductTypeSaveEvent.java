package sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.events.productTypeEvents;


import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.dto.ProductTypeDto;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.editors.ProductTypeEditor;

public class ProductTypeSaveEvent extends ProductTypeEditorEvent
{
    public ProductTypeSaveEvent(ProductTypeEditor source, ProductTypeDto productTypeDto)
    {
        super(source,productTypeDto);
    }

}
