package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.companyEvents;

import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.CompanyDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.editors.CompanyEditor;

public class CompanyDeleteEvent extends CompanyEditorEvent
{

    public CompanyDeleteEvent(CompanyEditor source, CompanyDto companyDto) {
        super(source, companyDto);
    }
}
