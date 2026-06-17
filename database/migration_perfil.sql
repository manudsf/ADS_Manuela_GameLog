-- Migration: Adiciona colunas de perfil ao usuario
ALTER TABLE usuario ADD COLUMN IF NOT EXISTS apelido TEXT;
ALTER TABLE usuario ADD COLUMN IF NOT EXISTS bio TEXT;
ALTER TABLE usuario ADD COLUMN IF NOT EXISTS foto_perfil TEXT;
