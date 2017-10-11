package util;

import com.itechart.javalab.firstproject.entities.Photo;
import dto.AttachmentDataDto;
import dto.PhoneDataDto;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
        if (!ServletFileUpload.isMultipartContent(req)) {
            return null;
        }
        Map<String, Object> parameters = new HashMap<>();
        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
        Set<PhoneDataDto> phones = new HashSet<>();
        Set<String> phonesForDelete = new HashSet<>();
        Set<String> attachmentsForDelete = new HashSet<>();
        Set<AttachmentDataDto> attachments = new HashSet<>();
        AttachmentDataDto attachment = new AttachmentDataDto();
        Photo photo = new Photo();
        PhoneDataDto phone = new PhoneDataDto();
        String pathToFolder = "D:\\IndividualProject\\contacts\\";
        String folder = pathToFolder.concat(LocalDate.now().toString()).concat(UUID.randomUUID().toString()).concat("\\");
        for (FileItem item : items) {
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                String fieldValue = item.getString("UTF-8");
                    if (!fieldName.equals("attachComment") && !fieldName.equals("attachTitle") && !fieldName.equals("countryCode") && !fieldName.equals("operatorCode") &&
                            !fieldName.equals("number") && !fieldName.equals("type") && !fieldName.equals("comment") && !fieldName.equals("phoneId")
                            && !fieldName.equals("attachId") && !fieldName.equals("attachmentForDelete") && !fieldName.equals("phoneForDelete")
                            && !fieldName.equals("photoId")) {
                        parameters.put(fieldName, fieldValue);
                    } else {
                        switch (fieldName) {
                            case "photoId":
                                photo.setId(Long.parseLong(fieldValue));
                                break;
                            case "phoneId":
                                phone.setId(fieldValue);
                                break;
                            case "countryCode":
                                phone.setCountryCode(fieldValue);
                                break;
                            case "operatorCode":
                                phone.setOperatorCode(fieldValue);
                                break;
                            case "number":
                                phone.setNumber(fieldValue);
                                break;
                            case "type":
                                phone.setType(fieldValue);
                                break;
                            case "comment":
                                phone.setComment(fieldValue);
                                phones.add(phone);
                                phone = new PhoneDataDto();
                                break;
                            case "attachId":
                                attachment.setId(fieldValue);
                                break;
                            case "attachTitle":
                                attachment.setFileName(fieldValue);
                                break;
                            case "attachComment":
                                attachment.setCommentary(fieldValue);
                                attachments.add(attachment);
                                attachment = new AttachmentDataDto();
                                break;
                            case "phoneForDelete":
                                phonesForDelete.add(fieldValue);
                                break;
                            case "attachmentForDelete":
                                attachmentsForDelete.add(fieldValue);
                                break;
                        }
                    }
            } else {
                String fieldName = item.getFieldName();
                String fileName = FilenameUtils.getName(item.getName());
                if (!fileName.equals("")) {
                    InputStream fileContent = item.getInputStream();
                    String[] array = fileName.split("\\.");
                    String format = array[array.length - 1];
                    if (fieldName.equals("photoFile")) {
                        String uuid = UUID.randomUUID().toString();
                        String fileTitle = uuid.concat(".").concat(format);
                        File file = new File(folder.concat(fileTitle));
                        FileUtils.copyInputStreamToFile(fileContent, file);
                        photo.setPathToFile(folder);
                        photo.setUuid(fileTitle);
                        parameters.put(fieldName, photo);
                    } else if (fieldName.equals("attachmentFile")) {
                        String uuid = UUID.randomUUID().toString();
                        String fileTitle = uuid.concat(".").concat(format);
                        File file = new File(folder.concat(fileTitle));
                        FileUtils.copyInputStreamToFile(fileContent, file);
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
        if (phonesForDelete.size() != 0) {
            parameters.put("phonesForDelete", phonesForDelete);
        }
        if (attachmentsForDelete.size() != 0) {
            parameters.put("attachmentsForDelete", attachmentsForDelete);
        }
        return parameters;
    }
}
