package util;

import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.entities.Phone;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Yauhen Malchanau on 15.09.2017.
 */
public class Data {

    public static Map<String, Object> upload(HttpServletRequest req) throws IOException, ServletException, FileUploadException {
        Map<String, Object> parameters = new HashMap<>();
        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
        Set<Phone> phones = new HashSet<>();
        Set<Attachment> attachments = new HashSet<>();
        Attachment attachment = new Attachment();
        Phone phone = new Phone();
        String pathToFolder = "D:\\IndividualProject\\contacts\\";
        String folder = pathToFolder.concat(LocalDate.now().toString()).concat(UUID.randomUUID().toString()).concat("\\");
        for (FileItem item : items) {
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                String fieldValue = item.getString("UTF-8");
                if (!fieldValue.equals("")) {
                    if (!fieldName.equals("attachComment") && !fieldName.equals("countryCode") && !fieldName.equals("operatorCode") &&
                            !fieldName.equals("number") && !fieldName.equals("type") && !fieldName.equals("comment")) {
                        parameters.put(fieldName, fieldValue);
                    } else if (!fieldName.equals("attachComment")) {
                        switch (fieldName) {
                            case "countryCode":
                                phone.setCountryCode(fieldValue);
                                break;
                            case "operatorCode":
                                phone.setOperatorCode(Integer.parseInt(fieldValue));
                                break;
                            case "number":
                                phone.setNumber(Integer.parseInt(fieldValue));
                                break;
                            case "type":
                                phone.setType(fieldValue);
                                break;
                            case "comment":
                                phone.setComment(fieldValue);
                                phones.add(phone);
                                phone = new Phone();
                                break;
                        }
                    } else if (fieldName.equals("attachComment")) {
                        attachment.setCommentary(fieldValue);
                        attachments.add(attachment);
                        attachment = new Attachment();
                    }
                }
            } else {
                String fieldName = item.getFieldName();
                String fileName = FilenameUtils.getName(item.getName());
                if (!fileName.equals("")) {
                    InputStream fileContent = item.getInputStream();
                    String[] array = fileName.split("\\.");
                    String format = array[1];
                    if (fieldName.equals("image")) {
                        String uuid = UUID.randomUUID().toString();
                        String fileTitle = uuid.concat(".").concat(format);
                        File file = new File(folder.concat(fileTitle));
                        FileUtils.copyInputStreamToFile(fileContent, file);
                        Photo photo = new Photo(0, folder, fileTitle);
                        parameters.put(fieldName, photo);
                    } else if (fieldName.equals("attachment")) {
                        String uuid = UUID.randomUUID().toString();
                        String fileTitle = uuid.concat(".").concat(format);
                        File file = new File(folder.concat(fileTitle));
                        FileUtils.copyInputStreamToFile(fileContent, file);
                        attachment.setFileName(fileName);
                        attachment.setPathToFile(folder);
                        attachment.setUuid(fileTitle);
                        attachment.setDate(Timestamp.valueOf(LocalDateTime.now()));
                    }
                }
            }
        }
        if (attachments.size() != 0) {
            parameters.put("attachments", attachments);
        }
        if (phones.size() != 0) {
            parameters.put("phones", phones);
        }
        return parameters;
    }
}
