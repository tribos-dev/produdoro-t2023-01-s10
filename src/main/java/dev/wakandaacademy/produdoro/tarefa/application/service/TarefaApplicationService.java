package dev.wakandaacademy.produdoro.tarefa.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.tarefa.application.api.EditaTarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaDetalhadoResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaIdResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.application.repository.TarefaRepository;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class TarefaApplicationService implements TarefaService {
	private final TarefaRepository tarefaRepository;
	private final UsuarioRepository usuarioRepository;

	@Override
	public TarefaIdResponse criaNovaTarefa(TarefaRequest tarefaRequest) {
		log.info("[inicia] TarefaApplicationService - criaNovaTarefa");
		Tarefa tarefaCriada = tarefaRepository.salva(new Tarefa(tarefaRequest));
		log.info("[finaliza] TarefaApplicationService - criaNovaTarefa");
		return TarefaIdResponse.builder().idTarefa(tarefaCriada.getIdTarefa()).build();
	}

	@Override
	public Tarefa detalhaTarefa(String usuario, UUID idTarefa) {
		log.info("[inicia] TarefaApplicationService - detalhaTarefa");
		Usuario usuarioPorEmail = usuarioRepository.buscaUsuarioPorEmail(usuario);
		log.info("[usuarioPorEmail] {}", usuarioPorEmail);
		Tarefa tarefa = tarefaRepository.buscaTarefaPorId(idTarefa)
				.orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Tarefa não encontrada!"));
		tarefa.pertenceAoUsuario(usuarioPorEmail);
		log.info("[finaliza] TarefaApplicationService - detalhaTarefa");
		return tarefa;
	}

	@Override
	public void editaDescricaoDaTarefa(String usuario, UUID idTarefa, EditaTarefaRequest tarefaRequestEditada) {
		log.info("[inicia] TarefaApplicationService - editaDescricaoDaTarefa");
		Tarefa tarefa = detalhaTarefa(usuario, idTarefa);
		tarefa.edita(tarefaRequestEditada);
		tarefaRepository.salva(tarefa);
		log.info("[finaliza] TarefaApplicationService - editaDescricaoDaTarefa");
	}

	@Override
	public List<TarefaDetalhadoResponse> buscaTarefaPorUsuario(String usuario, UUID idUsuario) {
		log.info("[inicia] TarefaApplicationService - buscaTarefaPorUsuario");
		Usuario usuarioPorEmail = usuarioRepository.buscaUsuarioPorEmail(usuario);
		usuarioRepository.buscaUsuarioPorId(idUsuario);
		usuarioPorEmail.validaUsuario(idUsuario);
		List<Tarefa> tarefas = tarefaRepository.buscaTodasTarefasDoUsuario(idUsuario);
		log.info("[finaliza] TarefaApplicationService - buscaTarefaPorUsuario");
		return TarefaDetalhadoResponse.converte(tarefas);
	}

	@Override
	public void deletaTarefa(String usuario, UUID idTarefa) {
		log.info("[inicia] TarefaApplicationService - deletaTarefa");
		Tarefa tarefa = tarefaRepository.buscaTarefaPorId(idTarefa)
				.orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "Tarefa não encontrada!"));
		Usuario usuarioPorEmail = usuarioRepository.buscaUsuarioPorEmail(usuario);
		tarefa.pertenceAoUsuario(usuarioPorEmail);
		tarefaRepository.deleta(tarefa);
		log.info("[finaliza] TarefaApplicationService - deletaTarefa");
	}

	  @Override
	    public void ativaTarefa(String usuarioEmail, UUID idUsuario, UUID idTarefa) {
	        log.info("[inicia] TarefaApplicationService - ativaTarefa");
	        Tarefa tarefa = tarefaRepository.buscaTarefaPorId(idTarefa)
	                .orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "id da tarefa inválido"));

	        Usuario usuario = usuarioRepository.buscaUsuarioPorEmail(usuarioEmail);
	        tarefa.pertenceAoUsuario(usuario);

	        tarefa.ativarTarefa();
	        tarefaRepository.desativaTarefa(idUsuario);

	        tarefaRepository.salva(tarefa);
	        log.info("[finaliza] TarefaApplicationService - ativaTarefa");
	    }

	@Override
	public void imcrementaPomodoro(String usuarioEmail, UUID idTarefa) {
		log.info("[inicia] TarefaApplicationService - imcrementaPomodoro");
		Tarefa tarefa = detalhaTarefa(usuarioEmail, idTarefa);
		tarefa.incrementaPomodoro();
		tarefaRepository.salva(tarefa);
		log.info("[finaliza] TarefaApplicationService - imcrementaPomodoro");
	}

	@Override
	public void concluiTarefa(String usuario, UUID idTarefa) {
		log.info("[inicia] TarefaApplicationService - concluiTarefa");
		Usuario usuarioPorEmail = usuarioRepository.buscaUsuarioPorEmail(usuario);
		log.info("[usuarioPorEmail] {}", usuarioPorEmail);
		Tarefa tarefa = tarefaRepository.buscaTarefaPorId(idTarefa)
				.orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "Tarefa não encontrada!"));
		tarefa.pertenceAoUsuario(usuarioPorEmail);
		tarefa.concluiTarefa();
		tarefaRepository.salva(tarefa);
		log.info("[finaliza] TarefaApplicationService - concluiTarefa");

	}
}
