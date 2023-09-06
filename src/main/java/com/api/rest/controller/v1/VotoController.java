package com.api.rest.controller.v1;

import com.api.rest.entity.Voto;
import com.api.rest.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController("VotoControllerV1")
@RequestMapping("/v1")
public class VotoController {

    @Autowired
    private VotoRepository votoRepository;


    @PostMapping("/encuestas/{encuesta_id}/votos")
    public ResponseEntity<?> crearVoto(@PathVariable Long encuesta_id, @RequestBody Voto voto) {
        voto = votoRepository.save(voto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{voto_id}").buildAndExpand(voto.getVoto_id()).toUri());

        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }


    @GetMapping("/encuestas/{encuesta_id}/votos")
    public Iterable<Voto> listarTodosLosVotos(@PathVariable Long encuesta_id) {
        return votoRepository.findByEncuesta(encuesta_id);
    }
}
