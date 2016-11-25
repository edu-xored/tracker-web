package edu.xored.tracker;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class AttachmentService {
    // TODO: store attachments via git core API.
    private static final Path ATTACHMENTS_ROOT_DIR = Paths.get(".").resolve("files");

    public void saveAttachment(long issueHash, MultipartFile file) {
        if(file.isEmpty()) {
            throw new FileIsEmptyException();
        }
        String name = file.getOriginalFilename();
        Path path = getIssuePath(issueHash);
        File newFile = new File(path.resolve(name).toString());
        File dir = new File(path.toString());
        if(!dir.exists()) {
            dir.mkdirs();
        }
        try (BufferedOutputStream stream =
                     new BufferedOutputStream(new FileOutputStream(newFile))) {
            byte[] bytes = file.getBytes();
            stream.write(bytes);
            stream.flush();
        } catch(IOException e) {
            throw new AttachmentException(e);
        }
    }

    public ByteArrayInputStream getAttachment(long issueHash, String name) {
        Path path = getIssuePath(issueHash).resolve(name);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             InputStream input = new BufferedInputStream(new FileInputStream(path.toString()));) {
            int data;
            while ((data = input.read()) != -1) {
                out.write(data);
            }
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new AttachmentException(e);
        }
    }

    private Path getIssuePath (long issueHash) {
        return ATTACHMENTS_ROOT_DIR.resolve(String.valueOf(issueHash));
    }

    public static class FileIsEmptyException extends AttachmentException {
        public FileIsEmptyException() {
            super("File is empty");
        }
    }

    public static class AttachmentException extends RuntimeException {
        public AttachmentException(IOException e) {
            super(e);
        }
        public AttachmentException(String message) {
            super(message);
        }
    }
}
