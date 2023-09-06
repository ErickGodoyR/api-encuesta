package com.api.rest.controller.v1;

import com.api.rest.dto.OpcionCount;
import com.api.rest.dto.VotoResult;
import com.api.rest.entity.Voto;
import com.api.rest.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("ComputeResultControllerV1")
@RequestMapping("/v1")
public class ComputeResultController {

    @Autowired
    private VotoRepository votoRepository;


    @GetMapping("/calcularResultado")
    public ResponseEntity<?> calcularResultado(@RequestParam Long encuesta_id) {
        VotoResult votoResult = new VotoResult();
        Iterable<Voto> votos = votoRepository.findByEncuesta(encuesta_id);

        //Algoritmo para contar los votos
        int totalVotos = 0;
        Map<Long, OpcionCount> tempMap = new HashMap<Long, OpcionCount>();

        for(Voto v:votos){
            totalVotos ++;

            //Obtener la opcionCount correspondiente a esta opción
            OpcionCount opcionCount = tempMap.get(v.getOpcion().getOpcion_id());
            if(opcionCount == null){
                opcionCount = new OpcionCount();
                opcionCount.setOpcion_id(v.getOpcion().getOpcion_id());
                tempMap.put(v.getOpcion().getOpcion_id(), opcionCount);
            }
            opcionCount.setCount(opcionCount.getCount()+1);
        }

        votoResult.setTotalVotos(totalVotos);
        votoResult.setResults(tempMap.values());

        return new ResponseEntity<>(votoResult, HttpStatus.OK);
    }
}
