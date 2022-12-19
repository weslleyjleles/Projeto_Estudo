package br.com.ProjetoEstudo.controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ProjetoEstudo.model.CommentModel;

@RestController
public class CommentController {
	@Autowired
	KafkaTemplate<Integer, CommentModel> kafkaTemplate;

	@PostMapping("/salvarComentario")
	public ResponseEntity<CommentModel> salvarComentario(@RequestBody CommentModel comment) {
		if (!comment.getTextoComment().isEmpty() && comment.getIdUsuario() != null
				&& comment.getIdPublicacao() != null) {
			comment.setAcaoComment("salvar");
			ProducerRecord<Integer, CommentModel> producerRecord = new ProducerRecord<>("Comentario", null, comment);
			kafkaTemplate.send(producerRecord);
			return new ResponseEntity<>(HttpStatusCode.valueOf(200));
		} else {
			return new ResponseEntity<>(HttpStatusCode.valueOf(400));
		}

	}

	@PutMapping("alterarComentario/{id}")
	public ResponseEntity<CommentModel> alterarComentario(@RequestBody CommentModel comment) {
		if (!comment.getTextoComment().isEmpty() && comment.getIdComment() != null) {
			comment.setAcaoComment("alterar");
			ProducerRecord<Integer, CommentModel> producerRecord = new ProducerRecord<>("Comentario", null, comment);
			kafkaTemplate.send(producerRecord);
			return new ResponseEntity<>(HttpStatusCode.valueOf(200));
		} else {
			return new ResponseEntity<>(HttpStatusCode.valueOf(400));
		}

	}

	@DeleteMapping(value = "/deletarComentario/{id}")
	public ResponseEntity<String> deletarComentario(@PathVariable Integer id) {
		if (id != null) {
			CommentModel comment = new CommentModel();
			comment.setIdComment(id);
			;
			comment.setAcaoComment("deletar");
			ProducerRecord<Integer, CommentModel> producerRecord = new ProducerRecord<>("Comentario", id, comment);
			kafkaTemplate.send(producerRecord);
			return new ResponseEntity<>(HttpStatusCode.valueOf(200));
		} else {
			return new ResponseEntity<>(HttpStatusCode.valueOf(400));
		}

	}
}