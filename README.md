# MotusWatch - Sistema de Gestão de Motos

## Descrição
O MotusWatch é um sistema de gestão de motos que utiliza classificação por cores para otimizar a organização e controle das motos dentro do pátio da Mottu.

## Funcionalidades

###  Classificação por Cores
- **Verde**: Pronta para uso (sem limite de tempo)
- **Amarelo**: Reparos rápidos (limite de 15 minutos)
- **Vermelho**: Reparos graves (prioridade alta)
- **Roxo**: Problemas administrativos (até resolução)

###  Autenticação e Autorização
- Sistema de login com Spring Security
- Dois tipos de usuário:
  - **ADMIN**: Acesso completo (criar, editar, excluir)
  - **USER**: Acesso de leitura apenas

###  Funcionalidades Principais
- Gestão de motos (CRUD completo)
- Gestão de usuários
- Controle de movimentações
- Dashboard com estatísticas
- Relatórios por área
- Sistema de alertas por tempo de permanência

## Tecnologias Utilizadas

### Backend
- **Spring Boot**
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **Thymeleaf** - Templates para interface web
- **Flyway** - Versionamento do banco de dados
- **H2 Database** - Banco de dados em memória
- **Lombok** - Redução de código boilerplate

### Frontend
- **Bootstrap 5.3.3** - Framework CSS
- **Bootstrap Icons** - Ícones
- **Thymeleaf** - Engine de templates


## Configuração do Banco de Dados

### Migrações Flyway
O projeto utiliza Flyway para versionamento do banco de dados com as seguintes migrações:

1. **V1__Create_tables.sql** - Criação das tabelas principais
2. **V2__Insert_initial_data.sql** - Dados iniciais
3. **V3__Add_audit_fields.sql** - Campos de auditoria
4. **V4__Create_indexes.sql** - Índices para otimização

## Como Executar


### Passos
1. Clone o repositório  
2. Execute o comando: `mvn spring-boot:run`
3. Acesse: `http://localhost:8080`

### Usuários de Teste
- **Admin**: `admin` / `admin123`
- **User**: `user` / `user123`

### Integrantes
- Caroline de Oliveira - RM 559123
- Giulia Correa Camillo - RM 554473

## Vídeo
https://youtu.be/3ysJPwuv3PI?si=InNaIS0pMx383iWl
