package dev.wakandaacademy.produdoro.tarefa.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.wakandaacademy.produdoro.DataHelper;
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
}
