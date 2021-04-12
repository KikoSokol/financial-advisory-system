package sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.events.companyEvents;


import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.dto.CompanyDto;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.editors.CompanyEditor;

public class CompanyUpdateEvent extends CompanyEditorEvent
{
    public CompanyUpdateEvent(CompanyEditor source, CompanyDto companyDto) {
        super(source, companyDto);
    }
}
