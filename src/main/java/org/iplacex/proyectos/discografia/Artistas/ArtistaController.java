package org.iplacex.proyectos.discografia.Artistas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")

public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepo;

    @PostMapping(
        value ="/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista cli){

        Object res= artistaRepo.insert(cli);
        return new ResponseEntity<>(res, null, HttpStatus.CREATED);
    }
    @GetMapping(value = "/artistas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {
        return new ResponseEntity<>(artistaRepo.findAll(), null, HttpStatus.OK);
    }
    @GetMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetClienteRequest(@PathVariable("id") String id){
        Optional<Artista> optArtista = artistaRepo.findById(id);

        if(!optArtista.isPresent()){
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optArtista.get(), null, HttpStatus.OK);
    }
    @PutMapping(
        value = "/artista/{id}/update", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleUpdateArtistaRequest(
        @PathVariable("id") String id, 
        @RequestBody Artista artista){
        if(!artistaRepo.existsById(id)){
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
        artista._id = id;
        Artista temp = artistaRepo.save(artista);

        return new ResponseEntity<>(temp, null, HttpStatus.OK);
    }
    @DeleteMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable("id") String id) {
        if (!artistaRepo.existsById(id)) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        artistaRepo.deleteById(id);

        return new ResponseEntity<>(null, null, HttpStatus.OK);
    }
    
}
