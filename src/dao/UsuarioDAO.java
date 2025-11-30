package dao;

import com.mongodb.client.MongoCollection;
import models.Aluno;
import models.Professor;
import models.Usuario;
import org.bson.Document;

public class UsuarioDAO {

    private static MongoCollection<Document> colUsuarios() {
        return MongoConnection.getDatabase("escola").getCollection("usuarios");
    }

    private static MongoCollection<Document> colAlunos() {
        return MongoConnection.getDatabase("escola").getCollection("alunos");
    }

    private static MongoCollection<Document> colProfessores() {
        return MongoConnection.getDatabase("escola").getCollection("professores");
    }

    // ==================================
    // CADASTRAR USUÁRIO + ALUNO/PROF
    // ==================================
    public static void cadastrarUsuario(Usuario u) {

        // --- Documento base do usuário ---
        Document usuarioDoc = new Document()
                .append("idUsuario", u.getId())
                .append("nome", u.getNome())
                .append("cpf", u.getCpf())
                .append("email", u.getEmail())
                .append("senha", u.getSenha());

        if (u instanceof Aluno a) {

            // 1. Adicionar tipo no documento
            usuarioDoc.append("tipo", "aluno");

            // 2. Inserir na coleção de login
            colUsuarios().insertOne(usuarioDoc);

            // 3. Criar também na coleção alunos
            Document alunoDoc = new Document()
                    .append("idUsuario", a.getId())
                    .append("nome", a.getNome())
                    .append("cpf", a.getCpf())
                    .append("email", a.getEmail())
                    .append("senha", a.getSenha())
                    .append("matricula", a.getMatricula())
                    .append("nomeResp", a.getNomeResp());

            colAlunos().insertOne(alunoDoc);
        }

        else if (u instanceof Professor p) {

            usuarioDoc.append("tipo", "professor");

            colUsuarios().insertOne(usuarioDoc);

            Document profDoc = new Document()
                    .append("idUsuario", p.getId())
                    .append("nome", p.getNome())
                    .append("cpf", p.getCpf())
                    .append("email", p.getEmail())
                    .append("senha", p.getSenha())
                    .append("numeroCNDB", p.getNumeroCNDB())
                    .append("coordenador", p.getCoordenador());

            colProfessores().insertOne(profDoc);
        }
    }

    // ==================================
    // LOGIN
    // ==================================
    public static Usuario login(String email, String senha) {

        Document filtro = new Document("email", email).append("senha", senha);

        Document user = colUsuarios().find(filtro).first();

        if (user == null)
            return null;

        String tipo = user.getString("tipo");
        int id = user.getInteger("idUsuario");

        if ("aluno".equals(tipo)) {

            Document a = colAlunos().find(new Document("idUsuario", id)).first();

            return new Aluno(
                    a.getInteger("idUsuario"),
                    a.getString("nome"),
                    a.getString("cpf"),
                    a.getString("email"),
                    a.getString("senha"),
                    a.getInteger("matricula"),
                    a.getString("nomeResp")
            );
        }

        if ("professor".equals(tipo)) {

            Document p = colProfessores().find(new Document("idUsuario", id)).first();

            return new Professor(
                    p.getInteger("idUsuario"),
                    p.getString("nome"),
                    p.getString("cpf"),
                    p.getString("email"),
                    p.getString("senha"),
                    p.getString("numeroCNDB"),
                    p.getBoolean("coordenador")
            );
        }

        return null;
    }
}
