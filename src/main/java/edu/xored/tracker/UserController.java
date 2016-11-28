package edu.xored.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@RestController
@RequestMapping(value = "/api")
public class UserController {
    private static final String getNameCommand = "git config user.name";
    private static final String getEmailCommand = "git config user.email";

    @GetMapping(value = "user")
    public User getUser() {
        String name = getOutput(getNameCommand);
        String email = getOutput(getEmailCommand);
        return new User(name, email);
    }

    private String getOutput (String command) {
        Process theProcess;
        try {
            theProcess = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            // TODO (Card #50): find better approach for exceptions logging
            e.printStackTrace();
            throw new GitNotFoundException();
        }
        try (BufferedReader inStream = new BufferedReader(new InputStreamReader(theProcess.getInputStream()))) {
            return inStream.readLine();
        } catch(IOException e) {
            // TODO (Card #50): find better approach for exceptions logging
            e.printStackTrace();
            throw new ExecutionFailedException();
        }
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Git not found")
    private class GitNotFoundException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "An error occurred while executing command")
    private class ExecutionFailedException extends RuntimeException {
    }
}
