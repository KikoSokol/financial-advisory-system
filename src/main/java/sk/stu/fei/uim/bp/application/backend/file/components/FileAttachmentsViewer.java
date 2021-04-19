package sk.stu.fei.uim.bp.application.backend.file.components;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the file-attachments-viewer template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("file-attachments-viewer")
@JsModule("./views/files/file-attachments-viewer.js")
public class FileAttachmentsViewer extends PolymerTemplate<FileAttachmentsViewer.FileAttachmentsViewerModel> {

    @Id("attachmentsTable")
    private Grid attachmentsTable;

    /**
     * Creates a new FileAttachmentsViewer.
     */
    public FileAttachmentsViewer() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between FileAttachmentsViewer and file-attachments-viewer
     */
    public interface FileAttachmentsViewerModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
