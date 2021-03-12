package sk.stu.fei.uim.bp.application.backend.client.web;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the personal-card-component template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("personal-card-component")
@JsModule("./views/client/personal-card-component.js")
public class PersonalCardComponent extends PolymerTemplate<PersonalCardComponent.PersonalCardComponentModel> {

    @Id("frontSideName")
    private Span frontSideName;
    @Id("downloadFrontSide")
    private Button downloadFrontSide;
    @Id("uploadFrontSide")
    private Upload uploadFrontSide;
    @Id("backSideName")
    private Span backSideName;
    @Id("downloadBackSide")
    private Button downloadBackSide;
    @Id("uploadBackSide")
    private Upload uploadBackSide;

    /**
     * Creates a new PersonalCardComponent.
     */
    public PersonalCardComponent() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between PersonalCardComponent and personal-card-component
     */
    public interface PersonalCardComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
