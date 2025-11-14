import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnex{

    public static void main(String[] args) throws Exception {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USERNAME");
        String pass = System.getenv("DB_PASSWORD");

        if (url == null || user == null || pass == null) {
            throw new IllegalArgumentException("Une variable d'environnement est manquante !");
        }

        Connection conn = DriverManager.getConnection(url, user, pass);
        System.out.println("Connexion réussie !");
        conn.close();
    }
}