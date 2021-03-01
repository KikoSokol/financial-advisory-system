package sk.stu.fei.uim.bp.application.backend.file;

import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.vaadin.flow.component.upload.receivers.FileData;
import org.bson.types.ObjectId;
import java.io.IOException;
import java.io.InputStream;

public interface FileRepository
{
    ObjectId saveFile(InputStream file, FileData fileData, DBObject metadata);

    ObjectId saveFile(InputStream file, FileData fileData);

    ObjectId saveFile(InputStream file, FileData fileData, String customFileName, DBObject metadata);

    ObjectId saveFile(InputStream file, FileData fileData, String customFileName);

    GridFSFile getInformationAboutFile(ObjectId fileId);

    InputStream getFile(ObjectId fileId) throws IOException;

    void deleteFile(ObjectId fileIdToDelete);
}
