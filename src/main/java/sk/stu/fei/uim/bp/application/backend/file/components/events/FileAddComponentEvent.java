package sk.stu.fei.uim.bp.application.backend.file.components.events;

import com.vaadin.flow.component.ComponentEvent;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;
import sk.stu.fei.uim.bp.application.backend.file.components.FileAddComponent;

public class FileAddComponentEvent extends ComponentEvent<FileAddComponent>
{
    private final FileWrapper fileWrapper;

    public FileAddComponentEvent(FileAddComponent source, FileWrapper fileWrapper) {
        super(source, false);
        this.fileWrapper = fileWrapper;
    }

    public FileWrapper getFileWrapper() {
        return fileWrapper;
    }
}
