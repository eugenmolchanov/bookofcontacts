package util;

import com.itechart.javalab.firstproject.entities.Photo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Yauhen Malchanau on 15.09.2017.
 */
public class Data {

    public static Map<String, Object> uploadImage(HttpServletRequest req) throws IOException, ServletException, FileUploadException {
        Map<String, Object> parameters = new HashMap<>();
        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
        for (FileItem item : items) {
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                String fieldValue = item.getString();
                parameters.put(fieldName, fieldValue);
            } else {
                String fieldName = item.getFieldName();
                String fileName = FilenameUtils.getName(item.getName());
                InputStream fileContent = item.getInputStream();
                String pathToFolder = "D:\\IndividualProject\\images\\";
                String uuid = UUID.randomUUID().toString();
                String format = ".jpg";
                String path = pathToFolder.concat(uuid).concat(format);
                File file = new File(path);
                FileUtils.copyInputStreamToFile(fileContent, file);
                Photo photo = new Photo(0, path, uuid);
                parameters.put(fieldName, photo);
            }
        }
        return parameters;
    }
}
