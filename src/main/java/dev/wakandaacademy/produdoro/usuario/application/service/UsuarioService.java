package dev.wakandaacademy.produdoro.usuario.application.service;

import java.util.UUID;

import dev.wakandaacademy.produdoro.usuario.application.api.UsuarioCriadoResponse;
import dev.wakandaacademy.produdoro.usuario.application.api.UsuarioNovoRequest;

public interface UsuarioService {
	UsuarioCriadoResponse criaNovoUsuario(UsuarioNovoRequest usuarioNovo);

	UsuarioCriadoResponse buscaUsuarioPorId(UUID idUsuario);

	void mudaStatusPausaLonga(String usuarioEmail, UUID idUsuario);

	void mudaStatusPausaCurta(String usuarioEmail, UUID idUsuario);
}
