package datalink;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import redis.clients.jedis.UnifiedJedis;
import com.mongodb.client.result.DeleteResult;

import java.util.ArrayList;
import java.util.List;

public class CRUD {
    private MongoCollection<Document> collection;
    private UnifiedJedis redis; 
    
    public CRUD(){
        // Assumindo que MongoConnection.getdatabase() retorna o objeto de conexão com o MongoDB
        MongoDatabase db = MongoConnection.getdatabase();
        this.collection = db.getCollection("usuarios");
        
        // Conexão Redis (resolvendo o NullPointerException)
        this.redis = RedisConnection.getConnection(); 
    }
    
    //Create
    public void cadastrarUsuario(String nome,int idade){
        Document doc = new Document("nome", nome).append("idade",idade);
        collection.insertOne(doc);
        
        // Invalida o cache
        redis.del("usuarios");
        System.out.println("Usuário cadastrado! Cache de listagem invalidado.");
    }
    
    //read (com cache para o uso do redis)
    // O método ListarUsuarios é público e retorna uma Lista<Document>
    public List<Document> ListarUsuarios(){ 
        String cache = redis.get("usuarios");
        List<Document> Lista = new ArrayList<>();
        
        if (cache != null){
            System.out.println("Dados vindos do REDIS (Cache Hit)");
            JSONArray arr = new JSONArray(cache);
            // Loop corrigido (i < arr.length())
            for (int i = 0; i < arr.length(); i++ ){ 
                JSONObject obj = arr.getJSONObject(i);
                Lista.add (new Document(obj.toMap()));
            }
        }else{
            System.out.println("Consultando o MONGODB (Cache Miss)");
            MongoCursor<Document> cursor = collection.find().iterator();
            JSONArray jsonArray = new JSONArray();
            
            while (cursor.hasNext()){
                Document doc = cursor.next();
                jsonArray.put(new JSONObject(doc.toJson()));
                Lista.add(doc);
            }
            // Salva no Cache com TTL de 60 segundos
            redis.setex("usuarios", 60, jsonArray.toString());
            System.out.println("Cache de listagem criado no Redis.");
        }
        return Lista;
    }
    
    //Update
    public void atualizarUsuario(String nomeAntigo, String novoNome){
        collection.updateOne(new Document("nome",nomeAntigo),
                             new Document("$set", new Document("nome",novoNome)));
        redis.del("usuarios");
        System.out.println("Usuário atualizado! Cache invalidado.");
    }
    
    //Delete
    public void excluirUsuario(String nome){
        // 1. Executa a exclusão e armazena o resultado
        DeleteResult resultado = collection.deleteOne(new Document ("nome",nome));
        
        // 2. Verifica se algum documento foi afetado
        if (resultado.getDeletedCount() > 0) {
            // Se deletou, invalida o cache e confirma o sucesso
            redis.del("usuarios");
            System.out.println("Usuário excluído! Cache invalidado.");
        } else {
            // Se não deletou (count é 0), o usuário não foi encontrado
            System.out.println("Usuário inexistente, tente novamente!");
        }
}
}