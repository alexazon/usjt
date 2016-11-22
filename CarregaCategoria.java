
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarregaCategoria {

    public CarregaCategoria() {
    }

    public void listarSugestao(Connection conn) {
        ArrayList<Categoria> lista = buscarCategoria(conn);
        for (Categoria categoria : lista) {
            System.out.println(categoria);
        }
    }

    public ArrayList<Categoria> buscarCategoria(Connection conn) {
        String sqlSelect = "SELECT nome_categoria FROM cad_categoria;";

        ArrayList<Categoria> lista = new ArrayList<>();

        try (PreparedStatement stm = conn.prepareStatement(sqlSelect);
                ResultSet rs = stm.executeQuery();) {
            //veja que desta vez foi possivel usar o mesmo try
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setNomeCategoria(rs.getString("nome_categoria"));
                lista.add(categoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void main(String[] args) {
        ConexaoBD bd = new ConexaoBD();
        Connection conn = null;
        try {
            conn = bd.conectar();
            CarregaCategoria carrega = new CarregaCategoria();
            carrega.buscarCategoria(conn);
            carrega.listarSugestao(conn);

        } catch (SQLException ex) {
            Logger.getLogger(CarregaCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
