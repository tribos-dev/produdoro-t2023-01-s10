package dev.wakandaacademy.produdoro.tarefa.application.service;


class TarefaApplicationServiceTest {
<<<<<<< HEAD
=======

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
>>>>>>> dev
}
