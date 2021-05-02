package sk.stu.fei.uim.bp.application.backend.file.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;
import sk.stu.fei.uim.bp.application.backend.file.components.events.FileAddComponentAddEvent;
import sk.stu.fei.uim.bp.application.backend.file.components.events.FileAddComponentCancelEvent;
import sk.stu.fei.uim.bp.application.backend.file.components.events.FileAddComponentEvent;
import sk.stu.fei.uim.bp.application.backend.file.utils.FileAttachment;

import java.util.LinkedList;
import java.util.List;


@Tag("file-attachments-add-view")
@JsModule("./views/files/file-attachments-add-view.js")
public class FileAttachmentsAddView extends PolymerTemplate<FileAttachmentsAddView.FileAttachmentsAddViewModel> {

    @Id("uploadedFileTable")
    private Grid<FileWrapper> uploadedFileTable;
    @Id("addFile")
    private Button addFile;

    List<FileWrapper> listOfAddFiles;

    private Dialog dialog;


    public FileAttachmentsAddView()
    {
        initColumns();
        initDialog();
        this.listOfAddFiles = new LinkedList<>();


        this.addFile.addClickListener(event -> openAddWindow());

    }

    private void initColumns()
    {
        Grid.Column<FileWrapper> nameColumn = this.uploadedFileTable.addColumn(FileWrapper::getFileName);
        nameColumn.setAutoWidth(true);
        nameColumn.setHeader("Názov");
        nameColumn.setKey("nameColumn");
        nameColumn.setId("nameColumn");
    }

    private void initDialog()
    {
        this.dialog = new Dialog();
        this.dialog.setModal(true);
        this.dialog.setCloseOnOutsideClick(false);
        this.dialog.setDraggable(true);
    }


    public List<FileWrapper> getAddFiles()
    {
        return this.listOfAddFiles;
    }


    private FileAddComponent createFileAddComponent()
    {
        FileAddComponent fileAddComponent = new FileAddComponent();
        fileAddComponent.addListener(FileAddComponentAddEvent.class,this::addFile);
        fileAddComponent.addListener(FileAddComponentCancelEvent.class,this::cancelAddFile);

        return fileAddComponent;
    }


    private void openAddWindow()
    {
        this.dialog.add(createFileAddComponent());
        this.dialog.open();
    }

    private void closeAddWindow()
    {
        this.dialog.removeAll();
        this.dialog.close();
    }


    private void addFile(FileAddComponentAddEvent event)
    {
        this.listOfAddFiles.add(event.getFileWrapper());
        this.uploadedFileTable.setItems(this.listOfAddFiles);
        this.closeAddWindow();
    }

    private void cancelAddFile(FileAddComponentCancelEvent event)
    {
        this.closeAddWindow();
    }


    public void clear()
    {
        this.listOfAddFiles.clear();
        this.uploadedFileTable.setItems(this.listOfAddFiles);
    }



    public interface FileAttachmentsAddViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
