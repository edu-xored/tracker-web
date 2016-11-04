package edu.xored.tracker;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.io.FileInputStream;

import org.springframework.web.multipart.MultipartFile;
@Service
public class FileService {
    public String saveFile(long hash, MultipartFile file) {
        if(file.isEmpty()) {
            return "File is empty";
        }
        String name = file.getOriginalFilename();
        try {
            byte[] bites = file.getBytes();
            String path = "./files/" + String.valueOf(hash);
            File dir = new File(path);
            if(!dir.exists()) {
                dir.mkdirs();
            }
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(path + "/" + name)));
            stream.write(bites);
            stream.flush();
            stream.close();
            return "Success!";
        } catch(Exception e) {
            return "Failed: " + e.getMessage();
        }
    }

    public ResponseEntity<byte[]> getFile(long hash, String name) throws IOException {
        String path = "./files/" + String.valueOf(hash) + "/" + name;
        ByteArrayOutputStream out = null;
        InputStream input = null;
        try {
            out = new ByteArrayOutputStream();
            input = new BufferedInputStream(new FileInputStream(path));
            int data =0;
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
        byte[] bytes = out.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
    }
}

