package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Usuario;
import iye.grupo2.cronicotrak.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión de usuarios del sistema.
 * Proporciona endpoints para realizar operaciones CRUD sobre la entidad Usuario.
 */
@RestController
@RequestMapping("/api/v1/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    /**
     * Obtiene una lista de todos los usuarios registrados.
     *
     * @return Lista de entidades Usuario.
     */
    @GetMapping
    public List<Usuario> findAll() {
        return service.findAll();
    }

    /**
     * Busca un usuario por su identificador único.
     *
     * @param id Identificador único del usuario.
     * @return ResponseEntity con el usuario encontrado o un estado 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param entity Objeto Usuario que se desea guardar.
     * @return La entidad Usuario guardada, incluyendo su ID generado.
     */
    @PostMapping
    public Usuario save(@RequestBody Usuario entity) {
        return service.save(entity);
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param id Identificador único del usuario a actualizar.
     * @param entity Objeto Usuario con los nuevos datos.
     * @return ResponseEntity con el usuario actualizado o un estado 404 si no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario entity) {
        Usuario updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Elimina un usuario del sistema por su identificador único.
     *
     * @param id Identificador único del usuario a eliminar.
     * @return ResponseEntity con estado 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
