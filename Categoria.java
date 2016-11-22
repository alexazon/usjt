
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Categoria {

    private int idCategoria;
    private String nomeCategoria;
    private Date dataCategoria;

    public Categoria() {
    }

    public Categoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Categoria(int idCategoria, String nomeCategoria, Date dataCategoria) {
        this.idCategoria = idCategoria;
        this.nomeCategoria = nomeCategoria;
        this.dataCategoria = dataCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public Date getDataCategoria() {
        return dataCategoria;
    }

    //Setters
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public void setDataCategoria(Date dataCategoria) {
        this.dataCategoria = dataCategoria;
    }

    public void carregar(Connection conn) {
        String sqlSelect = "SELECT * FROM cad_categoria WHERE nome_categoria = ?";

        try (PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
            stm.setString(1, getNomeCategoria());
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    setIdCategoria(rs.getInt("id_categoria"));
                    setNomeCategoria(rs.getString("nome_categoria"));
                    setDataCategoria(rs.getDate("datacad_categoria"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e1) {
            System.out.print(e1.getStackTrace());
        }
    }

}
