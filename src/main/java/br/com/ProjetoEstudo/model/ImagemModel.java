package br.com.ProjetoEstudo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImagemModel {
    private Integer idImagem;
    private Integer idPublicacao;
    private MultipartFile imagem;
}
