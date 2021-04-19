package sk.stu.fei.uim.bp.application.backend.file.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the file-attachments-add-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("file-attachments-add-view")
@JsModule("./views/files/file-attachments-add-view.js")
public class FileAttachmentsAddView extends PolymerTemplate<FileAttachmentsAddView.FileAttachmentsAddViewModel> {

    @Id("uploadedFileTable")
    private Grid uploadedFileTable;
    @Id("addFile")
    private Button addFile;

    /**
     * Creates a new FileAttachmentsAddView.
     */
    public FileAttachmentsAddView() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between FileAttachmentsAddView and file-attachments-add-view
     */
    public interface FileAttachmentsAddViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
