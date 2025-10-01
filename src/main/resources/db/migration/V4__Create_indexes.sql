CREATE INDEX IF NOT EXISTS idx_usuarios_cpf ON usuarios(cpf);
CREATE INDEX IF NOT EXISTS idx_usuarios_email ON usuarios(email);
CREATE INDEX IF NOT EXISTS idx_usuarios_perfil ON usuarios(perfil);
CREATE INDEX IF NOT EXISTS idx_usuarios_ativo ON usuarios(ativo);

CREATE INDEX IF NOT EXISTS idx_motos_usuario ON motos(usuario_id);
CREATE INDEX IF NOT EXISTS idx_motos_data_entrada ON motos(data_entrada);
CREATE INDEX IF NOT EXISTS idx_motos_ativo ON motos(ativo);

