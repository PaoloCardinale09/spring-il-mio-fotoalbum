package org.lessons.springilmiofotoalbum.api;

import jakarta.validation.Valid;
import org.lessons.springilmiofotoalbum.model.Message;
import org.lessons.springilmiofotoalbum.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/messages")
public class SendMessageRestController {
    @Autowired
    private MessageRepository messageRepository;

    @PostMapping
    public ResponseEntity<?> createMessage(@Valid @RequestBody Message message, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid message data");
        }

        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }
}
