package dev.wakandaacademy.produdoro.tarefa.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import dev.wakandaacademy.produdoro.DataHelper;
import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.tarefa.application.api.EditaTarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaIdResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.application.repository.TarefaRepository;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;

@ExtendWith(MockitoExtension.class)
class TarefaApplicationServiceTest {

	// @Autowired
	@InjectMocks
	TarefaApplicationService tarefaApplicationService;

	// @MockBean
	@Mock
	TarefaRepository tarefaRepository;

	@Mock
	UsuarioRepository usuarioRepository;

	@Test
	void deveRetornarIdTarefaNovaCriada() {
		TarefaRequest request = getTarefaRequest();
		when(tarefaRepository.salva(any())).thenReturn(new Tarefa(request));
		TarefaIdResponse response = tarefaApplicationService.criaNovaTarefa(request);

		assertNotNull(response);
		assertEquals(TarefaIdResponse.class, response.getClass());
		assertEquals(UUID.class, response.getIdTarefa().getClass());
	}

	public TarefaRequest getTarefaRequest() {
		TarefaRequest request = new TarefaRequest("tarefa 1", UUID.randomUUID(), null, null, 0);
		return request;
	}

//	@Test
//	@DisplayName("Should be able to turn on a task")
//	void deveSerCapazDeAtivarTarefa() {
//		Tarefa tarefa = DataHelper.createTarefa();
//		Usuario usuario = DataHelper.createUsuario();
//
//		when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
//		when(tarefaRepository.buscaTarefaPorId(any(UUID.class))).thenReturn(Optional.of(tarefa));
//		tarefaApplicationService.ativaTarefa(usuario.getEmail(), usuario.getIdUsuario(), tarefa.getIdTarefa());
//
//		verify(usuarioRepository, times(1)).buscaUsuarioPorEmail(usuario.getEmail());
//		verify(tarefaRepository, times(1)).buscaTarefaPorId(tarefa.getIdTarefa());
//		verify(tarefa, times(1)).pertenceAoUsuario(usuario);
//		assertEquals(StatusAtivacaoTarefa.ATIVA, tarefa.getStatusAtivacao());
//	}

	@Test
	@DisplayName("Should be able to return an error when idTask invalid")
	void deveSerCapazDeRetornarErroQuandoIdForInvalido() throws APIException {
		Usuario usuario = DataHelper.createUsuario();
		Tarefa tarefa = DataHelper.createTarefa();
//	        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
//	        when(tarefaRepository.buscaTarefaPorId(any(UUID.class))).thenReturn(Optional.of(tarefa));

		APIException ex = Assertions.assertThrows(APIException.class, () -> {
			tarefaApplicationService.ativaTarefa(usuario.getEmail(), usuario.getIdUsuario(), tarefa.getIdTarefa());
		});
		assertEquals("id da tarefa inválido", ex.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusException());
	}

	@Test
	void deveDeletarTarefa() {
		String usuario = "email@email.com";
		UUID idTarefa = UUID.randomUUID();
		Usuario usuarioMock = DataHelper.createUsuario();
		Tarefa tarefaMock = DataHelper.createTarefa();

		when(usuarioRepository.buscaUsuarioPorEmail(usuario)).thenReturn(usuarioMock);
		when(tarefaRepository.buscaTarefaPorId(idTarefa)).thenReturn(Optional.of(tarefaMock));
		tarefaApplicationService.deletaTarefa(usuario, idTarefa);

		verify(usuarioRepository, times(1)).buscaUsuarioPorEmail(usuario);
		verify(tarefaRepository, times(1)).buscaTarefaPorId(idTarefa);
		verify(tarefaRepository, times(1)).deleta(tarefaMock);

	}

	@Test
	void testConcluiTarefa() {
		String usuario = "email@email.com";
		UUID idTarefa = UUID.randomUUID();
		Usuario usuarioMock = DataHelper.createUsuario();
		Tarefa tarefaMock = DataHelper.createTarefa();

		when(usuarioRepository.buscaUsuarioPorEmail(usuario)).thenReturn(usuarioMock);
		when(tarefaRepository.buscaTarefaPorId(idTarefa)).thenReturn(Optional.of(tarefaMock));
		tarefaApplicationService.concluiTarefa(usuario, idTarefa);
	}

	@Test
	@DisplayName("Teste incrementa pomodoro")
	void incrementaPomodoro_idTarefaETokenValido_DeveIncrementarUmPomodoro() {
		// DADO
		Tarefa tarefa = DataHelper.createTarefa();
		UUID idTarefa = tarefa.getIdTarefa();
		Usuario usuario = DataHelper.createUsuario();
		String usuarioEmail = usuario.getEmail();
		// QUANDO
		when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
		when(tarefaRepository.buscaTarefaPorId(any(UUID.class))).thenReturn(Optional.of(tarefa));
		tarefaApplicationService.imcrementaPomodoro(usuarioEmail, idTarefa);
		// ENTÃO
		verify(tarefaRepository, times(1)).salva(any(Tarefa.class));

	}

	@Test
	void deveEditarTarefa() {
		EditaTarefaRequest request = DataHelper.getEditaTarefaRequest();
		Usuario usuario = DataHelper.createUsuario();
		Tarefa tarefa = DataHelper.createTarefa();
		when(usuarioRepository.buscaUsuarioPorEmail(any())).thenReturn(usuario);
		when(tarefaRepository.buscaTarefaPorId(any())).thenReturn(Optional.of(tarefa));
		tarefaApplicationService.editaDescricaoDaTarefa(usuario.getEmail(), tarefa.getIdTarefa(), request);

		verify(usuarioRepository, times(1)).buscaUsuarioPorEmail(usuario.getEmail());
		verify(tarefaRepository, times(1)).buscaTarefaPorId(tarefa.getIdTarefa());
		assertEquals(request.getDescricao(), tarefa.getDescricao());
	}

	@Test
	void naoDeveEditarTarefa() {
		UUID idTarefaInvalido = UUID.fromString("c28932c0-4b44-4192-aaa1-021fd2d8ecef");
		String UsuarioEmail = DataHelper.getEditaTarefaRequest().getDescricao();

		when(tarefaRepository.buscaTarefaPorId(idTarefaInvalido)).thenThrow(APIException.class);
		assertThrows(APIException.class,
				() -> tarefaApplicationService.editaDescricaoDaTarefa(UsuarioEmail, idTarefaInvalido, null));
	}
}