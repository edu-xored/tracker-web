package edu.xored.tracker;

import static java.util.Objects.requireNonNull;

import edu.xored.tracker.IssueRepository.IssueNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/api/issues")
public class IssueController {

    private final IssueRepository issueRepository;
    private final AttachmentService attachmentService;

    public IssueController(
            IssueRepository issueRepository, AttachmentService attachmentService) {
        this.issueRepository = requireNonNull(issueRepository);
        this.attachmentService = requireNonNull(attachmentService);
    }

    @PostMapping
    public Issue postIssue(@RequestBody Issue issue) {
        return issueRepository.save(issue);
    }

    @GetMapping(value = "/{hash}")
    public Issue getIssue(@PathVariable("hash") String hash) {
        assertIssueExists(hash);
        return issueRepository.findOne(hash);
    }

    @PutMapping(value = "/{hash}")
    public Issue putIssue(@PathVariable("hash") String hash,
                          @RequestBody Issue issue) {
        throw new IssueRepository.IssueNotImplementedException();
    }

    @DeleteMapping(value = "/{hash}")
    public void deleteIssue(@PathVariable("hash") String hash) {
        assertIssueExists(hash);
        issueRepository.delete(hash);
    }

    @PatchMapping(value = "/{hash}")
    public Issue patchIssue(@PathVariable("hash") String hash,
                            @RequestBody Issue patchedIssue) {
        assertIssueExists(hash);
        return issueRepository.replace(hash, patchedIssue);
    }

    @PatchMapping()
    public void patchAll() {
        issueRepository.replaceAll();
    }

    @GetMapping
    public Collection<Issue> getIssues(@RequestParam(value = "status", required = false) Issue.Status status) {
        return issueRepository.findAll(status);

    }

    @PostMapping(value = "/{hash}/comments")
    public void postComment(@RequestBody Comment comment,
                            @PathVariable("hash") String hash) {
        assertIssueExists(hash);
        issueRepository.postComment(comment, hash);
    }

    private void assertIssueExists(String hash) throws IssueNotFoundException {
        if (!issueRepository.exists(hash)) {
            throw new IssueNotFoundException(hash);
        }
    }

    @PostMapping(value="/{hash}/upload")
    public ResponseEntity<String> saveAttachment(@PathVariable("hash") String hash,
                                                 @RequestParam("file") MultipartFile file) {
        assertIssueExists(hash);
        attachmentService.saveAttachment(hash, file);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>("Success!", headers, HttpStatus.CREATED);
    }

    @GetMapping(value="/{hash}/download")
    public ResponseEntity<InputStreamResource> getAttachment(@PathVariable("hash") String hash,
                                                             @RequestParam("name") String name) {
        assertIssueExists(hash);
        try (ByteArrayInputStream input =  attachmentService.getAttachment(hash,name)) {
            InputStreamResource out = new InputStreamResource(input);
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(out, headers, HttpStatus.CREATED);
        } catch(IOException e) {
            throw new AttachmentService.AttachmentException(e);
        }
    }

    @ExceptionHandler(IssueNotFoundException.class)
    public String notFoundHandler(IssueNotFoundException ex) {
        return ex.getMessage();
    }
}
