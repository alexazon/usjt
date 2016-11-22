import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameIndex extends JFrame implements ActionListener {

    private JScrollPane rolagem;
    private JLabel lblSistema;
    private JButton btnCadastrar;
    private JButton btnLogin;
    private JSeparator jSeparator1;

    private static FrameIndex intanciaUnica;

    public static synchronized FrameIndex getInstance() {
        if (intanciaUnica == null) {
            intanciaUnica = new FrameIndex();
        }

        return intanciaUnica;
    }

    public FrameIndex() {
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
        setDefaultCloseOperation(FrameIndex.EXIT_ON_CLOSE);
        btnLogin.setFont(new Font("Arial Black", 0, 11));
        btnCadastrar.setFont(new Font("Arial Black", 0, 11));
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnLogin) {
            FrameLogin tela = new FrameLogin();
            tela.setVisible(true);
        } else if (ae.getSource() == btnCadastrar) {
            FrameCadColab telaCad = new FrameCadColab();
            telaCad.setVisible(true);
        }
    }

    public static void main(String[] args) {
        Usuario.getInstance();
        FrameIndex.getInstance();
    }
}
