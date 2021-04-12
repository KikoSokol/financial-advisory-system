package sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.events.companyEvents;


import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.dto.CompanyDto;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.editors.CompanyEditor;

public class CompanySaveEvent extends CompanyEditorEvent
{
    public CompanySaveEvent(CompanyEditor source, CompanyDto companyDto) {
        super(source, companyDto);
    }
}
