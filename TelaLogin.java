
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class TelaLogin extends JFrame implements ActionListener {
    
    private JTextField txtNome, txtSenha;
    private JLabel lblNome, lblSenha;
    private JButton btnOk;
    public String Acesso;
    
    public TelaLogin() {
        txtNome = new JTextField(15);
        txtSenha = new JTextField(15);
        lblNome = new JLabel("Nome");
        lblSenha = new JLabel("Senha");
        btnOk = new JButton("Ok");
        
        Container caixa = getContentPane();
        caixa.setLayout(new BorderLayout());
        
        JPanel pnlSul = new JPanel(new FlowLayout());
        JPanel pnlLinha1 = new JPanel(new FlowLayout());
        JPanel pnlLinha2 = new JPanel(new FlowLayout());
        JPanel pnlCentro = new JPanel(new GridLayout(2, 1));
        
        pnlLinha1.add(lblNome);
        pnlLinha1.add(txtNome);
        pnlLinha2.add(lblSenha);
        pnlLinha2.add(txtSenha);
        pnlCentro.add(pnlLinha1);
        pnlCentro.add(pnlLinha2);
        pnlSul.add(btnOk);
        
        add(pnlSul, BorderLayout.CENTER);
        add(pnlCentro, BorderLayout.NORTH);
        
        btnOk.addActionListener(this);
        
        setVisible(true);
        //setResizable(false);
        setSize(259, 130);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(TelaLogin.DISPOSE_ON_CLOSE);
    }
    
    public boolean consultar(String login, String senha) {
        boolean autenticado = false;
        String sql;
        Connection conn = null;
        try {
            
            ConexaoBD bd = new ConexaoBD();
            conn = bd.conectar();
            
            sql = "SELECT * FROM cad_usuario WHERE email_user=? and senha_user=?";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, senha);
            ResultSet rs;
            rs = ps.executeQuery();
            if (rs.next()) {
                String user = rs.getString("email_user");
                String pass = rs.getString("senha_user");
                Acesso = rs.getString("tipo_user");//Aqui armazeno o acesso ("Inventario" ou "Operacao") na variável publica Acesso declarada no inicio do código
                autenticado = true;
                ps.close();
                
            } else {
                ps.close();
                return autenticado;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
        return autenticado;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnOk) {
            boolean resposta = consultar(txtNome.getText(), txtSenha.getText());//Pego usuario e senha digitados e jogo no metodo consultar para validar
            CadUsuario user = CadUsuario.getInstance();
            user.setEmailUser(txtNome.getText());            
            if (resposta == true) {//Se Usuario e senha estiverem corretos
                if (Acesso.equals("colaborador")) { //Aqui é a variável se for "Inventario" abro a tela RDI
                    JOptionPane.showMessageDialog(this, "Seja Bem Vindo " + Acesso);
                    this.setVisible(false);
                    ConexaoBD bd = new ConexaoBD();
                    Connection conn = null;              
                    try {
                        conn = bd.conectar();
                        user.selectUser(conn);
                        conn.setAutoCommit(false);
                        Index index = Index.getInstance();
                        index.setVisible(false);                        
                        IndexMural muralLogin = new IndexMural(conn);
                        
                    } catch (Exception e) {
                    }
                } else if (Acesso.equals("avaliador")) { //Aqui é a variável se for "Operacao" abro a tela RDIOpe
                    JOptionPane.showMessageDialog(this, "Seja Bem Vindo " + Acesso);
                    //this.setVisible(false);
                    // this.setVisible(false);
                    // formRdiOpe frmrdiOPE = new formRdiOpe();
                    //frmrdiOPE.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuário e(ou) senha incorretos!\nEntre em contato com setor de Inventário");
            }
        }
    }
    
    public static void main(String[] args) {
        TelaLogin telaLogin = new TelaLogin();
    }
}
