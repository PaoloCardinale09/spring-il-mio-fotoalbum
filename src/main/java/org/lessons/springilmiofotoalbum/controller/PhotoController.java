package org.lessons.springilmiofotoalbum.controller;

import jakarta.validation.Valid;
import org.lessons.springilmiofotoalbum.messages.AlertMessage;
import org.lessons.springilmiofotoalbum.messages.AlertMessageType;
import org.lessons.springilmiofotoalbum.model.Photo;
import org.lessons.springilmiofotoalbum.repository.CategoryRepository;
import org.lessons.springilmiofotoalbum.repository.PhotoRepository;
import org.lessons.springilmiofotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {
    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    CategoryRepository categoryRepository;


    @GetMapping
    public String list(
            @RequestParam(name = "keyword", required = false) String searchingString, Model model) { // è la mappa di attributi che il controller passa alla view
        List<Photo> photos;
        if (searchingString == null || searchingString.isBlank()) {
            photos = photoRepository.findAll();
        } else {
            photos = photoRepository.findByTitleContainingIgnoreCase(searchingString);
        }
        // passo lista di Photo alla view
        model.addAttribute("photosList", photos);
        model.addAttribute("searchInput", searchingString == null ? "" : searchingString);
        // restituisco il nome del template alla view
        return "/photos/list";
    }


    // prendere photo per id
    @GetMapping("/{id}")
    public String photoDetail(@PathVariable("id") Integer photoId, Model model) {
        // cerca db dettaglio con id

        Photo photo = getPhotoById(photoId);
        // passa la Photo alla view
        model.addAttribute("photo", photo);
        //ritorna template
        return "/photos/show";
    }


    @GetMapping("/create")
    public String create(Model model) {
        // aggiungo al model l'attributo book contenente un Book vuoto
        model.addAttribute("photo", new Photo());
        // aggiungo al model la lista delle categories per popolare le checkbox
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "/photos/edit"; // template con form di creazione di un book
    }

    // controller che gestisce la post del form con i dati della nuova Photo
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("photo") Photo formPhoto,
                        BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        // i dati della photo sono dentro formPhoto
        // verifico se ci sono errori
        // verifico se in validazione ci sono stati errori
        if (bindingResult.hasErrors()) {
            // ci sono stati errori
            // aggiungo al model la lista delle categorie per popolare le checkbox
            model.addAttribute("categoryList", categoryRepository.findAll());
            return "/photos/edit"; // template unico per create e edit
        }
        // setto timestamp di creazione
//        formPhoto.setCreatedAt(LocalDateTime.now());
        // metodo save salve se non esiste altrimenti fa update
        photoRepository.save(formPhoto);


        // se tutto va a buon fine ritorna la lista delle pizze
        redirectAttributes.addFlashAttribute("message",
                new AlertMessage(AlertMessageType.SUCCESS, "Photo created!"));
        return "redirect:/photos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        // verifica se esiste photo con quel id
//        Optional<Photo> result = photoRepository.findById(id);
//        // se non esiste error 404
//        if (result.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo with id " + id + "not found");
//        }
        Photo photo = getPhotoById(id);
        //recupero dati da DB
        // aggiungo photo al model
        model.addAttribute("photo", photo);
        // aggiungo al model la lista degli ingrerdienti per popolare le checkbox
        model.addAttribute("categoryList", categoryRepository.findAll());
        //restituisco il template edit
        return "photos/edit";

    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id,
                         @Valid @ModelAttribute("photo") Photo formPhoto,
                         BindingResult bindingresult, RedirectAttributes redirectAttributes, Model model) {
        // cerco photo da id
        Photo photoToEdit = getPhotoById(id); // vecchia versione di photo
        // valido formPhoto
        if (bindingresult.hasErrors()) {
            // aggiungo al model la lista degli ingrerdienti per popolare le checkbox
            model.addAttribute("categoryList", categoryRepository.findAll());
            // se ci sono errori ritorno il template con il form
            return "/photos/edit";
        }
        // trasferisco su formPhoto tutti i valori dei campi che non sono presenti nel form (altrimenti li perdo)
        formPhoto.setId(photoToEdit.getId());
//        formPhoto.setCreatedAt(photoToEdit.getCreatedAt());
        // salvo i dati
        photoRepository.save(formPhoto);
        redirectAttributes.addFlashAttribute("message",
                new AlertMessage(AlertMessageType.SUCCESS, "Photo updated!"));
        return "redirect:/photos";
    }

    // METHODS DELETE

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Photo photoToDelete = getPhotoById(id);
        // cancello la photo
        photoRepository.delete(photoToDelete);
        // add messaggio di successo come flashattribute
        redirectAttributes.addFlashAttribute("message",
                new AlertMessage(AlertMessageType.SUCCESS, photoToDelete.getTitle() + " deleted!"));
        return "redirect:/photos";

    }


    //UTILITY METHODS
    // metodo per selezionare una photo da DB o eccezione
    private Photo getPhotoById(Integer id) {
        // verificare se c'è una photo con quell' id
        Optional<Photo> result = photoRepository.findById(id);
        // se non esiste error 404
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo with id " + id + "not found");
        }
        return result.get();
    }
}
