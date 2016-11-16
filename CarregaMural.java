
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarregaMural {
    public CarregaMural() {
    }

    public void listarSugestao(Connection conn) {
        ArrayList<Sugestao> lista = buscarSugestao(conn);
        for (Sugestao sugestao : lista) {
            System.out.println(sugestao);
        }
    }

    public ArrayList<Sugestao> buscarSugestao(Connection conn) {
        String sqlSelect = "SELECT id_sugestao, datacad_sugestao, dataupdate_sugestao, "
                + "titulo_sugestao, txt_sugestao, id_user, sug.id_categoria, st.id_status, cat.nome_categoria "
                + "FROM cad_sugestao sug "
                + "JOIN tab_status st ON sug.id_status = st.id_status "
                + "JOIN cad_categoria cat ON cat.id_categoria = sug.id_categoria "
                + "WHERE st.nome_status = 'Ativo'";

        ArrayList<Sugestao> lista = new ArrayList<>();

        try (PreparedStatement stm = conn.prepareStatement(sqlSelect);
                ResultSet rs = stm.executeQuery();) {
            //veja que desta vez foi possivel usar o mesmo try
            while (rs.next()) {
                Sugestao sugestao = new Sugestao();
                sugestao.setIdSugestao(rs.getInt("id_sugestao"));
                sugestao.setDataCadSugestao(rs.getDate("datacad_sugestao"));
                sugestao.setDataUpdateSugestao(rs.getDate("dataupdate_sugestao"));
                sugestao.setTitulo(rs.getString("titulo_sugestao"));
                sugestao.setCategoria(rs.getString("nome_categoria"));
                sugestao.setTextSugestao(rs.getString("txt_sugestao"));
                sugestao.setIdUser(rs.getInt("id_user"));
                sugestao.setIdCategoria(rs.getInt("id_categoria"));
                sugestao.setIdStatus(rs.getInt("id_status"));
                lista.add(sugestao);
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
            CarregaMural carrega = new CarregaMural();
            carrega.buscarSugestao(conn);
            carrega.listarSugestao(conn);

        } catch (SQLException ex) {
            Logger.getLogger(CarregaMural.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
