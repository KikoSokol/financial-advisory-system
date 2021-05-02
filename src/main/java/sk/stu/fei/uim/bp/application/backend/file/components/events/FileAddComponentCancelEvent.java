package sk.stu.fei.uim.bp.application.backend.file.components.events;

import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;
import sk.stu.fei.uim.bp.application.backend.file.components.FileAddComponent;

public class FileAddComponentCancelEvent extends FileAddComponentEvent
{
    public FileAddComponentCancelEvent(FileAddComponent source, FileWrapper fileWrapper) {
        super(source, fileWrapper);
    }
}
