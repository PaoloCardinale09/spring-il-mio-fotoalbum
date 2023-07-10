package org.lessons.springilmiofotoalbum.controller;

import org.lessons.springilmiofotoalbum.messages.AlertMessage;
import org.lessons.springilmiofotoalbum.messages.AlertMessageType;
import org.lessons.springilmiofotoalbum.model.Message;
import org.lessons.springilmiofotoalbum.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/messages")
public class SendMessageController {
    @Autowired
    MessageRepository messageRepository;

    @GetMapping
    @CrossOrigin
    public String list(Model model) {
        List<Message> messages;
        messages = messageRepository.findAll();

        model.addAttribute("messagesList", messages);
        return "/messages/index";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Message messageToDelete = getMessageById(id);
        // cancello la photo
        messageRepository.delete(messageToDelete);
        // add messaggio di successo come flashattribute
        redirectAttributes.addFlashAttribute("message",
                new AlertMessage(AlertMessageType.SUCCESS, messageToDelete.getEmail() + " deleted!"));
        return "redirect:/messages";

    }

    //UTILITY METHODS
    // metodo per selezionare una photo da DB o eccezione
    private Message getMessageById(Integer id) {
        // verificare se c'è un messaggio con quell' id
        Optional<Message> result = messageRepository.findById(id);
        // se non esiste error 404
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message with id " + id + "not found");
        }
        return result.get();
    }
}
