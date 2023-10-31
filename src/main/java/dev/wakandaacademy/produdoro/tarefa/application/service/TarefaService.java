package dev.wakandaacademy.produdoro.tarefa.application.service;

import java.util.List;
import java.util.UUID;

import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaDetalhadoResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaIdResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;

public interface TarefaService {
	TarefaIdResponse criaNovaTarefa(TarefaRequest tarefaRequest);

	Tarefa detalhaTarefa(String usuario, UUID idTarefa);

	List<TarefaDetalhadoResponse> buscaTarefaPorUsuario(String usuario, UUID idUsuario);

	void deletaTarefa(String usuario, UUID idTarefa);

	void imcrementaPomodoro(String usuario, UUID idTarefa);

	void concluiTarefa(String usuario, UUID idTarefa);
}
