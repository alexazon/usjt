
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

public class FrameUpdatePerfil extends JFrame implements ActionListener {

    private JLabel cadastro;
    private JLabel lblNome, lblEmail, lblRa, lblSenha, lblSenhaConfimar;
    private JTextField txtNome, txtEmail, txtRa;
    private JPasswordField passSenha, txtSenhaConfimar;
    private JButton btnCancelar;
    private JButton btnCadastrar;
    private ArrayList<JTextField> textFields;
    private Date java;

    public FrameUpdatePerfil(Connection conn) {
        super("Editar Dados de Colaborador");
        Usuario userUpdate = Usuario.getInstance();
        cadastro = new JLabel("Perfil Usuário");
        lblNome = new JLabel("Nome: ");
        txtNome = new JTextField(15);
        txtNome.setText(userUpdate.getNomeUser());
        lblEmail = new JLabel("Email: ");
        txtEmail = new JTextField(15);
        txtEmail.setText(userUpdate.getEmailUser());
        lblRa = new JLabel("Registro (RA): ");
        txtRa = new JTextField(15);
        txtRa.setText(userUpdate.getRaUser());
        lblSenha = new JLabel("Nova Senha:      ");
        passSenha = new JPasswordField(15);
        lblSenhaConfimar = new JLabel("Confirma Senha:  ");
        txtSenhaConfimar = new JPasswordField(15);
        btnCancelar = new JButton("Cancelar");
        btnCadastrar = new JButton("Atualizar");
        textFields = new ArrayList();
        textFields.add(txtNome);
        textFields.add(txtEmail);
        textFields.add(txtRa);

        Container caixa = getContentPane();
        caixa.setLayout(new BorderLayout());

        JPanel centro = new JPanel(new FlowLayout());
        JPanel pnlLinha1 = new JPanel(new GridLayout(1, 2));
        JPanel pnlLinha2 = new JPanel(new GridLayout(1, 2));
        JPanel pnlLinha3 = new JPanel(new GridLayout(1, 2));
        JPanel pnlLinha4 = new JPanel(new GridLayout(1, 2));
        JPanel pnlLinha5 = new JPanel(new GridLayout(1, 2));
        JPanel pnlLinha6 = new JPanel(new GridLayout(1, 2));
        JPanel pnlLinha7 = new JPanel(new GridLayout(1, 2));
        JPanel pnlLinha8 = new JPanel(new FlowLayout());
        JPanel pnlCentro = new JPanel(new FlowLayout(0, 10, 5));
        centro.add(cadastro);
        pnlLinha1.add(lblNome);
        pnlLinha1.add(txtNome);

        pnlLinha2.add(lblEmail);
        pnlLinha2.add(txtEmail);

        pnlLinha3.add(lblRa);
        pnlLinha3.add(txtRa);

        pnlLinha6.add(lblSenha);
        pnlLinha6.add(passSenha);

        pnlLinha7.add(lblSenhaConfimar);
        pnlLinha7.add(txtSenhaConfimar);

        pnlLinha8.add(btnCadastrar);
        pnlLinha8.add(btnCancelar);

        pnlCentro.add(pnlLinha1);
        pnlCentro.add(pnlLinha2);
        pnlCentro.add(pnlLinha3);
        pnlCentro.add(pnlLinha4);
        pnlCentro.add(pnlLinha5);
        pnlCentro.add(pnlLinha6);
        pnlCentro.add(pnlLinha7);

        add(centro, BorderLayout.NORTH);
        add(pnlCentro, BorderLayout.CENTER);
        add(pnlLinha8, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(this);
        btnCancelar.addActionListener(this);

        setVisible(true);
        //setResizable(false);
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
        char[] senha = passSenha.getPassword();
        String senhaString = String.copyValueOf(senha);
        char[] senhaConfirma = txtSenhaConfimar.getPassword();
        String senhaConfirmaString = String.copyValueOf(senhaConfirma);

        if (senhaString.equals(senhaConfirmaString)) {
            passSenha.setBorder(borderBlack);
            txtSenhaConfimar.setBorder(borderBlack);
        } else {
            passSenha.setBorder(borderRed);
            txtSenhaConfimar.setBorder(borderRed);
            contErros++;
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
                Usuario userUpdate = Usuario.getInstance();
                userUpdate.setNomeUser(txtNome.getText());
                userUpdate.setEmailUser(txtEmail.getText());
                userUpdate.setRaUser(txtRa.getText());
                if (passSenha.getPassword() != null) {
                    char[] Character = passSenha.getPassword();
                    String senha = String.copyValueOf(Character);
                    userUpdate.setSenhaUser(senha);
                }
                //txtDataNasc.getText()
                Connection conn = null;
                ConexaoBD bd = new ConexaoBD();
                try {
                    conn = bd.conectar();
                    userUpdate.updateUser(conn);
                    conn.setAutoCommit(false);
                    this.dispose();
                    JOptionPane.showMessageDialog(this, "Dados Atualizado com Sucesso");

                } catch (Exception e) {
                }

            } else {
                JOptionPane.showMessageDialog(null, "Prenchimento Incorreto.");
            }
        } else if (ae.getSource() == btnCancelar) {
            this.dispose();
        }
    }

}
