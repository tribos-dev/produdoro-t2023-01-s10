package dev.wakandaacademy.produdoro.tarefa.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
<<<<<<< HEAD
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
=======
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

>>>>>>> dev
import java.util.Optional;
import java.util.UUID;

import dev.wakandaacademy.produdoro.DataHelper;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
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

<<<<<<< HEAD
    @Mock
    UsuarioRepository usuarioRepository;

    @Test
    void deveRetornarIdTarefaNovaCriada() {
        TarefaRequest request = getTarefaRequest();
        when(tarefaRepository.salva(any())).thenReturn(new Tarefa(request));
=======
		TarefaIdResponse response = tarefaApplicationService.criaNovaTarefa(request);
>>>>>>> dev

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

<<<<<<< HEAD
    public TarefaRequest getTarefaRequest() {
        TarefaRequest request = new TarefaRequest("tarefa 1", UUID.randomUUID(), null, null, 0);
        return request;
    }

    @Test
    @DisplayName("Teste incrementa pomodoro")
    void incrementaPomodoro_idTarefaETokenValido_DeveIncrementarUmPomodoro () {
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

=======
		verify(usuarioRepository, times(1)).buscaUsuarioPorEmail(usuario);
		verify(tarefaRepository, times(1)).buscaTarefaPorId(idTarefa);
		verify(tarefaMock, times(1)).pertenceAoUsuario(usuarioMock);
		verify(tarefaMock, times(1)).concluiTarefa();
		verify(tarefaRepository, times(1)).salva(tarefaMock);
	}
>>>>>>> dev
}
