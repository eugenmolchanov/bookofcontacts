package commands;

import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.services.AttachmentService;
import com.itechart.javalab.firstproject.services.impl.AttachmentServiceImpl;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 30.09.2017.
 */
public class DownloadAttachment implements ActionCommand {

    private static Logger logger = Logger.getLogger(DownloadAttachment.class);
    private AttachmentService service = AttachmentServiceImpl.getInstance();
    private String page = ConfigurationManager.getProperty("contact");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Attachment attachment = service.findById(id);
            String path = attachment.getPathToFile().concat(attachment.getUuid());
            File file = new File(path);
            byte[] attachmentBytes = Files.readAllBytes(file.toPath());
            String mimeType = req.getServletContext().getMimeType(path);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            resp.setContentType(mimeType);
            resp.setContentLength((int) file.length());
            String [] array = attachment.getUuid().split("\\.");
            String extension = array[array.length - 1];
            resp.setHeader("Content-Disposition", "attachment; filename*=UTF-8''%c2%a3%20and%20%e2%82%ac%20rates" + attachment.getFileName().concat(".").concat(extension));
            OutputStream outputStream = resp.getOutputStream();
            outputStream.write(attachmentBytes);
            outputStream.flush();
            outputStream.close();
        } catch (SQLException | IOException e) {
            logger.error(e);
        }
        return page;
    }
}
