import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoBD {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {

        }
    }

    public static Connection conectar() throws SQLException {
        String servidor = "localhost";
        String porta = "3306";
        String database = "sys_sugestao";
        String usuario = "root";
        String senha = "mpx458mg9";
        return DriverManager.getConnection("jdbc:mysql://" + servidor + ":" + porta + "/" + database + "?user=" + usuario + "&password=" + senha);
    }

    public static void desconectar(Connection conn) throws SQLException {
        conn.close();
    }

    /*public static void main(String[] args) {
        Connection conn = null;
        ConexaoBD bd = new ConexaoBD();
        try {
            conn = ConexaoBD.conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }*/
}
