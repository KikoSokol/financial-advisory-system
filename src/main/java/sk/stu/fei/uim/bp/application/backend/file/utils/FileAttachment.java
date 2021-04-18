package sk.stu.fei.uim.bp.application.backend.file.utils;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotNull;

@Data
@Document("FileAttachment")
public class FileAttachment
{
    @MongoId
    private ObjectId fileAttachmentId;

    @NotNull
    private ObjectId fileInDbId;

    @Transient
    private GridFSFile file;

    @NotNull
    private String fileName;


    public FileAttachment(ObjectId fileInDbId, String fileName) {
        setFileInDbId(fileInDbId);
        setFileName(fileName);
    }
}
