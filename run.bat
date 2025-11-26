@echo off

rem Criar diretório bin se não existir
if not exist bin (
    mkdir bin
)

echo Compilando arquivos Java...

rem Compilar todos os arquivos necessários
javac -d bin -cp "libs/*" ^
src\utils\EnvReader.java ^
src\models\Usuario.java ^
src\models\Aluno.java ^
src\dao\MongoConnection.java ^
src\dao\AlunoDAO.java ^
src\dao\TestAlunoDAO.java

if errorlevel 1 (
    echo.
    echo ERRO NA COMPILACAO!
    pause
    exit /b
)

echo.
echo Execucao iniciada...
echo.

rem Executar a classe principal de testes
java -cp "libs/*;bin" dao.TestAlunoDAO

echo.
pause
