-- Insertar usuario de prueba
INSERT INTO usuario (
  id, name, email, password, token, created, modified, last_login, is_active, role
) VALUES (
  '123e4567-e89b-12d3-a456-426614174000',
  'Juan Rodriguez',
  'juan@rodriguez.org',
  'hunter2',
  'tokenhunter2',
  CURRENT_TIMESTAMP,
  CURRENT_TIMESTAMP,
  CURRENT_TIMESTAMP,
  TRUE,
  'USER'
);
-- Insertar teléfono relacionado
INSERT INTO phone (
  number, cityCode, countryCode, usuario_id
) VALUES (
  '1234567',
  '1',
  '57',
  '123e4567-e89b-12d3-a456-426614174000'
);