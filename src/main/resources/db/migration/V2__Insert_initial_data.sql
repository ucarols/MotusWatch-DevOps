INSERT INTO usuarios (nome, telefone, cpf, email, senha, perfil) VALUES
('Admin Sistema', '11999999999', '12345678901', 'admin@motuswatch.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyVqLx6/3j8J8J8J8J8J8J8J8J8J', 'ADMIN'),
('Caroline de Oliveira', '11958833399', '01987654321', 'caroline@gmail.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyVqLx6/3j8J8J8J8J8J8J8J8J8J', 'USER'),
('Giulia Correa Camillo', '11974296677', '11122233344', 'giulia@gmail.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKyVqLx6/3j8J8J8J8J8J8J8J8J8J', 'USER');

INSERT INTO motos (placa, modelo, area, observacao, data_entrada, usuario_id) VALUES
('ABC1234', 'MOTTU_POP', 'VERDE', 'Moto Ok', CURRENT_TIMESTAMP, 2),
('DEF5678', 'MOTTU_E', 'AMARELO', ' troca de óleo', CURRENT_TIMESTAMP, 3),
('GHI9012', 'MOTTU_SPORT', 'VERMELHO', 'Problema no motor', CURRENT_TIMESTAMP, 2),
('JKL3456', 'MOTTU_POP', 'ROXO', 'sem placa', CURRENT_TIMESTAMP, 3);
