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

## Infraestrutura Azure

### Criação dos Serviços via Script PowerShell

O projeto utiliza um script PowerShell (`scripts/scripts-azure.ps1`) para automatizar a criação de toda a infraestrutura no Azure. O script cria os seguintes recursos:

#### Recursos Criados

1. **Resource Group** (`rg-motuswatch`)
  - Grupo de recursos na região Brazil South para organizar todos os componentes

2. **SQL Server** (`sqlmotuswatch`)
  - Servidor de banco de dados SQL Server
  - Usuário administrador: `adminmotuswatch`
  - Localização: Brazil South

3. **SQL Database** (`dbmotuswatch`)
  - Banco de dados SQL Server

4. **App Service Plan** (`motuswatch`)

7 **Web App** (`motuswatch-rm559123`)
  - Integração com GitHub Actions para CI/CD

#### Como Executar o Script

```powershell
# Navegue até o diretório de scripts
cd scripts

# Execute o script (requer Azure CLI instalado e autenticado)
.\scripts-azure.ps1
```

O script aceita parâmetros customizáveis:
```powershell
.\scripts-azure.ps1 `
  -ResourceGroup "rg-motuswatch" `
  -WEBAPP_NAME "motuswatch-rm559123" `
  -LOCATION "brazilsouth" `
  -SqlServerName "sqlmotuswatch" `
  -DbName "dbmotuswatch"
```

### Banco de Dados

#### Configuração SQL Server Azure

- **Servidor**: `sqlmotuswatch.database.windows.net`
- **Banco de dados**: `dbmotuswatch`
- **Porta**: 1433


### Processo de Deploy

O deploy é totalmente automatizado via **GitHub Actions** e ocorre em duas etapas:

1. Faça commit e push das alterações para a branch `main`:
```bash
git add .
git commit -m "Suas alterações"
git push origin main
```

2. O GitHub Actions será acionado automaticamente
3. Acompanhe o progresso na aba "Actions" do repositório GitHub
4. Após conclusão, a aplicação estará disponível em: `https://motuswatch-rm559123.azurewebsites.net`

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
