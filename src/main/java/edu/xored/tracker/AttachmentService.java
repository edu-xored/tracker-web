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

@Service
public class AttachmentService {
    // TODO: store attachments via git core API.
    private static final String ATTACHMENTS_ROOT_DIR = "./files";

    public void saveAttachment(long issueHash, MultipartFile file) {
        if(file.isEmpty()) {
            throw new FileIsEmptyException();
        }
        String name = file.getOriginalFilename();
        String path = ATTACHMENTS_ROOT_DIR + File.separator + String.valueOf(issueHash);
        File newFile = new File(path + File.separator + name);
        if(newFile.exists()) {
            throw new FileAlreadyExistsException();
        }
        File dir = new File(path);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        try (BufferedOutputStream stream =
                     new BufferedOutputStream(new FileOutputStream(newFile))) {
            byte[] bytes = file.getBytes();
            stream.write(bytes);
            stream.flush();
        } catch(IOException e) {
            throw new StreamErrorException(e.getMessage());
        }
    }

    public ByteArrayOutputStream getAttachment(long issueHash, String name) {
        String path = ATTACHMENTS_ROOT_DIR + File.separator + String.valueOf(issueHash) + File.separator + name;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             InputStream input = new BufferedInputStream(new FileInputStream(path));) {
            int data = 0;
            while((data = input.read())!=-1) {
                out.write(data);
            }
            return out;
        } catch(IOException e) {
            throw new StreamErrorException(e.getMessage());
        }
    }

    private class FileIsEmptyException extends RuntimeException {
        public FileIsEmptyException() {
            super("File is empty");
        }
    }

    private class FileAlreadyExistsException extends RuntimeException {
        public FileAlreadyExistsException() {
            super("File already exists");
        }
    }

    private class StreamErrorException extends RuntimeException {
        public StreamErrorException() {
            super("Unknown error");
        }

        public StreamErrorException(String message) {
            super(message);
        }
    }
}
