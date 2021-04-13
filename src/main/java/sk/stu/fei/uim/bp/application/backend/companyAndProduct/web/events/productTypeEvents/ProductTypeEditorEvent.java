package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.productTypeEvents;


import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductTypeDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.editors.ProductTypeEditor;

public abstract class ProductTypeEditorEvent extends ComponentEvent<ProductTypeEditor>
{
    private final ProductTypeDto productTypeDto;

    public ProductTypeEditorEvent(ProductTypeEditor source, ProductTypeDto productTypeDto)
    {
        super(source,false);
        this.productTypeDto = productTypeDto;
    }

    public ProductTypeDto getProductTypeDto() {
        return productTypeDto;
    }
}
