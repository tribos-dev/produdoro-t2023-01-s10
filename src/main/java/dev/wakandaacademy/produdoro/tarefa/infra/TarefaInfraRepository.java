package dev.wakandaacademy.produdoro.tarefa.infra;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.tarefa.application.repository.TarefaRepository;
import dev.wakandaacademy.produdoro.tarefa.domain.StatusAtivacaoTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
@RequiredArgsConstructor
public class TarefaInfraRepository implements TarefaRepository {

	private final TarefaSpringMongoDBRepository tarefaSpringMongoDBRepository;
	private final MongoTemplate mongoTemplate;

	@Override
	public Tarefa salva(Tarefa tarefa) {
		log.info("[inicia] TarefaInfraRepository - salva");
		try {
			tarefaSpringMongoDBRepository.save(tarefa);
		} catch (DataIntegrityViolationException e) {
			throw APIException.build(HttpStatus.BAD_REQUEST, "Tarefa já cadastrada", e);
		}
		log.info("[finaliza] TarefaInfraRepository - salva");
		return tarefa;
	}

	@Override
	public Optional<Tarefa> buscaTarefaPorId(UUID idTarefa) {
		log.info("[inicia] TarefaInfraRepository - buscaTarefaPorId");
		Optional<Tarefa> tarefaPorId = tarefaSpringMongoDBRepository.findByIdTarefa(idTarefa);
		log.info("[finaliza] TarefaInfraRepository - buscaTarefaPorId");
		return tarefaPorId;
	}

	@Override
	public List<Tarefa> buscaTodasTarefasDoUsuario(UUID idUsuario) {
		log.info("[inicia] TarefaInfraRepository -  tarefasDoUsuario");
		List<Tarefa> tarefas = tarefaSpringMongoDBRepository.findAllByIdUsuario(idUsuario);
		log.info("[finaliza] TarefaInfraRepository -  tarefasDoUsuario");
		return tarefas;
	}

	@Override
	public void desativaTarefa(UUID idUsuario) {
		log.info("[inicia] TarefaInfraRepository - desativaTarefa");
		Query query = new Query();
		query.addCriteria(Criteria.where("idUsuario").is(idUsuario));
		Update update = new Update();
		update.set("statusAtivacao", StatusAtivacaoTarefa.INATIVA);
		mongoTemplate.updateMulti(query, update, Tarefa.class);
		log.info("[finaliza] TarefaInfraRepository - desativaTarefa");

	}

	@Override
	public void deleta(Tarefa tarefa) {
		log.info("[inicia] TarefaInfraRepository - deleta");
		tarefaSpringMongoDBRepository.delete(tarefa);
		log.info("[finaliza] TarefaInfraRepository - deleta");
	}

}
