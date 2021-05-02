package sk.stu.fei.uim.bp.application.backend.file.components;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;
import sk.stu.fei.uim.bp.application.backend.file.components.events.FileAddComponentAddEvent;
import sk.stu.fei.uim.bp.application.backend.file.components.events.FileAddComponentCancelEvent;


@Tag("file-add-component")
@JsModule("./views/files/file-add-component.js")
public class FileAddComponent extends PolymerTemplate<FileAddComponent.FileAddComponentModel> {

    @Id("upload")
    private Upload upload;
    @Id("fileName")
    private TextField fileName;
    @Id("addFile")
    private Button addFile;
    @Id("cancel")
    private Button cancel;


    private final MemoryBuffer memoryBuffer;


    public FileAddComponent()
    {
        this.memoryBuffer = new MemoryBuffer();
        this.addFile.setEnabled(false);

        this.upload.setReceiver(this.memoryBuffer);
//        this.upload.setMaxFileSize(1073741824);

        this.upload.addSucceededListener(succeededEvent ->
        {
            this.enableAddButton();
        });


        this.cancel.addClickListener(event -> {
            this.cancelEvent();
        });



    }


    private void enableAddButton()
    {
        this.addFile.addClickListener(event ->
        {
            this.addFile();
        });
        this.addFile.setEnabled(true);
    }


    private void addFile()
    {
        if(this.fileName.isEmpty())
        {
            FileWrapper fileWrapper = new FileWrapper(this.memoryBuffer.getInputStream(),this.memoryBuffer.getFileData());
            fireEvent(new FileAddComponentAddEvent(this,fileWrapper));
        }
        else
        {
            FileWrapper fileWrapper = new FileWrapper(this.memoryBuffer.getInputStream(),this.memoryBuffer.getFileData(), this.fileName.getValue());
            fireEvent(new FileAddComponentAddEvent(this,fileWrapper));
        }
    }

    private void cancelEvent()
    {
        this.fireEvent(new FileAddComponentCancelEvent(this,null));
    }



    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener)
    {
        return getEventBus().addListener(eventType,listener);
    }


    public interface FileAddComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
