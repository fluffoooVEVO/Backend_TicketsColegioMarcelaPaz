package cl.marcelapaz.usuarios.service;

import cl.marcelapaz.usuarios.dto.LoginRequest;
import cl.marcelapaz.usuarios.dto.RegistroRequest;
import cl.marcelapaz.usuarios.dto.UsuarioResponse;
import cl.marcelapaz.usuarios.model.Usuario;
import cl.marcelapaz.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponse registrar(RegistroRequest request) {
        String email = normalizar(request.email());
        if (repository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre().trim());
        usuario.setEmail(email);
        usuario.setPasswordHash(passwordEncoder.encode(request.password()));
        return UsuarioResponse.desde(repository.save(usuario));
    }

    public UsuarioResponse login(LoginRequest request) {
        Usuario usuario = repository.findByEmail(normalizar(request.email()))
                .filter(u -> passwordEncoder.matches(request.password(), u.getPasswordHash()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos"));
        return UsuarioResponse.desde(usuario);
    }

    public UsuarioResponse buscar(Long id) {
        return repository.findById(id)
                .map(UsuarioResponse::desde)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    private String normalizar(String email) {
        return email.trim().toLowerCase();
    }
}
