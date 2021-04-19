package sk.stu.fei.uim.bp.application.backend.file.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the file-add-component template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("file-add-component")
@JsModule("./views/files/file-add-component.js")
public class FileAddComponent extends PolymerTemplate<FileAddComponent.FileAddComponentModel> {

    @Id("upload")
    private Upload upload;
    @Id("fileName")
    private TextField fileName;
    @Id("addFile")
    private Button addFile;

    /**
     * Creates a new FileAddComponent.
     */
    public FileAddComponent() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between FileAddComponent and file-add-component
     */
    public interface FileAddComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
