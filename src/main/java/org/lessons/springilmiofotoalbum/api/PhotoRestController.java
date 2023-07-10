package org.lessons.springilmiofotoalbum.api;

import jakarta.validation.Valid;
import org.lessons.springilmiofotoalbum.model.Photo;
import org.lessons.springilmiofotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/photos")
public class PhotoRestController {
    // controllo per la risorsa di Photo
    @Autowired
    private PhotoRepository photoRepository;

    //servizio per avere la lista di tutte le photo
    @GetMapping
    public List<Photo> index() {
        return photoRepository.findAll(Sort.by("title"));

    }

    // Singola photo
    @GetMapping("/{id}")
    public Photo get(@PathVariable Integer id) {
        Optional<Photo> photo = photoRepository.findById(id);
        if (photo.isPresent()) {
            return photo.get();

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    // servizio per creare nuova photo che mi arriva come json nel request body
    @PostMapping
    public Photo create(@Valid @RequestBody Photo photo) {

        return photoRepository.save(photo);
    }

    // servizio per cancellare
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        photoRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Photo update(@PathVariable Integer id, @Valid @RequestBody Photo photo) {
        photo.setId(id);
        return photoRepository.save(photo);
    }

    // servizio dimostrativo della paginazione
    @GetMapping("/page")
    public Page<Photo> page(
//            @RequestParam(defaultValue = "3") Integer size,
//            @RequestParam(defaultValue = "0") Integer page
            Pageable pageable) {
        // creo una pageable a a partire da size e page
//        Pageable pageable = PageRequest.of(page, size);
        // restituisco una Page estratta dal DB dal metodo findAll
        return photoRepository.findAll(pageable);
    }
}
