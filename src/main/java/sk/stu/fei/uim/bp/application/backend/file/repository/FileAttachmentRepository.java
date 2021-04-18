package sk.stu.fei.uim.bp.application.backend.file.repository;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.file.utils.FileAttachment;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface FileAttachmentRepository
{
    FileAttachment addNewFileAttachment(@NotNull FileAttachment newFileAttachment);

    FileAttachment getFileAttachmentById(@NotNull ObjectId fileAttachmentId);

    List<FileAttachment> getFileAttachmentByListOfId(@NotNull List<ObjectId> fileAttachmentIds);
}
