package br.com.ProjetoEstudo.controller;


import br.com.ProjetoEstudo.model.ImagemMessage;
import br.com.ProjetoEstudo.model.ImagemModel;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;

@RestController
public class ImagemController {

    @Autowired
    KafkaTemplate<Integer, ImagemMessage> kafkaTemplate;

    @PostMapping(value = "salvarImagem",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> salvarImagem(@ModelAttribute ImagemModel imagem) throws IOException {
        if (imagem.getImagem()!=null && imagem.getIdPublicacao()!=null){
            String filename = imagem.getImagem().getOriginalFilename();
            String[] arr = filename.split("\\.");
            String extensao = arr[arr.length-1];
            String base64 = Base64.getEncoder().encodeToString(imagem.getImagem().getBytes());
            ImagemMessage imagemenvia = new ImagemMessage();
            imagemenvia.setExtensaoImagem(extensao);
            imagemenvia.setAcaoImagem("Salvar");
            imagemenvia.setImagem64(base64);
            imagemenvia.setIdPublicacao(imagem.getIdPublicacao());
            ProducerRecord<Integer, ImagemMessage> producerRecord = new ProducerRecord<>("Imagem", null, imagemenvia);
            kafkaTemplate.send(producerRecord);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        }else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
    }

    @DeleteMapping(value = "deletarImagem/{id}")
    public ResponseEntity<?> deletarImagem(@PathVariable("id") Integer id){
        if (id != null){
            ImagemMessage imagem= new ImagemMessage();
            imagem.setIdImagem(id);
            imagem.setAcaoImagem("deletar");
            ProducerRecord<Integer, ImagemMessage> producerRecord = new ProducerRecord<>("Imagem", id, imagem);
            kafkaTemplate.send(producerRecord);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        }else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
    }
}
