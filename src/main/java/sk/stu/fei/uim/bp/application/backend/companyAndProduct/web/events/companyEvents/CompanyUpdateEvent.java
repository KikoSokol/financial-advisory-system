package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.companyEvents;


import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.CompanyDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.editors.CompanyEditor;

public class CompanyUpdateEvent extends CompanyEditorEvent
{
    public CompanyUpdateEvent(CompanyEditor source, CompanyDto companyDto) {
        super(source, companyDto);
    }
}
