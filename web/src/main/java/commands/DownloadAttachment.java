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

    private static Logger logger = Logger.getLogger(CreateNewContact.class);
    private AttachmentService service = AttachmentServiceImpl.getInstance();
    private String page = ConfigurationManager.getProperty("contact");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Attachment attachment = service.findById(id);
            String path = attachment.getPathToFile().concat(attachment.getUuid());
            byte[] attachmentBytes = Files.readAllBytes(new File(path).toPath());
            resp.setContentType("text/plain");
            resp.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", "Movie info.txt"));
            resp.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", attachment.getFileName()));
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
