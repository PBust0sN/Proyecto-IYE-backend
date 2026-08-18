# Keycloak Setup Instructions

El servidor Keycloak está desplegado en `auth.sarabia-ti.cl`.

Para configurar los clientes de CrónicoTrack, sigue estos pasos:

1. Ingresar a la consola de administración de Keycloak (admin).
2. Crear un Realm (o usar el existente para CrónicoTrack).
3. Importar roles: `nurse`, `director`.
4. Crear Clientes:
   - Frontend: `cronicotrak-frontend` (Public Client, Valid Redirect URIs: URL de producción del front).
   - Backend: `cronicotrak-backend` (Bearer-only).
5. Crear usuarios de prueba y asignarles los roles.
6. Actualizar las URLs de auth en el frontend y en el backend (application.yaml).
