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
src\models\Turma.java ^
src\models\Professor.java ^
src\models\Frequencia.java ^
src\dao\MongoConnection.java ^
src\dao\AlunoDAO.java ^
src\dao\TurmaDAO.java ^
src\dao\ProfessorDAO.java ^
src\dao\FrequenciaDAO.java ^
src\dao\UsuarioDAO.java ^
src\views\TurmaView.java ^
src\views\AlunoView.java ^
src\views\ProfessorView.java ^
src\views\FrequenciaView.java ^
src\views\LoginView.java ^
src\views\MenuPrincipalView.java ^
src\service\UsuarioService.java ^
src\Main.java 
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
java -cp "libs/*;bin" Main

echo.
pause
