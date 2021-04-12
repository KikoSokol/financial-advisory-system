package sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.events.productTypeEvents;


import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.dto.ProductTypeDto;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.editors.ProductTypeEditor;

public class ProductTypeCancelEvent extends ProductTypeEditorEvent
{
    public ProductTypeCancelEvent(ProductTypeEditor source, ProductTypeDto productTypeDto) {
        super(source, productTypeDto);
    }
}
