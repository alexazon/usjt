
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarregaSugestao {

    public CarregaSugestao() {
    }

    public void listarSugestao(Connection conn) {
        ArrayList<Sugestao> lista = buscarSugestaoAvaliador(conn);
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
                + "WHERE st.nome_status = 'Pendente'";

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

    public ArrayList<Sugestao> buscarSugestaoRealizada(Connection conn) {
        String sqlSelect = "SELECT id_sugestao, datacad_sugestao, dataupdate_sugestao, "
                + "titulo_sugestao, txt_sugestao, sug.id_user, sug.id_categoria, st.id_status, cat.nome_categoria "
                + "FROM cad_sugestao sug "
                + "JOIN tab_status st ON sug.id_status = st.id_status "
                + "JOIN cad_categoria cat ON cat.id_categoria = sug.id_categoria "
                + "JOIN cad_usuario user ON sug.id_user = user.id_user "
                + "WHERE st.nome_status = 'Pendente' AND user.id_user = ?";

        ArrayList<Sugestao> lista = new ArrayList<>();
        Usuario userID = Usuario.getInstance();
        try (PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
            stm.setInt(1, userID.getIdUser());
            try (ResultSet rs = stm.executeQuery();) {
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
        } catch (SQLException e1) {
            System.out.print(e1.getStackTrace());
        }
        return lista;
    }

    public ArrayList<Sugestao> buscarSugestaoCategoria(Connection conn, String nomeCategoria) {
        String sqlSelects = "SELECT id_sugestao, datacad_sugestao, dataupdate_sugestao,\n"
                + "                titulo_sugestao, txt_sugestao, id_user, sug.id_categoria, st.id_status, cat.nome_categoria\n"
                + "                FROM cad_sugestao sug\n"
                + "                JOIN tab_status st ON sug.id_status = st.id_status\n"
                + "                JOIN cad_categoria cat ON cat.id_categoria = sug.id_categoria\n"
                + "                WHERE st.nome_status = 'Ativo' AND cat.nome_categoria = '" + nomeCategoria + "'";

        ArrayList<Sugestao> listar = new ArrayList<>();

        try (PreparedStatement stm = conn.prepareStatement(sqlSelects);
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
                listar.add(sugestao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listar;
    }
    
    
    public ArrayList<Sugestao> buscarSugestaoAvaliador(Connection conn) {
        String sqlSelects = "SELECT id_sugestao, datacad_sugestao, dataupdate_sugestao, "
                + "titulo_sugestao, txt_sugestao, user.name_user, sug.id_user, sug.id_categoria, st.id_status, cat.nome_categoria "
                + "FROM cad_sugestao sug "
                + "JOIN tab_status st ON sug.id_status = st.id_status "
                + "JOIN cad_categoria cat ON cat.id_categoria = sug.id_categoria "
                + "JOIN cad_usuario user ON sug.id_user = user.id_user "
                + "WHERE st.nome_status = 'Pendente'";

        ArrayList<Sugestao> listar = new ArrayList<>();

        try (PreparedStatement stm = conn.prepareStatement(sqlSelects);
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
                sugestao.setUserName(rs.getString("name_user"));
                listar.add(sugestao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listar;
    }

    public static void main(String[] args) {
        ConexaoBD bd = new ConexaoBD();
        Connection conn = null;
        try {
            conn = bd.conectar();
            CarregaSugestao carrega = new CarregaSugestao();
            //carrega.buscarSugestaoCategoria(conn, "Estrutura");
            carrega.listarSugestao(conn);

        } catch (SQLException ex) {
            Logger.getLogger(CarregaSugestao.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }
}
