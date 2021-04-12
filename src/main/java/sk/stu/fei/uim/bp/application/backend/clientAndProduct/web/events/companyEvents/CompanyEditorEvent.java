package sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.events.companyEvents;


import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.dto.CompanyDto;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.editors.CompanyEditor;

public abstract class CompanyEditorEvent extends ComponentEvent<CompanyEditor>
{
    private final CompanyDto companyDto;

    public CompanyEditorEvent(CompanyEditor source, CompanyDto companyDto) {
        super(source,false);
        this.companyDto = companyDto;
    }

    public CompanyDto getCompanyDto() {
        return companyDto;
    }
}
