package dev.wakandaacademy.produdoro.usuario.application.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioApplicationServiceTest {

	//	@Autowired
    @InjectMocks
    UsuarioApplicationService usuarioApplicationService;

    //	@MockBean
    @Mock
    UsuarioRepository usuarioRepository;
    
    void deveMudarStatusPausaLonga() {
    	
    }

}
