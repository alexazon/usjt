
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.*;
import javax.swing.UIManager;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.MaskFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class FrameCadastroPessoa extends JFrame implements ActionListener, ItemListener {

    //componentes da tela
    private JLabel lblNome, lblDataNascimento, lblCpf, lblEmail, lblEndereco,
            lblNumero, lblBairro, lblComplemento, lblCidade, lblCep, lblRa, lblPeriodo,
            lblCampus, lblUsuario, lblSenha, lblCurso, lblUf, lblAreaAtuacao, lblPerfil;
    private JTextField txtNome, txtEmail, txtEndereco,
            txtBairro, txtComplemento, txtCidade, txtPeriodo,
            txtCampus, txtUsuario;
    private JFormattedTextField txtDataNascimento, txtCpf, txtCep, txtNumero, txtRa;
    private JPasswordField txtSenha;
    private JButton btnCadastrar, btnLimpar, btnVoltar;
    private JComboBox cbbUf, cbbCurso, cbbPerfil, cbbPeriodo, cbbCampus;
    private JPanel painelSuperior, painelInferior, painelMeio;
    private JPanel painelPessoa, painelUsuario, painelComplUsuario, painelBotoes;
    private ArrayList<JTextField> textFields;
    private Container mainContainer;
    private CheckModel model;
    private DefaultListModel dlm = new DefaultListModel();
    private static final ArrayList<Categoria> listaCategorias = new Categoria().buscaCategoriasArrayList();
    private boolean fromFrameLogin = false;

    final int LARGURA_TELA = 650;
    final int ALTURA_TELA = 400;

    //instanciando nova tela
    public FrameCadastroPessoa(boolean fromFrameLogin) {
        super("Cadastro de Usuários");

        //bordas para JPanel
        UIManager.getDefaults().put("TitledBorder.titleColor", Color.GRAY);
        Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Font titleFont = UIManager.getFont("TitledBorder.font");

        //Paineis
        painelSuperior = new JPanel(new BorderLayout());
        painelMeio = new JPanel(new BorderLayout());
        painelInferior = new JPanel(new BorderLayout());
        painelComplUsuario = new JPanel(); //Inicio necessário para método exibeComplColaborador()

        //Painel Cadastro Pessoa   
        painelPessoa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblNome = new JLabel("Nome");
        txtNome = new JTextField(35);
        lblDataNascimento = new JLabel("Data Nasc.");
        txtDataNascimento = new JFormattedTextField();
        txtDataNascimento.setColumns(10);
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.install(txtDataNascimento);
        } catch (ParseException ex) {
            Logger.getLogger(FrameCadastroPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }

        lblCpf = new JLabel("CPF    ");
        txtCpf = new JFormattedTextField();
        txtCpf.setColumns(25);
        try {
            MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
            cpfMask.install(txtCpf);
        } catch (ParseException ex) {
            Logger.getLogger(FrameCadastroPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        lblCep = new JLabel("CEP");
        txtCep = new JFormattedTextField();
        txtCep.setColumns(10);
        try {
            MaskFormatter cepMask = new MaskFormatter("#####-###");
            cepMask.install(txtCep);
        } catch (ParseException ex) {
            Logger.getLogger(FrameCadastroPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        lblEmail = new JLabel("E-mail ");
        txtEmail = new JTextField(22);
        lblEndereco = new JLabel("End.   ");
        txtEndereco = new JTextField(28);
        lblNumero = new JLabel("Número");
        txtNumero = new JFormattedTextField();
        txtNumero.setColumns(5);
        try {
            MaskFormatter numeroMask = new MaskFormatter("#######");
            numeroMask.install(txtNumero);
        } catch (ParseException ex) {
            Logger.getLogger(FrameCadastroPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }

        lblBairro = new JLabel("Bairro");
        txtBairro = new JTextField(9);
        lblComplemento = new JLabel("Complemento");
        txtComplemento = new JTextField(15);
        lblCidade = new JLabel("Cidade ");
        txtCidade = new JTextField(21);
        lblUf = new JLabel("UF");
        Vector listaUf = listarUF();
        cbbUf = new JComboBox(listaUf);
        painelPessoa.add(lblNome);
        painelPessoa.add(txtNome);
        painelPessoa.add(lblDataNascimento);
        painelPessoa.add(txtDataNascimento);
        painelPessoa.add(lblCpf);
        painelPessoa.add(txtCpf);
        painelPessoa.add(lblEmail);
        painelPessoa.add(txtEmail);
        painelPessoa.add(lblEndereco);
        painelPessoa.add(txtEndereco);
        painelPessoa.add(lblNumero);
        painelPessoa.add(txtNumero);
        painelPessoa.add(lblBairro);
        painelPessoa.add(txtBairro);
        painelPessoa.add(lblComplemento);
        painelPessoa.add(txtComplemento);
        painelPessoa.add(lblCidade);
        painelPessoa.add(txtCidade);
        painelPessoa.add(lblUf);
        painelPessoa.add(cbbUf);
        painelPessoa.add(lblCep);
        painelPessoa.add(txtCep);

        painelSuperior.add(painelPessoa);
        painelSuperior.setPreferredSize(new Dimension(160, 160));
        TitledBorder tituloPnlSuperior = BorderFactory.createTitledBorder(lowerEtched, "Cadastro de Pessoa");
        tituloPnlSuperior.setTitleFont(titleFont.deriveFont(Font.ITALIC + Font.BOLD));
        painelSuperior.setBorder(tituloPnlSuperior);

        //Configuração GridBag  
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(4, 4, 4, 4);

        //Painel Cadastro Pessoa
        painelUsuario = new JPanel(new GridBagLayout());
        lblPerfil = new JLabel("Perfil");
        Vector listaPerfis = new Perfil().getDescricaoPerfis();
        cbbPerfil = new JComboBox(listaPerfis);
        lblUsuario = new JLabel("Usuário");
        txtUsuario = new JTextField(15);
        txtSenha = new JPasswordField(15);
        lblSenha = new JLabel("Senha");
        gc.gridx = 0;
        gc.gridy = 0;
        painelUsuario.add(lblPerfil, gc);
        gc.gridx = 1;
        gc.gridy = 0;
        painelUsuario.add(cbbPerfil, gc);
        cbbPerfil.addItemListener(this);
        gc.gridx = 0;
        gc.gridy = 1;
        painelUsuario.add(lblUsuario, gc);
        gc.gridx = 1;
        gc.gridy = 1;
        painelUsuario.add(txtUsuario, gc);
        gc.gridx = 0;
        gc.gridy = 2;
        painelUsuario.add(lblSenha, gc);
        gc.gridx = 1;
        gc.gridy = 2;
        painelUsuario.add(txtSenha, gc);

        BoxLayout blp = new BoxLayout(painelInferior, BoxLayout.LINE_AXIS);
        painelInferior.setLayout(blp);
        painelInferior.add(painelUsuario);
        TitledBorder tituloPnlInferior = BorderFactory.createTitledBorder(lowerEtched, "Usuário");
        tituloPnlInferior.setTitleFont(titleFont.deriveFont(Font.ITALIC + Font.BOLD));
        painelInferior.setBorder(tituloPnlInferior);

        //Painel de botoes
        painelBotoes = new JPanel(new GridBagLayout());
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(this);
        btnLimpar = new JButton("Limpar Formulário");
        btnLimpar.addActionListener(this);
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(this);
        gc.gridx = 0;
        gc.gridy = 0;
        painelBotoes.add(btnVoltar, gc);
        gc.gridx = 1;
        gc.gridy = 0;
        painelBotoes.add(btnLimpar, gc);
        gc.gridx = 2;
        gc.gridy = 0;
        painelBotoes.add(btnCadastrar, gc);

        //container principal
        mainContainer = getContentPane();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(painelSuperior, BorderLayout.NORTH);
        mainContainer.add(painelInferior, BorderLayout.CENTER);
        mainContainer.add(painelBotoes, BorderLayout.SOUTH);

        //Adiciona TextFields obrigatorios em ArrayList para validacao em looping
        textFields = new ArrayList();
        textFields.add(txtNome);
        textFields.add(txtDataNascimento);
        textFields.add(txtCpf);
        textFields.add(txtEmail);
        textFields.add(txtEndereco);
        textFields.add(txtNumero);
        textFields.add(txtBairro);
        textFields.add(txtCidade);
        textFields.add(txtCep);
        textFields.add(txtUsuario);
        textFields.add(txtSenha);

        if (fromFrameLogin) {
            cbbPerfil.setSelectedIndex(2);
            cbbPerfil.setEnabled(false);

        }

        //configurações do JFrame
        setSize(LARGURA_TELA, ALTURA_TELA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setDefaultLookAndFeelDecorated(true);
        setResizable(false); //não deixa fazer alterações no tamanho da janela      
        setLocationRelativeTo(null); //faz com que o form inialize no meio da tela   
    }

    public Vector listarUF() {
        Vector listaUF = new Vector();
        listaUF.addElement("AC");
        listaUF.addElement("AL");
        listaUF.addElement("AP");
        listaUF.addElement("AM");
        listaUF.addElement("BA");
        listaUF.addElement("CE");
        listaUF.addElement("DF");
        listaUF.addElement("ES");
        listaUF.addElement("GO");
        listaUF.addElement("MA");
        listaUF.addElement("MT");
        listaUF.addElement("MS");
        listaUF.addElement("MG");
        listaUF.addElement("PR");
        listaUF.addElement("PB");
        listaUF.addElement("PA");
        listaUF.addElement("PE");
        listaUF.addElement("PI");
        listaUF.addElement("RJ");
        listaUF.addElement("RN");
        listaUF.addElement("RS");
        listaUF.addElement("RO");
        listaUF.addElement("RR");
        listaUF.addElement("SC");
        listaUF.addElement("SE");
        listaUF.addElement("SP");
        listaUF.addElement("TO");
        return listaUF;
    }

    public void exibeComplColaborador() {
        painelComplUsuario = new JPanel(new GridBagLayout());
        lblRa = new JLabel("RA");
        txtRa = new JFormattedTextField();
        txtRa.setColumns(15);
        try {
            MaskFormatter raMask = new MaskFormatter("#########");
            raMask.install(txtRa);
        } catch (ParseException ex) {
            Logger.getLogger(FrameCadastroPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] periodos = {"Manhã", "Tarde", "Noite"};
        lblPeriodo = new JLabel("Período");
        cbbPeriodo = new JComboBox(periodos);
        String[] campus = {"Butantã", "Mooca"};
        lblCampus = new JLabel("Campus");
        cbbCampus = new JComboBox(campus);
        lblCurso = new JLabel("Curso");
        Vector listaCursos = new Curso().getDescricaoCursos();
        cbbCurso = new JComboBox(listaCursos);

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(4, 4, 4, 4);
        gc.gridx = 0;
        gc.gridy = 0;
        painelComplUsuario.add(lblRa, gc);
        gc.gridx = 1;
        gc.gridy = 0;
        painelComplUsuario.add(txtRa, gc);
        gc.gridx = 0;
        gc.gridy = 1;
        painelComplUsuario.add(lblPeriodo, gc);
        gc.gridx = 1;
        gc.gridy = 1;
        painelComplUsuario.add(cbbPeriodo, gc);
        gc.gridx = 0;
        gc.gridy = 2;
        painelComplUsuario.add(lblCampus, gc);
        gc.gridx = 1;
        gc.gridy = 2;
        painelComplUsuario.add(cbbCampus, gc);
        gc.gridx = 0;
        gc.gridy = 3;
        painelComplUsuario.add(lblCurso, gc);
        gc.gridx = 1;
        gc.gridy = 3;
        painelComplUsuario.add(cbbCurso, gc);
        painelInferior.add(painelComplUsuario);
        painelComplUsuario.revalidate();
        painelComplUsuario.repaint();

    }

    public void exibeComplAvaliador() {

        model = new CheckModel(listaCategorias);
        JTable table
                = new JTable(model) {
            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(320, 100);
            }
        };
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(4, 4, 4, 4);
        gc.gridx = 0;
        gc.gridy = 0;
        painelComplUsuario.add(new JScrollPane(table));
        model.addTableModelListener(
                new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                DefaultListModel dlm = new DefaultListModel();
                dlm.removeAllElements();
                for (Integer integer : model.checked) {
                    dlm.addElement(integer);
                }
            }
        });

        painelInferior.add(painelComplUsuario);
        painelComplUsuario.revalidate();
        painelComplUsuario.repaint();
    }

    public void limpaComplUsuario() {
        painelComplUsuario.removeAll();
        painelInferior.revalidate();
        painelInferior.repaint();
    }

    public void insereUsuarioXCategoria(int idUs, int idCat) {
        String queryInsert
                = "INSERT INTO usuarios_categorias "
                + "(id_usuario, id_categoria) VALUES "
                + "(?, ?)";
        ConexaoMySQL con = new ConexaoMySQL();
        Connection conAtiva = null;
        try {
            conAtiva = con.conectar();
        } catch (SQLException e2) {
            System.out.println(e2.getStackTrace());
            JOptionPane.showMessageDialog(null, "Problema ao conectar com o Banco.");
        }

        try (PreparedStatement pst = conAtiva.prepareStatement(queryInsert);) {
            pst.setInt(1, idUs);
            pst.setInt(2, idCat);
            pst.execute();
        } catch (SQLException e2) {
            System.out.println(e2.getStackTrace());
            JOptionPane.showMessageDialog(null, "Erro de inserção de categorias por usuário.");
        }
    }

    public void limpaForm() {
        txtNome.setText("");
        txtDataNascimento.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        txtNumero.setText("");
        txtBairro.setText("");
        txtComplemento.setText("");
        txtCidade.setText("");
        txtCep.setText("");
        txtRa.setText("");
        txtPeriodo.setText("");
        txtCampus.setText("");
        txtUsuario.setText("");
        txtSenha.setText("");
    }

    public boolean validaForm() { //ARRUMAR
        boolean error;
        int contErros = 0;
        Border borderRed = BorderFactory.createLineBorder(Color.red);
        Border borderBlack = BorderFactory.createLineBorder(Color.black);
        //Validacao de textFields - atributos Pessoa------------------------
        for (JTextField textField : textFields) {
            if (textField.getText() == null || textField.getText().trim().isEmpty()) {
                textField.setBorder(borderRed);
                contErros++;
            } else {
                textField.setBorder(borderBlack);
            }
        };

        //Validacao de data de nascimento
        if (txtDataNascimento.getText().trim().length() < 10) {
            txtDataNascimento.setBorder(borderRed);
            contErros++;
        } else {
            txtDataNascimento.setBorder(borderBlack);
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

    public void actionPerformed(ActionEvent a) {
        if (a.getSource() == btnCadastrar) {

            if (!validaForm()) {

                // Construção de Pessoa para inserção           
                String nome = txtNome.getText();
                String cpf = txtCpf.getText();
                String dataNascimento = txtDataNascimento.getText();
                String email = txtEmail.getText();
                String endereco = txtEndereco.getText();
                int numero = Integer.parseInt(txtNumero.getText().trim());
                String bairro = txtBairro.getText();
                String complemento = txtComplemento.getText();
                String cidade = txtCidade.getText();
                String estado = cbbUf.getSelectedItem().toString();
                String cep = txtCep.getText();
                Pessoa pessoa = new Pessoa();
                pessoa.setNome(nome);
                pessoa.setCpf(cpf);
                pessoa.setDataNascimento(dataNascimento);
                pessoa.setEmail(email);
                pessoa.setEndereco(endereco);
                pessoa.setNumero(numero);
                pessoa.setBairro(bairro);
                pessoa.setComplemento(complemento);
                pessoa.setCidade(cidade);
                pessoa.setEstado(estado);
                pessoa.setCep(cep);
                Pessoa nPessoa = pessoa.inserir();

                // Construção de Usuario para inserção 
                Usuario nUsuario = new Usuario();
                String usuario = txtUsuario.getText();
                char[] encSenha = txtSenha.getPassword();
                String senha = new String(encSenha);
                String perfil = cbbPerfil.getSelectedItem().toString();
                int idPessoa = pessoa.getIdPessoa();
                nUsuario.setUsuario(usuario);
                nUsuario.setSenha(senha);
                nUsuario.setIdPessoa(idPessoa);

                //Decisao do tipo de usuário a ser criado         
                switch (perfil) {
                    case "Colaborador":
                        nUsuario.setIdPerfil(3);
                        nUsuario.inserir();
                        String ra = txtRa.getText();
                        String periodo = cbbPeriodo.getSelectedItem().toString();
                        String campus = cbbCampus.getSelectedItem().toString();
                        String dCurso = cbbCurso.getSelectedItem().toString();
                        Curso curso = new Curso().getCursoByDescricao(dCurso);
                        Colaborador colaborador = new Colaborador();
                        colaborador.setRA(ra);
                        colaborador.setTurno(periodo);
                        colaborador.setCampus(campus);
                        colaborador.setIdCurso(curso.getIdCurso());
                        colaborador.setIdPessoa(idPessoa);
                        colaborador.inserir();
                        break;
                    case "Avaliador":
                        nUsuario.setIdPerfil(2);
                        nUsuario.inserir();
                        for (Integer integer : model.checked) {
                            int idUsuario = nUsuario.getIdUsuario();
                            int idCategoria = integer + 1;
                            insereUsuarioXCategoria(idUsuario, idCategoria);
                        }
                        break;
                    default:
                        nUsuario.setIdPerfil(1);
                        nUsuario.inserir();
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Campos obrigatórios não preenchidos.");
            }
        } else if (a.getSource() == btnLimpar) {
            limpaForm();
        } else if (a.getSource() == btnVoltar) {
            dispose();
            FrameLogin fl = new FrameLogin();

        }

    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String perfilSelecionado = e.getItem() + "";
            switch (perfilSelecionado) {
                case "Avaliador":
                    limpaComplUsuario();
                    exibeComplAvaliador();
                    break;
                case "Colaborador":
                    limpaComplUsuario();
                    exibeComplColaborador();
                    break;
                case "Superior":
                    limpaComplUsuario();
                    break;
            }
        }
    }

    //CLASSE PARA LISTA DE COMBO BOX (CATEGORIAS X AVALIADOR)   
    private static class CheckModel extends AbstractTableModel {

        private String[] columnNames = {"Categoria", "Permitido"};
        private final int rows;
        private java.util.List<Boolean> rowList;
        private Set<Integer> checked = new TreeSet<Integer>();
        private ArrayList<Categoria> lista;

        public CheckModel(ArrayList<Categoria> l) {
            this.rows = l.size();
            rowList = new ArrayList<Boolean>(rows);
            for (int i = 0; i < rows; i++) {
                rowList.add(Boolean.FALSE);
            }
            lista = l;
        }

        @Override
        public int getRowCount() {
            return rows;
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            if (col == 0) {
                return lista.get(row).getDescricao();
            } else {
                return rowList.get(row);
            }
        }

        @Override
        public void setValueAt(Object aValue, int row, int col) {
            boolean b = (Boolean) aValue;
            rowList.set(row, b);
            if (b) {
                checked.add(row);
            } else {
                checked.remove(row);
            }
            fireTableRowsUpdated(row, row);
        }

        @Override
        public Class<?> getColumnClass(int col) {
            return getValueAt(0, col).getClass();
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return col == 1;
        }

    }

    public static void main(String[] args) {
        new FrameCadastroPessoa(false);
    }

}
