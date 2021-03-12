package sk.stu.fei.uim.bp.application.backend.client.web;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the physical-person-card template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("physical-person-card")
@JsModule("./views/client/physical-person-card.js")
public class PhysicalPersonCard extends PolymerTemplate<PhysicalPersonCard.PhysicalPersonCardModel> {

    @Id("fullName")
    private H4 fullName;
    @Id("email")
    private Label email;
    @Id("phone")
    private Label phone;

    /**
     * Creates a new PhysicalPersonCard.
     */
    public PhysicalPersonCard() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between PhysicalPersonCard and physical-person-card
     */
    public interface PhysicalPersonCardModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
