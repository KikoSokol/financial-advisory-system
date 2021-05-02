package sk.stu.fei.uim.bp.application.backend.file.components;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import sk.stu.fei.uim.bp.application.backend.file.repository.FileRepository;
import sk.stu.fei.uim.bp.application.backend.file.repository.implementation.FileRepositoryImpl;
import sk.stu.fei.uim.bp.application.backend.file.utils.FileAttachment;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;
import sk.stu.fei.uim.bp.application.validarors.messages.FileMessages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Tag("file-attachments-viewer")
@JsModule("./views/files/file-attachments-viewer.js")
public class FileAttachmentsViewer extends PolymerTemplate<FileAttachmentsViewer.FileAttachmentsViewerModel> {

    @Id("attachmentsTable")
    private Grid<FileAttachment> attachmentsTable;

    private final FileRepository fileRepository;

    @Autowired
    public FileAttachmentsViewer(FileRepositoryImpl fileRepository) {
        this.fileRepository = fileRepository;
        initColumns();

    }


    public void setFileAttachments(List<FileAttachment> fileAttachments)
    {
        this.attachmentsTable.setItems(fileAttachments);
    }

    public void clear()
    {
        this.attachmentsTable.setItems(new LinkedList<>());
    }


    private void initColumns()
    {
        Grid.Column<FileAttachment> nameColumn = this.attachmentsTable.addColumn(FileAttachment::getFileName);
        nameColumn.setAutoWidth(true);
        nameColumn.setHeader("Názov");
        nameColumn.setKey("nameColumn");
        nameColumn.setId("nameColumn");

        Grid.Column<FileAttachment> downloadColumn =  this.attachmentsTable.addComponentColumn(fileAttachment -> {
            Button downloadButton = new Button("Stiahnuť");
//            downloadButton.setIcon(new Icon(VaadinIcon.DOWNLOAD));
            downloadButton.addClickListener(event -> {
                downloadFile(fileAttachment.getFile());

            });
            return downloadButton;

        });
        downloadColumn.setAutoWidth(true);
        downloadColumn.setHeader("");
        downloadColumn.setKey("downloadColumn");
        downloadColumn.setId("downloadColumn");
    }



    private void downloadFile(GridFSFile file)
    {
        try {
            InputStream stream = this.fileRepository.getFile(file.getObjectId());
            StreamResource resource = getStreamResource(file.getFilename(),stream);
            StreamRegistration registration = VaadinSession.getCurrent().getResourceRegistry().registerResource(resource);
            UI.getCurrent().getPage().open(registration.getResourceUri().getRawPath(),"_blank");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage(FileMessages.DOWNLOAD_FILE_ERROR);
        }
    }

    private StreamResource getStreamResource(String fileName, InputStream stream) throws IOException
    {
        AtomicBoolean isCorrect = new AtomicBoolean(true);
        StreamResource streamResource = new StreamResource(fileName,() -> {
            try {
                return new ByteArrayInputStream(stream.readAllBytes());
            } catch (IOException e) {
                isCorrect.set(false);
            }
            return null;

        });

        if (!isCorrect.get())
            throw new IOException();

        return streamResource;
    }

    private void showErrorMessage(String errorText)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showErrorMessage(errorText);
    }



    public interface FileAttachmentsViewerModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
