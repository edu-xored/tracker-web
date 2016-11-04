package edu.xored.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class UserController {

    @GetMapping(value = "/user")
    public User getUser() {
        Process theProcess = null;
        BufferedReader inStream = null;
        String name = null;
        String email = null;
        try {
            theProcess = Runtime.getRuntime().exec("git config user.name");
        } catch (IOException e) {
            e.printStackTrace();
            throw new GitNotFoundException();
        }
        try {
            inStream = new BufferedReader(new InputStreamReader( theProcess.getInputStream() ));
            name = inStream.readLine();
        } catch(IOException e) {
            e.printStackTrace();
            throw new InSrteamException();
        }

        try {
            theProcess = Runtime.getRuntime().exec("git config user.email");
        } catch (IOException e) {
            e.printStackTrace();
            throw new GitNotFoundException();
        }
        try {
            inStream = new BufferedReader(new InputStreamReader( theProcess.getInputStream() ));
            email = inStream.readLine();
        } catch(IOException e) {
            e.printStackTrace();
            throw new InSrteamException();
        }
        return new User(name, email);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Git not found")
    private class GitNotFoundException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "Error on inStream.readLine()")
    private class InSrteamException extends RuntimeException {
    }
}