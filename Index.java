
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.border.Border;

public class Index extends JFrame implements ActionListener {

    private JScrollPane rolagem;
    private JLabel lblSistema;
    private JButton btnCadastrar;
    private JButton btnLogin;
    private JSeparator jSeparator1;

    private static Index intanciaUnica;

    public static synchronized Index getInstance() {
        if (intanciaUnica == null) {
            intanciaUnica = new Index();
        }

        return intanciaUnica;
    }

    public Index() {
        super("Sugestão e Devolutias");
        btnLogin = new JButton("Login");
        btnCadastrar = new JButton("Cadastrar");
        lblSistema = new JLabel("Sistema de Devolutivas de Sugestão");
        lblSistema.setFont(new Font("Tahoma", 0, 18));
        jSeparator1 = new JSeparator();

        Container caixa = getContentPane();
        caixa.setLayout(new BorderLayout());

        JPanel pnltop = new JPanel(new GridLayout());
        JPanel pnlright = new JPanel(new FlowLayout());
        JPanel pnlCentro = new JPanel(new FlowLayout());

        pnltop.add(lblSistema);
        pnlright.add(btnLogin);
        pnlright.add(btnCadastrar);
        pnltop.add(pnlright, BorderLayout.EAST);
        add(jSeparator1);

        add(pnltop, BorderLayout.NORTH);

        btnLogin.addActionListener(this);
        btnCadastrar.addActionListener(this);

        setVisible(true);
        //setResizable(false);
        setSize(955, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(Index.EXIT_ON_CLOSE);
        btnLogin.setFont(new Font("Arial Black", 0, 11));
        btnCadastrar.setFont(new Font("Arial Black", 0, 11));

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnLogin) {
            TelaLogin tela = new TelaLogin();
            tela.setVisible(true);
        } else if (ae.getSource() == btnCadastrar) {
            CadColab telaCad = new CadColab();
            telaCad.setVisible(true);
        }
    }

    public static void main(String[] args) {
        CadUsuario.getInstance();
        Index.getInstance();
    }
}
