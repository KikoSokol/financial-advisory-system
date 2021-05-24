package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.events.companyViewEvents;

import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.views.CompanyView;

public class CompanyViewEvent extends ComponentEvent<CompanyView>
{
    public CompanyViewEvent(CompanyView source) {
        super(source, false);
    }
}
