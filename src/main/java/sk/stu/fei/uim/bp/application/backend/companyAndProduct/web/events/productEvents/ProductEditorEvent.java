package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productEvents;

import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.editors.ProductEditor;

public abstract class ProductEditorEvent extends ComponentEvent<ProductEditor>
{
    private final ProductDto productDto;

    public ProductEditorEvent(ProductEditor source, ProductDto productDto) {
        super(source, false);
        this.productDto = productDto;
    }

    public ProductDto getProductDto() {
        return productDto;
    }
}
