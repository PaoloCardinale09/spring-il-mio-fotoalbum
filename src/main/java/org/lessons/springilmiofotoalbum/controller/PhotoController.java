package org.lessons.springilmiofotoalbum.controller;

import org.lessons.springilmiofotoalbum.model.Photo;
import org.lessons.springilmiofotoalbum.repository.PhotoRepository;
import org.lessons.springilmiofotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {
    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoRepository photoRepository;


    @GetMapping
    public String list(
            @RequestParam(name = "keyword", required = false) String searchingString, Model model) { // è la mappa di attributi che il controller passa alla view
        List<Photo> photos;
        if (searchingString == null || searchingString.isBlank()) {
            photos = photoRepository.findAll();
        } else {
            photos = photoRepository.findByTitleContainingIgnoreCase(searchingString);
        }
        // passo lista di pizza alla view
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
        // passa la pizza alla view
        model.addAttribute("photo", photo);
        //ritorna template
        return "/photos/show";
    }


    //UTILITY METHODS
    // metodo per selezionare una pizza da DB o eccezione
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
