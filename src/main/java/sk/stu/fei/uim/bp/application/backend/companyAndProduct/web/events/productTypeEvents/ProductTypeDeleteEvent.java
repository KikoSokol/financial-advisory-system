package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productTypeEvents;

import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductTypeDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.editors.ProductTypeEditor;

public class ProductTypeDeleteEvent extends ProductTypeEditorEvent
{

    public ProductTypeDeleteEvent(ProductTypeEditor source, ProductTypeDto productTypeDto) {
        super(source, productTypeDto);
    }
}
