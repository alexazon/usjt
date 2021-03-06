import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;

public class FrameCadColab extends JFrame implements ActionListener {

    private JLabel cadastro;
    private JLabel lblNome, lblEmail, lblRa, lblSenha, lblDataNasc, lblSexo;
    private JTextField txtNome, txtEmail, txtRa;
    private MaskFormatter mascaraData;
    private JFormattedTextField txtDataNasc;
    private JPasswordField passSenha;
    private String[] sexoStrings = {"Selecionar", "Masculino", "Feminino"};
    private JComboBox sexoList;
    private JButton btnCancelar;
    private JButton btnCadastrar;
    private ArrayList<JTextField> textFields;
    private Date java;

    public FrameCadColab() {
        super("Cadastro de Colaborador");
        cadastro = new JLabel("Cadastro - Sistema de Sugestões");
        lblNome = new JLabel("Nome: ");
        txtNome = new JTextField(15);
        lblEmail = new JLabel("Email: ");
        txtEmail = new JTextField(15);
        lblRa = new JLabel("Registro (RA): ");
        txtRa = new JTextField(15);
        lblDataNasc = new JLabel("Data Nascimento:                       ");
        try {
            mascaraData = new MaskFormatter("##/##/####");
            txtDataNasc = new JFormattedTextField(mascaraData);
        } catch (ParseException ex) {
            Logger.getLogger(FrameCadColab.class.getName()).log(Level.SEVERE, null, ex);
        }

        textFields = new ArrayList();
        textFields.add(txtNome);
        textFields.add(txtEmail);

        lblSexo = new JLabel("Sexo:                                              ");
        sexoList = new JComboBox(sexoStrings);
        sexoList.setSelectedIndex(0);
        lblSenha = new JLabel("Senha:           ");
        passSenha = new JPasswordField(15);
        btnCancelar = new JButton("Cancelar");
        btnCadastrar = new JButton("Cadastrar");

        Container caixa = getContentPane();
        caixa.setLayout(new BorderLayout());

        JPanel centro = new JPanel(new FlowLayout());
        JPanel pnlLinha1 = new JPanel(new GridLayout(1, 2));
        JPanel pnlLinha2 = new JPanel(new GridLayout(1, 2));
        JPanel pnlLinha3 = new JPanel(new GridLayout(1, 2));
        JPanel pnlLinha4 = new JPanel(new GridLayout(1, 2));
        JPanel pnlLinha5 = new JPanel(new GridLayout(1, 2));
        JPanel pnlLinha6 = new JPanel(new GridLayout(1, 2));
        JPanel pnlLinha7 = new JPanel(new FlowLayout());
        JPanel pnlCentro = new JPanel(new FlowLayout(0, 10, 5));
        centro.add(cadastro);
        pnlLinha1.add(lblNome);
        pnlLinha1.add(txtNome);

        pnlLinha2.add(lblEmail);
        pnlLinha2.add(txtEmail);

        pnlLinha3.add(lblRa);
        pnlLinha3.add(txtRa);

        pnlLinha4.add(lblDataNasc);
        pnlLinha4.add(txtDataNasc);

        pnlLinha5.add(lblSexo);
        pnlLinha5.add(sexoList);

        pnlLinha6.add(lblSenha);
        pnlLinha6.add(passSenha);

        pnlLinha7.add(btnCadastrar);
        pnlLinha7.add(btnCancelar);

        pnlCentro.add(pnlLinha1);
        pnlCentro.add(pnlLinha2);
        pnlCentro.add(pnlLinha3);
        pnlCentro.add(pnlLinha4);
        pnlCentro.add(pnlLinha5);
        pnlCentro.add(pnlLinha6);

        add(centro, BorderLayout.NORTH);
        add(pnlCentro, BorderLayout.CENTER);
        add(pnlLinha7, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(this);
        btnCancelar.addActionListener(this);

        setVisible(true);
        //setResizable(false);
        txtDataNasc.setMargin(new Insets(0, 22, 0, 22));
        txtDataNasc.setSize(10, 5);
        setSize(390, 270);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(FrameCadColab.DISPOSE_ON_CLOSE);
    }

    public boolean validaForm() { //ARRUMAR
        boolean error;
        int contErros = 0;
        Border borderRed = BorderFactory.createLineBorder(Color.red);
        Border borderBlack = BorderFactory.createLineBorder(Color.black);
        //Validacao de textFields - atributos Pessoa------------------------
        for (JTextField textField : textFields) {
            if (textField.getText() == null || textField.getText().isEmpty()) {
                textField.setBorder(borderRed);
                contErros++;
            } else {
                textField.setBorder(borderBlack);
            }
        };

        //Validacao de data de nascimento
        if (sexoList.getSelectedItem() == "Selecionar") {
            sexoList.setBorder(borderRed);
            contErros++;
        }
        if (txtDataNasc.getText().trim().length() < 10) {
            txtDataNasc.setBorder(borderRed);
            contErros++;
        } else {
            txtDataNasc.setBorder(borderBlack);
        }
        if (passSenha.getPassword() == null) {
            passSenha.setBorder(borderRed);
            contErros++;
        } else {
            passSenha.setBorder(borderBlack);
        }
        //Validacao de RA, caso Colaborador
        if (txtRa.getText().trim().isEmpty() || txtRa.getText() == null) {
            txtRa.setBorder(borderRed);
            contErros++;
        } else {
            txtRa.setBorder(borderBlack);
        }

        if (contErros == 0) {
            error = false;
        } else {
            error = true;
        }
        return error;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnCadastrar) {
            if (!validaForm()) {

                Usuario cadUser = Usuario.getInstance();

                cadUser.setNomeUser(txtNome.getText());
                cadUser.setEmailUser(txtEmail.getText());
                cadUser.setRaUser(txtRa.getText());
                char[] Character = passSenha.getPassword();
                String senha = String.copyValueOf(Character);
                cadUser.setSenhaUser(senha);
                cadUser.setTipoUser("Colaborador");
                cadUser.setSexoUser((String) sexoList.getSelectedItem());

                String dataString = txtDataNasc.getText();
                DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date data = new java.sql.Date(fmt.parse(dataString).getTime());
                    cadUser.setDasNascUser(data);
                } catch (ParseException ex) {
                    Logger.getLogger(FrameCadColab.class.getName()).log(Level.SEVERE, null, ex);
                }
                Date now = new Date(System.currentTimeMillis());
                cadUser.setDataCadUser(now);

                //txtDataNasc.getText()
                Connection conn = null;
                ConexaoBD bd = new ConexaoBD();
                try {
                    conn = bd.conectar();
                    cadUser.insertUser(conn);
                    conn.setAutoCommit(false);
                    this.dispose();
                    JOptionPane.showMessageDialog(this, "Usuário Cadastrado com Sucesso");

                } catch (Exception e) {
                }
            } else {
                JOptionPane.showMessageDialog(null, "Campos obrigatórios não preenchidos.");
            }
        } else if (ae.getSource() == btnCancelar) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        FrameCadColab telaCad = new FrameCadColab();
    }

}
