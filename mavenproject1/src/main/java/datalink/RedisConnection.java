package datalink;

import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;

public class RedisConnection {

    // Usei UnifiedJedis, que é uma classe recomendada para conexões complexas
    private static UnifiedJedis jedis; 

    public static UnifiedJedis getConnection() {
        if (jedis == null) {
            
            //Configurando autenticação
            JedisClientConfig config = DefaultJedisClientConfig.builder()
                    .user("default")
                    .password("root")
                    .build();

            //Conexão com Host e Porta Remotos
            jedis = new UnifiedJedis(
                new HostAndPort("redis-18799.crce216.sa-east-1-2.ec2.redns.redis-cloud.com", 18799),
                config
            );
            
            System.out.println("✅ Conexão Redis inicializada!");
        }
        return jedis;
    }
    
    // Método opcional para fechar a conexão
    public static void closeConnection() {
        if (jedis != null) {
            jedis.close();
            jedis = null;
            System.out.println("❌ Conexão Redis fechada.");
        }
    }
}