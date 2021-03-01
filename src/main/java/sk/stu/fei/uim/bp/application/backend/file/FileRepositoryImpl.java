package sk.stu.fei.uim.bp.application.backend.file;

import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.vaadin.flow.component.upload.receivers.FileData;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;

@Repository
public class FileRepositoryImpl implements FileRepository
{
    private final GridFsOperations gridFsOperations;

    @Autowired
    public FileRepositoryImpl(GridFsTemplate gridFsTemplate)
    {
        this.gridFsOperations = gridFsTemplate;
    }

    @Override
    public ObjectId saveFile(InputStream file, FileData fileData, DBObject metadata)
    {

        return this.gridFsOperations.store(file,fileData.getFileName(),fileData.getMimeType(),metadata);
    }

    @Override
    public ObjectId saveFile(InputStream file, FileData fileData)
    {

        return this.gridFsOperations.store(file,fileData.getFileName(),fileData.getMimeType());
    }

    @Override
    public ObjectId saveFile(InputStream file, FileData fileData, String customFileName,DBObject metadata)
    {
        String name = createCustomFileName(fileData.getFileName(),customFileName);
        return this.gridFsOperations.store(file,name,fileData.getMimeType(),metadata);
    }

    @Override
    public ObjectId saveFile(InputStream file, FileData fileData, String customFileName)
    {
        String name = createCustomFileName(fileData.getFileName(),customFileName);
        return this.gridFsOperations.store(file,name,fileData.getMimeType());
    }

    @Override
    public GridFSFile getInformationAboutFile(ObjectId fileId)
    {
        Criteria criteria = new Criteria("_id");
        criteria.is(fileId);

        Query query = new Query(criteria);

        return this.gridFsOperations.findOne(query);
    }

    @Override
    public InputStream getFile(ObjectId fileId) throws IOException {
        GridFSFile fileInfo = getInformationAboutFile(fileId);

        return this.gridFsOperations.getResource(fileInfo).getInputStream();
    }

    private String createCustomFileName(String oldFileName, String newFileName)
    {
        int dotPossition = oldFileName.lastIndexOf('.');

        return newFileName.concat(oldFileName.substring(dotPossition));
    }

    @Override
    public void deleteFile(ObjectId fileIdToDelete)
    {
        Criteria criteria = new Criteria("_id");
        criteria.is(fileIdToDelete);

        Query query = new Query(criteria);

        this.gridFsOperations.delete(query);
    }
}
