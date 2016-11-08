package edu.xored.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@RestController
public class UserController {
    private static String getNameCommand = "git config user.name";
    private static String getEmailCommand = "git config user.email";

    String getOutput (String command) {
        Process theProcess;
        // TODO: How to work with exceptions? How to log them?
        try {
            theProcess = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GitNotFoundException();
        }
        try (BufferedReader inStream = new BufferedReader(new InputStreamReader( theProcess.getInputStream() ))) {
            return inStream.readLine();
        } catch(IOException e) {
            e.printStackTrace();
            throw new ReceiveOutputException();
        }
    }

    @GetMapping(value = "/api/user")
    public User getUser() {
        String name = getOutput(getNameCommand);
        String email = getOutput(getEmailCommand);
        return new User(name, email);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Git not found")
    private class GitNotFoundException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "An error occurred while receiving the output")
    private class ReceiveOutputException extends RuntimeException {
    }
}
