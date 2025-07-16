package org.iplacex.proyectos.discografia.Discos;

import java.util.List;
import java.util.Optional;

import org.iplacex.proyectos.discografia.Artistas.IArtistaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepo;

    @Autowired
    private IArtistaRepository artistaRepo;

    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {
        if (!artistaRepo.existsById(disco.idArtista)) {
            return new ResponseEntity<>("Artista no existe", null, HttpStatus.BAD_REQUEST);
        }

        Object res = discoRepo.insert(disco);
        return new ResponseEntity<>(res, null, HttpStatus.CREATED);
    }

    @GetMapping(value = "/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        return new ResponseEntity<>(discoRepo.findAll(), null, HttpStatus.OK);
    }

    @GetMapping(value = "/disco/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable("id") String id) {
        Optional<Disco> optDisco = discoRepo.findById(id);

        if (!optDisco.isPresent()) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optDisco.get(), null, HttpStatus.OK);
    }

    @GetMapping(value = "/artista/{id}/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String id) {
        List<Disco> discos = discoRepo.findDiscosByIdArtista(id);
        return new ResponseEntity<>(discos, null, HttpStatus.OK);
    }
}