package br.com.ProjetoEstudo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeModel {
	private Integer id;                   //usar id no caso de ação de delete
	private Integer idUsuario;
	private Integer idPublicacao;
	private Integer idComentario;
	private String acaoLike;
	
	
	
}
