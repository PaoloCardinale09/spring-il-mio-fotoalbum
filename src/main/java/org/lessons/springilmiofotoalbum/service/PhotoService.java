package org.lessons.springilmiofotoalbum.service;

import org.lessons.springilmiofotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
    @Autowired
    PhotoRepository photoRepository;


    // metodo che restituisce la lista di tutti i libri filtrata o no a seconda del parametro
//    public List<Photo> getAll(Optional<String>) {
//        if (keywordOpt.isEmpty()) {
//            return photoRepository.findAll();
//        } else {
//            return photoRepository.findByTitle(keywordOpt.get());
//        }
//    }
}
