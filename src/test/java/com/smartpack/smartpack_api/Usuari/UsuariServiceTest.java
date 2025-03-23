package com.smartpack.smartpack_api.Usuari;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.smartpack.dto.UserResponseDto;
import com.smartpack.models.Usuari;
import com.smartpack.repositories.UsuariRepository;
import com.smartpack.services.UsuariService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UsuariServiceTest {

    @Mock
    private UsuariRepository usuariRepository;

    @InjectMocks
    private UsuariService usuariService;

    @Test
    void getUserById_shouldReturnUserResponseDto_whenUserExists() {
        Usuari user = new Usuari();
        user.setId(1L);
        user.setEmail("test@smartpack.com");
        user.setNom("Test");

        Mockito.when(usuariRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDto result = usuariService.getUserById(1L);

        Assertions.assertEquals("test@smartpack.com", result.getEmail());
        Assertions.assertEquals("Test", result.getNom());
    }

    @Test
    void getUserById_shouldThrowException_whenUserNotFound() {
        Mockito.when(usuariRepository.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> {
            usuariService.getUserById(2L);
        });
    }
}
