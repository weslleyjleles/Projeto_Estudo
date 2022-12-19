package br.com.ProjetoEstudo.controller;

import br.com.ProjetoEstudo.model.PublicacaoModel;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class PublicacaoController {

    @Autowired
    KafkaTemplate<Integer, PublicacaoModel> kafkaTemplate;

    @PostMapping(value = "salvarPublicacao")
    public ResponseEntity<String> salvarPublicacao(@RequestBody PublicacaoModel pub){
        if (!pub.getTituloPublicacao().isEmpty() && !pub.getTextoPublicacao().isEmpty() && pub.getIdUsuario() != null){
            pub.setAcaoPublicacao("salvar");
            pub.setDataHoraPublicacao(pub.getDataHoraPublicacao());
            pub.setVisualisacaoPublicacao(0);
            ProducerRecord<Integer, PublicacaoModel> producerRecord = new ProducerRecord<>("Postagem", null, pub);
            kafkaTemplate.send(producerRecord);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        }else{
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
    }

    @PutMapping(value = "alterarPublicacao/{id}")
    public ResponseEntity<String> alterarPublicacao(@RequestBody PublicacaoModel pub, @PathVariable("id") Integer id){
        if(id != null){
            pub.setIdPublicacao(id);
            pub.setAcaoPublicacao("alterar");
            ProducerRecord<Integer, PublicacaoModel> producerRecord = new ProducerRecord<>("Postagem", id, pub);
            kafkaTemplate.send(producerRecord);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        }else{
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
    }

    @DeleteMapping(value = "deletarPublicacao/{id}")
    public ResponseEntity<String> deletarPublicacao(@PathVariable("id") Integer id){
        if (id != null){
            PublicacaoModel pub = new PublicacaoModel();
            pub.setIdPublicacao(id);
            pub.setAcaoPublicacao("deletar");
            pub.setDataHoraPublicacao(pub.getDataHoraPublicacao());
            ProducerRecord<Integer, PublicacaoModel> producerRecord = new ProducerRecord<>("Postagem", id, pub);
            kafkaTemplate.send(producerRecord);

            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        }
        else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
    }

}
