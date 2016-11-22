
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;

public class Usuario {

    private int idUser;
    private String nomeUser;
    private String emailUser;
    private String raUser;
    private String senhaUser;
    private Date dasNascUser;
    private String tipoUser;
    private Date dataCadUser;
    private String sexoUser;

    public Usuario() {
    }

    public Usuario(String emailUser) {
        this.emailUser = emailUser;
    }


    public Usuario(int idUser, String nomeUser, String emailUser, String raUser, String senhaUser, Date dasNascUser, String tipoUser, Date dataCadUser, String sexoUser) {
        this.idUser = idUser;
        this.nomeUser = nomeUser;
        this.emailUser = emailUser;
        this.raUser = raUser;
        this.senhaUser = senhaUser;
        this.dasNascUser = dasNascUser;
        this.tipoUser = tipoUser;
        this.dataCadUser = dataCadUser;
        this.sexoUser = sexoUser;
    }

//Getters Metodos de Acessos
    public int getIdUser() {
        return idUser;
    }

    public String getNomeUser() {
        return nomeUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getRaUser() {
        return raUser;
    }

    public String getSenhaUser() {
        return senhaUser;
    }

    public Date getDasNascUser() {
        return dasNascUser;
    }

    public String getTipoUser() {
        return tipoUser;
    }

    public Date getDataCadUser() {
        return dataCadUser;
    }

    public String getSexoUser() {
        return sexoUser;
    }

// Setters Meteodos Modificadores
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public void setRaUser(String raUser) {
        this.raUser = raUser;
    }

    public void setSenhaUser(String senhaUser) {
        this.senhaUser = senhaUser;
    }

    public void setDasNascUser(Date dasNascUser) {
        this.dasNascUser = dasNascUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    public void setDataCadUser(Date dataCadUser) {
        this.dataCadUser = dataCadUser;
    }

    public void setSexoUser(String sexoUser) {
        this.sexoUser = sexoUser;
    }

    public String toString() {
        return "usuario{" + "idUser=" + idUser + ", nomeUser=" + nomeUser + ", emailUser=" + emailUser + ", raUser=" + raUser + ", senhaUser=" + senhaUser + ", dasNascUser=" + dasNascUser + ", tipoUser=" + tipoUser + ", dataCadUser=" + dataCadUser + ", sexoUser=" + sexoUser + '}';
    }
    private static Usuario intanciaUnica;

    public static synchronized Usuario getInstance() {
        if (intanciaUnica == null) {
            intanciaUnica = new Usuario();
        }

        return intanciaUnica;
    }

    public void selectUser(Connection conn) {
        String query = "SELECT * FROM cad_usuario WHERE email_user = ?";
        try (PreparedStatement pst = conn.prepareStatement(query);) {
            pst.setString(1, getEmailUser());
            try (ResultSet rs = pst.executeQuery();) {
                if (rs.next()) {
                    setIdUser(rs.getInt("id_user"));
                    setNomeUser(rs.getString("name_user"));
                    setEmailUser(rs.getString("email_user"));
                    setRaUser(rs.getString("ra_user"));
                    setSenhaUser(rs.getString("senha_user"));
                    setDasNascUser(rs.getDate("datanasc_user"));
                    setTipoUser(rs.getString("tipo_user"));
                    setDataCadUser(rs.getDate("datacad_user"));
                    setSexoUser(rs.getString("sexo_user"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUser(Connection conn) {

        String insert = "INSERT INTO `sys_sugestao`.`cad_usuario` (`name_user`, `email_user`, `ra_user`, `senha_user`, `datanasc_user`, `tipo_user`, `datacad_user`, `sexo_user`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement pst = conn.prepareStatement(insert);) {
            pst.setString(1, this.getNomeUser());
            pst.setString(2, this.getEmailUser());
            pst.setString(3, this.getRaUser());
            pst.setString(4, this.getSenhaUser());
            pst.setDate(5, this.getDasNascUser());
            pst.setString(6, this.getTipoUser());
            pst.setDate(7, this.getDataCadUser());
            pst.setString(8, this.getSexoUser());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateUser(Connection conn) {

        String insert = "UPDATE cad_usuario SET name_user = ?, email_user = ?, ra_user = ?, senha_user = ? WHERE id_user = ?";
        try (PreparedStatement pst = conn.prepareStatement(insert);) {
            Usuario userUpdate = Usuario.getInstance();
            pst.setString(1, userUpdate.getNomeUser());
            pst.setString(2, userUpdate.getEmailUser());
            pst.setString(3, userUpdate.getRaUser());
            pst.setString(4, userUpdate.getSenhaUser());
            pst.setInt(5, userUpdate.getIdUser());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
