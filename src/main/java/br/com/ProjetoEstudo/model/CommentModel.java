package br.com.ProjetoEstudo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentModel {

	private Integer idComment;
	private Integer idPublicacao;
	private Integer idUsuario;
	private String textoComment;
	private String acaoComment;
}
