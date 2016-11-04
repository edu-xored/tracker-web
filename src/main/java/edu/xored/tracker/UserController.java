package edu.xored.tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class UserController {

    @GetMapping(value = "/user")
    public User getUser() {
        Process theProcess = null;
        BufferedReader inStream = null;
        String name = null;
        String eMail = null;
        try {
            theProcess = Runtime.getRuntime().exec("git config user.name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inStream = new BufferedReader(new InputStreamReader( theProcess.getInputStream() ));
            name = inStream.readLine();
        } catch(IOException e) {
            System.err.println("Error on inStream.readLine()");
            e.printStackTrace();
        }
        try {
            theProcess = Runtime.getRuntime().exec("git config user.email");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inStream = new BufferedReader(new InputStreamReader( theProcess.getInputStream() ));
            eMail = inStream.readLine();
        } catch(IOException e) {
            System.err.println("Error on inStream.readLine()");
            e.printStackTrace();
        }
        return new User(name, eMail);
    }
}