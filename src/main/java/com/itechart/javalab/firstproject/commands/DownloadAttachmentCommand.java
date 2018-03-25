package com.itechart.javalab.firstproject.commands;

import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.service.AttachmentService;
import com.itechart.javalab.firstproject.service.impl.AttachmentServiceImpl;
import org.apache.log4j.Logger;
import com.itechart.javalab.firstproject.resources.ConfigurationManager;
import com.itechart.javalab.firstproject.resources.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 30.09.2017.
 */
public class DownloadAttachmentCommand implements ActionCommand {

    private static Logger logger = Logger.getLogger(DownloadAttachmentCommand.class);
    private AttachmentService service = AttachmentServiceImpl.getInstance();
    private final String ACTIVE_PAGE = ConfigurationManager.getProperty("contact");
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
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
            String fileName = attachment.getFileName().concat(".").concat(extension);
            fileName = URLEncoder.encode(fileName, "UTF-8");
            fileName = URLDecoder.decode(fileName, "ISO8859_1");
            resp.setHeader("Content-disposition", "attachment; filename="+ fileName);
            OutputStream outputStream = resp.getOutputStream();
            outputStream.write(attachmentBytes);
            outputStream.flush();
            outputStream.close();
            return ACTIVE_PAGE;
        } catch (SQLException e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return ACTIVE_PAGE;
        } catch (Exception e) {
            logger.error("Can't load attachment. Exception.");
            logger.error(e.getMessage(), e);
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return ERROR_PAGE;
        }
    }
}
