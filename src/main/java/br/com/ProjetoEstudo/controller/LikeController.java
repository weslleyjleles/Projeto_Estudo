package br.com.ProjetoEstudo.controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ProjetoEstudo.model.LikeModel;

@RestController
public class LikeController {

	@Autowired
	KafkaTemplate<Integer, LikeModel> kafkaTemplate;

	@PostMapping("salvarLike")
	public ResponseEntity<LikeModel> criarLike(@RequestBody LikeModel like) {
		if (like.getIdUsuario() != null && !(like.getIdComentario() != null && like.getIdPublicacao() != null)
				&& !(like.getIdComentario() == null && like.getIdPublicacao() == null)) {

			like.setAcaoLike("salvar");

			ProducerRecord<Integer, LikeModel> producerRecord = new ProducerRecord<>("Like", null, like);
			kafkaTemplate.send(producerRecord);
			return new ResponseEntity<>(HttpStatusCode.valueOf(200));

		}

		else {
			return new ResponseEntity<>(HttpStatusCode.valueOf(400));
		}

	}

	@DeleteMapping(value = "/deletarLike/{id}")
	public ResponseEntity<String> deletarLike(@PathVariable Integer id) {
		if (id != null) {
			LikeModel like = new LikeModel();
			like.setId(id);
			like.setAcaoLike("deletar");
			ProducerRecord<Integer, LikeModel> producerRecord = new ProducerRecord<>("Like", id, like);
			kafkaTemplate.send(producerRecord);
			return new ResponseEntity<>(HttpStatusCode.valueOf(200));
		} else {
			return new ResponseEntity<>(HttpStatusCode.valueOf(400));
		}

	}

}
