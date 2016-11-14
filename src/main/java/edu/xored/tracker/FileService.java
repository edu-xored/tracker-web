package edu.xored.tracker;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class FileService {
    public String saveFile(long hash, MultipartFile file) {
        if(file.isEmpty()) {
            return "File is empty";
        }
        String name = file.getOriginalFilename();
        try {
            String path = "./files/" + String.valueOf(hash);
            File newFile = new File(path + "/" + name);
            if(newFile.exists()) {
                return "File already exist";
            }
            File dir = new File(path);
            if(!dir.exists()) {
                dir.mkdirs();
            }
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(newFile));
            stream.write(bytes);
            stream.flush();
            stream.close();
            return "Success!";
        } catch(Exception e) {
            return "Failed: " + e.getMessage();
        }
    }

    public ByteArrayOutputStream getFile(long hash, String name) throws IOException {
        String path = "./files/" + String.valueOf(hash) + "/" + name;
        ByteArrayOutputStream out = null;
        InputStream input = null;
        try {
            out = new ByteArrayOutputStream();
            input = new BufferedInputStream(new FileInputStream(path));
            int data = 0;
            while((data = input.read())!=-1) {
                out.write(data);
            }
        } finally {
            if(null!=input) {
                input.close();
            }
            if(null!=out) {
                out.close();
            }
        }
        return out;
    }
}
