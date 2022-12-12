package br.com.ProjetoEstudo.controller;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import br.com.ProjetoEstudo.model.UsuarioModel;

@RestController
public class UsuarioController {
	@Autowired
	KafkaTemplate<Integer, UsuarioModel> kafkaTemplate;

	@PostMapping(value = "salvarUsuario")
	public ResponseEntity<String> salvarUsuario(@RequestBody UsuarioModel user){
		if (!user.getEmailUsuario().isEmpty() || !user.getSenhaUsuario().isEmpty() || !user.getUserUsuario().isEmpty()){
			user.setAcaoUsuario("salvar");
			ProducerRecord<Integer, UsuarioModel> producerRecord = new ProducerRecord<>("Usuario", null, user);
			kafkaTemplate.send(producerRecord);
			return new ResponseEntity<>(HttpStatusCode.valueOf(200));
		}else {
			return new ResponseEntity<>(HttpStatusCode.valueOf(400));
		}
	}

	@PutMapping(value = "alterarUsuario/{id}")
	public ResponseEntity<String> alterarUsusario(@RequestBody UsuarioModel user, @PathVariable("id") Integer id){
		if (!user.getEmailUsuario().isEmpty() || !user.getSenhaUsuario().isEmpty() || !user.getUserUsuario().isEmpty()){
			user.setIdUsuario(id);
			user.setAcaoUsuario("alterar");
			ProducerRecord<Integer, UsuarioModel> producerRecord = new ProducerRecord<>("Usuario", id, user);
			kafkaTemplate.send(producerRecord);
			return new ResponseEntity<>(HttpStatusCode.valueOf(200));
		}else {
			return new ResponseEntity<>(HttpStatusCode.valueOf(400));
		}
	}

	@DeleteMapping(value = "deletarUsuario/{id}")
	public ResponseEntity<String> deletarUsuario(@PathVariable("id") Integer id){
		UsuarioModel user = new UsuarioModel();
		user.setIdUsuario(id);
		user.setAcaoUsuario("deletar");
		ProducerRecord<Integer, UsuarioModel> producerRecord = new ProducerRecord<>("Usuario", id, user);
		kafkaTemplate.send(producerRecord);
		return new ResponseEntity<>(HttpStatusCode.valueOf(200));
	}
}
