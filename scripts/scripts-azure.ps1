Param(
    [string]$ResourceGroup = "rg-motuswatch",
    [string]$WEBAPP_NAME="motuswatch-rm559123",
    [string]$APP_SERVICE_PLAN="motuswatch",
    [string]$LOCATION="brazilsouth",
    [string]$RUNTIME="JAVA:17-java17",
    [string]$GITHUB_REPO_NAME="ucarols/MotusWatch-DevOps",
    [string]$BRANCH="main",
    [string]$APP_INSIGHTS_NAME="ai-motuswatch",
    [string]$SqlServerName = "sqlmotuswatch",
    [string]$DbName = "dbmotuswatch",
    [string]$AdminUser = "adminmotuswatch",
    [string]$AdminPass = "m0tvsw4tc7@2025-10",
    [switch]$AllowAzureServices = $true,
    [switch]$AllowClientIP = $true,
    [string]$Plan = ""
)

Write-Host "==> Criando Grupo de recursos "
az group create --name $ResourceGroup --location "$LOCATION"


Write-Host "==> Criando SQL Server $SqlServerName "
az sql server create `
  -g $ResourceGroup `
  -n $SqlServerName `
  -u $AdminUser `
  -p $AdminPass `
  -l $LOCATION | Out-Null

Write-Host "==> Criando Database $DbName "
az sql db create `
  -g $ResourceGroup `
  -s $SqlServerName `
  -n $DbName `
  --service-objective S0 `
  --backup-storage-redundancy Local | Out-Null

if ($AllowAzureServices) {
    Write-Host "==> Liberando Azure Services (0.0.0.0)"
    az sql server firewall-rule create `
    -g $ResourceGroup -s $SqlServerName `
    -n AllowAzureServices `
    --start-ip-address 0.0.0.0 `
    --end-ip-address 0.0.0.0 | Out-Null
}

if ($AllowClientIP) {
    $ip = (Invoke-RestMethod -Uri "https://api.ipify.org?format=text" -TimeoutSec 10)
    Write-Host "==> Liberando IP do cliente: $ip …"
    az sql server firewall-rule create `
    -g $ResourceGroup -s $SqlServerName `
    -n AllowMyIP `
    --start-ip-address $ip `
    --end-ip-address $ip | Out-Null
}

# Monta JDBC
$serverFqdn = "$SqlServerName.database.windows.net"
$jdbc = "jdbc:sqlserver://$serverFqdn"+":1433;database=$DbName;user=$AdminUser@$SqlServerName;password=$AdminPass;encrypt=true;trustServerCertificate=false;loginTimeout=30;"

Write-Host "==> Definindo variáveis de ambiente"
# Define variáveis de ambiente
$env:SPRING_DATASOURCE_URL = $jdbc
$env:SPRING_DATASOURCE_USERNAME = $AdminUser
$env:SPRING_DATASOURCE_PASSWORD = $AdminPass
$env:SPRING_DATASOURCE_DRIVER_CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver"

Write-Host "==> Criando plano de servico "
# Criar o Plano de Serviço
az appservice plan create `
 --name $APP_SERVICE_PLAN `
 --resource-group $ResourceGroup `
 --location "$LOCATION" `
 --sku F1 `
 --is-linux

Write-Host "==> Criando o serviço de aplicativo"
# Criar o Serviço de Aplicativo
az webapp create `
  --name $WEBAPP_NAME `
  --resource-group $ResourceGroup `
  --plan $APP_SERVICE_PLAN `
  --runtime "$RUNTIME"


Write-Host "==> Github actions"
az webapp deployment github-actions add `
 --name $WEBAPP_NAME `
 --resource-group $ResourceGroup `
 --repo $GITHUB_REPO_NAME `
 --branch $BRANCH `
 --login-with-github