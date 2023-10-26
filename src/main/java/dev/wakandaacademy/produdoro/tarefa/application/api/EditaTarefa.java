package dev.wakandaacademy.produdoro.tarefa.application.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Value;

@Value
public class EditaTarefa {

	@NotBlank
	@Size(message = "Campo descrição tarefa não pode estar vazio", max = 255, min = 3)
	private String descricao;

}
