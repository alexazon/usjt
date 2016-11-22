
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sugestao {

    private int idSugestao;
    private Date dataCadSugestao;
    private Date dataUpdateSugestao;
    private String titulo;
    private String textSugestao;
    private String categoria;
    private int idUser;
    private int idCategoria;
    private int idStatus;
    private String userName;

    public Sugestao() {
    }

    public Sugestao(int idSugestao, Date dataCadSugestao, Date dataUpdateSugestao, String titulo, String textSugestao, String categoria, int idUser, int idCategoria, int idStatus, String userName) {
        this.idSugestao = idSugestao;
        this.dataCadSugestao = dataCadSugestao;
        this.dataUpdateSugestao = dataUpdateSugestao;
        this.titulo = titulo;
        this.categoria = categoria;
        this.textSugestao = textSugestao;
        this.idUser = idUser;
        this.idCategoria = idCategoria;
        this.idStatus = idStatus;
        this.userName = userName;
    }

    public Sugestao(Date dataCadSugestao, String titulo, String textSugestao, int idUser, int idCategoria) {
        this.dataCadSugestao = dataCadSugestao;
        this.titulo = titulo;
        this.textSugestao = textSugestao;
        this.idUser = idUser;
        this.idCategoria = idCategoria;
    }

    Sugestao(String toString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

// Getters
    public int getIdSugestao() {
        return idSugestao;
    }
    public String getUserName(){
        return userName;
    }

    public String getCategoria() {
        return categoria;
    }

    public Date getDataCadSugestao() {
        return dataCadSugestao;
    }

    public Date getDataUpdateSugestao() {
        return dataUpdateSugestao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTextSugestao() {
        return textSugestao;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public int getIdStatus() {
        return idStatus;
    }
//Stters 
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setIdSugestao(int idSugestao) {
        this.idSugestao = idSugestao;
    }

    public void setDataCadSugestao(Date dataCadSugestao) {
        this.dataCadSugestao = dataCadSugestao;
    }

    public void setDataUpdateSugestao(Date dataUpdateSugestao) {
        this.dataUpdateSugestao = dataUpdateSugestao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setTextSugestao(String textSugestao) {
        this.textSugestao = textSugestao;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public void insertSugestao(Connection conn) {
        String sqlInsert = "INSERT INTO cad_sugestao (datacad_sugestao, titulo_sugestao, "
                + "txt_sugestao, id_user, id_categoria, id_status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
            stm.setDate(1, this.getDataCadSugestao());
            stm.setString(2, this.getTitulo());
            stm.setString(3, this.getTextSugestao());
            stm.setInt(4, this.getIdUser());
            stm.setInt(5, this.getIdCategoria());
            stm.setInt(6, this.getIdStatus());
            stm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/*public void incluir(Connection conn) {
        String sqlInsert = "INSERT INTO cliente(id, nome, fone) VALUES (?, ?, ?)";

        try (PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
            stm.setInt(1, getIdCliente());
            stm.setString(2, getNome());
            stm.setString(3, getFone());
            stm.execute();
            //incluir os pedidos
            for (Sugestao pedido : pedidos) {
                pedido.incluir(getIdCliente(), conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.print(e1.getStackTrace());
            }
        }
    }

    public void excluir(Connection conn) {
        String sqlDelete = "DELETE FROM cliente WHERE id = ?";
        try (PreparedStatement stm = conn.prepareStatement(sqlDelete);) {
            //excluir primeiro os pedidos
            for (Sugestao pedido : pedidos) {
                pedido.excluir(conn);
            }
            //depois excluir o cliente
            stm.setInt(1, getIdCliente());
            stm.execute();
            //anular os pedidos e os atributos
            pedidos = new ArrayList<>();
            setNome(null);
            setFone(null);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.print(e1.getStackTrace());
            }
        }
    }

    public void atualizar(Connection conn) {
        String sqlUpdate = "UPDATE cliente SET nome=?, fone=? WHERE id = ?";

        try (PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
            stm.setString(1, getFone());
            stm.setString(2, getNome());
            stm.setInt(3, getIdCliente());

            stm.execute();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.print(e1.getStackTrace());
            }
        }
    }

    public void carregar(Connection conn) {
        String sqlSelect = "SELECT nome, fone FROM cliente WHERE id = ?";

        try (PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
            stm.setInt(1, getIdCliente());
            try (ResultSet rs = stm.executeQuery();) {
                /*
				 * este outro try e' necessario pois nao da' para abrir o
				 * resultsetno anterior uma vez que antes era preciso configurar
				 * o parametrovia setInt; se nao fosse, poderia se fazer tudo no
				 * mesmo try
                 
                if (rs.next()) {
                    setNome(rs.getString("nome"));
                    setFone(rs.getString("fone"));
                    pedidos = carregarSugestaos(conn);
                } else {
                    setNome(null);
                    setFone(null);
                    pedidos = new ArrayList<>();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e1) {
            System.out.print(e1.getStackTrace());
        }
    }*/
