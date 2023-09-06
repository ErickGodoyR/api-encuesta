package com.api.rest.controller.v2;

import com.api.rest.entity.Encuesta;
import com.api.rest.exception.ResourceNotFoundException;
import com.api.rest.repository.EncuestaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController("EncuestaControllerV2")
@RequestMapping("/v2")
public class EncuestaController {

    @Autowired
    private EncuestaRepository encuestaRepository;


    @GetMapping("/encuestas")
    public ResponseEntity<Iterable<Encuesta>> listarTotalEncuestas(Pageable pageable){
        Page<Encuesta> encuestas = encuestaRepository.findAll(pageable);
        return new ResponseEntity<>(encuestas, HttpStatus.OK);
    }


    @PostMapping("/encuestas")
    public ResponseEntity<?> crearEncuesta(@Valid @RequestBody Encuesta encuesta) {
        encuesta = encuestaRepository.save(encuesta);

        HttpHeaders httpHeaders = new HttpHeaders();
        URI newEncuestaUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(encuesta.getEncuesta_id()).toUri();
        httpHeaders.setLocation(newEncuestaUri);

        return new ResponseEntity<>(null, httpHeaders,HttpStatus.CREATED);
    }

    @GetMapping("/encuestas/{encuesta_id}")
    public ResponseEntity<?> ObtenerEncuesta(@PathVariable Long encuesta_id){
        verifyEncuesta(encuesta_id);

        Optional<Encuesta> encuesta = encuestaRepository.findById(encuesta_id);

        if(encuesta.isPresent()){
            return new ResponseEntity<>(encuesta, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/encuestas/{encuesta_id}")
    public ResponseEntity<?> actualizarEncuesta(@Valid @RequestBody Encuesta encuesta, @PathVariable Long encuesta_id){
        verifyEncuesta(encuesta_id);

        encuesta.setEncuesta_id(encuesta_id);

        encuestaRepository.save(encuesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/encuestas/{encuesta_id}")
    public ResponseEntity<?> eliminarEncuesta(@PathVariable Long encuesta_id) {
        verifyEncuesta(encuesta_id);

        encuestaRepository.deleteById(encuesta_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    protected void verifyEncuesta(Long encuesta_id){
        Optional<Encuesta> encuesta = encuestaRepository.findById(encuesta_id);

        if(!encuesta.isPresent()){
            throw new ResourceNotFoundException("La encuesta con el ID : " + encuesta_id + " no existe");
        }
    }

}
