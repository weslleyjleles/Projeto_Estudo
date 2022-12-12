package br.com.ProjetoEstudo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicacaoModel {
    private Integer idPublicacao;
    private Integer idUsuario;
    private String tituloPublicacao;
    private String textoPublicacao;
    private Integer visualisacaoPublicacao;
    private Integer likePublicacao;
    private LocalDateTime dataHoraPublicacao = LocalDateTime.now();
    private String acaoPublicacao;
}
