package dev.wakandaacademy.produdoro.usuario.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import dev.wakandaacademy.produdoro.DataHelper;
import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;

@ExtendWith(MockitoExtension.class)
class UsuarioApplicationServiceTest {

	@InjectMocks
	UsuarioApplicationService usuarioApplicationService;

	@Mock
	UsuarioRepository usuarioRepository;

	@Test
	void UsuarioMudaStatusPausaLongaSucesso() {
		Usuario usuario = DataHelper.createUsuario();

		when(usuarioRepository.buscaUsuarioPorEmail(any())).thenReturn(usuario);
		usuarioApplicationService.mudaStatusPausaLonga(usuario.getEmail(), usuario.getIdUsuario());
		verify(usuarioRepository, times(1)).salva(any());
	}

	@Test
	void UsuarioMudaStatusPausaLongaFalha() {
		Usuario usuario = DataHelper.createUsuario();

		when(usuarioRepository.buscaUsuarioPorEmail(any())).thenReturn(usuario);
		APIException ex = assertThrows(APIException.class,
				() -> usuarioApplicationService.mudaStatusPausaLonga(usuario.getEmail(), UUID.randomUUID()));
		assertEquals(APIException.class, ex.getClass());
		assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusException());
		assertEquals("credencial de autenticação não e valida!", ex.getMessage());
	}

	@Test
	void testaPausaCurta() {
		Usuario usuario = DataHelper.createUsuario();
		when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
		when(usuarioRepository.buscaUsuarioPorId(any())).thenReturn(usuario);
		when(usuarioRepository.salva(any())).thenReturn(usuario);
		usuarioApplicationService.mudaStatusPausaCurta(usuario.getEmail(), usuario.getIdUsuario());

		verify(usuarioRepository, times(1)).buscaUsuarioPorEmail(usuario.getEmail());
		verify(usuarioRepository, times(1)).buscaUsuarioPorId(usuario.getIdUsuario());
		verify(usuarioRepository, times(1)).salva(usuario);
	}

	@Test
	void deveRetornarExceptionAoMudaStatusPausaCurta() {
		when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(DataHelper.createUsuario());
		APIException exception = assertThrows(APIException.class,
				() -> usuarioApplicationService.mudaStatusPausaCurta("qualquer@email.com", UUID.randomUUID()));

		assertEquals("credencial de autenticação não e valida!", exception.getMessage());
		assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusException());
	}
}
