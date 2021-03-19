package sk.stu.fei.uim.bp.application.backend.client.web;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import sk.stu.fei.uim.bp.application.backend.client.domain.IdentifyCardCopyFiles;
import sk.stu.fei.uim.bp.application.backend.client.domain.IdentifyCardCopyReference;
import sk.stu.fei.uim.bp.application.backend.file.FileRepository;
import sk.stu.fei.uim.bp.application.backend.file.FileRepositoryImpl;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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

    private boolean isSetFiles;

    private final FileRepository fileRepository;

    private IdentifyCardCopyFiles infoAboutFiles;

    MemoryBuffer bufferFrontSideFile;
    MemoryBuffer bufferForBackSideFile;

    private boolean isUploadedFrontSideFile;
    private boolean isUploadedBackSideFile;


    @Autowired
    public PersonalCardComponent(FileRepositoryImpl fileRepository) {
        this.fileRepository = fileRepository;
        clear();


        this.uploadFrontSide.addSucceededListener(succeededEvent -> isUploadedFrontSideFile = true);

        this.uploadBackSide.addSucceededListener(event -> this.isUploadedBackSideFile = true);


        this.downloadFrontSide.addClickListener(event -> {
            if(isSetFiles)
                downloadFile(this.infoAboutFiles.getFrontSide());
            else
            {
                //TODO: dorob notifikaciu o nenastavenom subore
            }
        });

        this.downloadBackSide.addClickListener(event -> {
            if(isSetFiles)
                downloadFile(this.infoAboutFiles.getBackSide());
            else
            {
                //TODO: dorob notifikaciu o nenastavenom subore
            }
        });




    }

    public void clear()
    {
        this.frontSideName.setText("Subor nebol pridaný");
        this.backSideName.setText("Subor nebol pridaný");
        this.isSetFiles = false;
        this.isUploadedFrontSideFile = false;
        this.isUploadedBackSideFile = false;
        this.bufferFrontSideFile = null;
        this.bufferForBackSideFile = null;
        setEnabledDownloadButtons(false);
        initBuffers();
    }

    private void initBuffers()
    {
        this.bufferFrontSideFile = new MemoryBuffer();
        this.bufferForBackSideFile = new MemoryBuffer();

        this.uploadFrontSide.setReceiver(bufferFrontSideFile);
        this.uploadBackSide.setReceiver(bufferForBackSideFile);
    }

    public void setFiles(IdentifyCardCopyReference identifyCardCopyReference)
    {
        if(identifyCardCopyReference == null)
            clear();
        else
        {
            initFiles(identifyCardCopyReference);
        }


    }

    private void initFiles(IdentifyCardCopyReference identifyCardCopyReference)
    {
        this.infoAboutFiles = new IdentifyCardCopyFiles();
        this.infoAboutFiles.setFrontSide(this.fileRepository.getInformationAboutFile(identifyCardCopyReference.getFrontSide()));
        this.infoAboutFiles.setBackSide(this.fileRepository.getInformationAboutFile(identifyCardCopyReference.getBackSide()));
        setFilesName();

        this.isSetFiles = true;
        setEnabledDownloadButtons(true);

        initBuffers();
    }

    private void setFilesName()
    {
        this.frontSideName.setText(infoAboutFiles.getFrontSide().getFilename());
        this.backSideName.setText(infoAboutFiles.getBackSide().getFilename());
    }

    private void setEnabledDownloadButtons(boolean enabled)
    {
        this.downloadFrontSide.setEnabled(enabled);
        this.downloadBackSide.setEnabled(enabled);
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
            //TODO: pridaj notifikáciu o tom že sa nepodarilo stiahnuť súbor
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



    public Optional<FileWrapper> getFrontSideFile()
    {
        if(!isUploadedFrontSideFile)
            return Optional.empty();

        return Optional.of(new FileWrapper(bufferFrontSideFile.getInputStream(),bufferFrontSideFile.getFileData()));
    }

    public Optional<FileWrapper> getBackSideFile()
    {
        if(!isUploadedBackSideFile)
            return Optional.empty();

        return Optional.of(new FileWrapper(bufferForBackSideFile.getInputStream(),bufferForBackSideFile.getFileData()));
    }



    /**
     * This model binds properties between PersonalCardComponent and personal-card-component
     */
    public interface PersonalCardComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
