package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productEvents;

import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.editors.ProductEditor;

public class ProductUpdateEvent extends ProductEditorEvent
{

    public ProductUpdateEvent(ProductEditor source, ProductDto productDto) {
        super(source, productDto);
    }
}
