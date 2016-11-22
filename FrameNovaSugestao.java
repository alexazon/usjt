
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FrameNovaSugestao extends JFrame implements ActionListener {

    private JButton btnEnviar, btnVoltar;
    private Connection conn;
    private JLabel lblTitulo, lblDescricao, lblVazio, lblTelaSugestao;
    private JTextField txtTitulo;
    private JTextArea txtDescricao;
    private JScrollPane scrollPane;
    private String[] cbCategoriaList;
    private JComboBox<String> cbCategoria;
    private JScrollPane areaScrollPane;
    private JPanel painelFundo;

    public FrameNovaSugestao(Connection conn) {

        super("Area de Sugestão");
        JPanel pnlGrid = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCentro = new GridBagConstraints();
        //JPanel pnlGrid = new JPanel(new GridLayout(8,3));
        JPanel pnlFlow = new JPanel(new FlowLayout());
        JPanel pnlGrids = new JPanel(new FlowLayout());
        ///tabela   
        //JPanel pnlCentro = new JPanel (new GridBagLayout());
        //GridBagConstraints gbcCentro = new GridBagConstraints();
        btnVoltar = new JButton("Voltar");
        btnEnviar = new JButton("Enviar");
        lblVazio = new JLabel("Selecione uma categoria");
        lblTitulo = new JLabel("Titulo: ");
        txtTitulo = new JTextField(15);
        lblDescricao = new JLabel("Titulo Descição");

        txtDescricao = new JTextArea();
        areaScrollPane = new JScrollPane(txtDescricao);
        //itens combobox   
        cbCategoriaList = carregaCategoria(conn);
        cbCategoria = new JComboBox(cbCategoriaList);

        gbcCentro.gridx = 0;
        gbcCentro.gridy = 0;
        gbcCentro.gridwidth = 4;
        pnlGrid.add(lblVazio, gbcCentro);

        //comboBox
        gbcCentro.gridx = 0;
        gbcCentro.gridy = 1;
        gbcCentro.gridwidth = 9;
        pnlGrid.add(cbCategoria, gbcCentro);
        //Label vazia para espaço entre ComboBox e Tabela
        gbcCentro.gridx = 0;
        gbcCentro.gridy = 2;
        gbcCentro.gridwidth = 4;
        pnlGrid.add(lblTitulo, gbcCentro);

        gbcCentro.gridx = 0;
        gbcCentro.gridy = 3;
        gbcCentro.gridwidth = 4;
        pnlGrid.add(txtTitulo, gbcCentro);

        //label vazia para espaço entre tabela e botão enviar
        gbcCentro.gridx = 0;
        gbcCentro.gridy = 4;
        gbcCentro.gridwidth = 4;
        pnlGrid.add(lblDescricao, gbcCentro);

        gbcCentro.gridx = 0;
        gbcCentro.gridy = 5;
        gbcCentro.gridwidth = 4;
        pnlGrid.add(areaScrollPane, gbcCentro);

        //botão enviar
        gbcCentro.gridx = 0;
        gbcCentro.gridy = 6;
        gbcCentro.gridwidth = 4;
        pnlGrids.add(btnEnviar, gbcCentro);

        gbcCentro.gridx = 3;
        gbcCentro.gridy = 7;
        gbcCentro.gridwidth = 4;
        pnlGrids.add(btnVoltar, gbcCentro);

        pnlFlow.add(pnlGrid);

        btnEnviar.addActionListener(this);
        btnVoltar.addActionListener(this);

        JPanel pnlNorte = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel lblTelaSugestao = new JLabel("Sugestão");
        JLabel lblVazio3 = new JLabel("\n");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        pnlNorte.add(lblTelaSugestao, gbc);

        Container caixa = getContentPane();
        caixa.setLayout(new BorderLayout());
        caixa.add(pnlFlow, BorderLayout.CENTER);
        caixa.add(pnlNorte, BorderLayout.NORTH);
        caixa.add(pnlGrids, BorderLayout.SOUTH);
        txtDescricao.setFont(new Font("Arial", Font.PLAIN, 12));
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setEditable(true);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(400, 250));
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public boolean validaForm() { //ARRUMAR
        boolean error;
        int contErros = 0;
        Border borderRed = BorderFactory.createLineBorder(Color.red);
        Border borderBlack = BorderFactory.createLineBorder(Color.black);
        //Validacao de data de nascimento
        if (txtTitulo.getText() == null || txtTitulo.getText().length() < 10) {
            txtTitulo.setBorder(borderRed);
            contErros++;
        } else {
            txtTitulo.setBorder(borderBlack);
        }
        if (txtDescricao.getText() == null || txtDescricao.getText().length() < 10) {
            txtDescricao.setBorder(borderRed);
            contErros++;
        } else {
            txtTitulo.setBorder(borderBlack);
        }
        //Validacao de RA, caso Colaborador

        if (contErros == 0) {
            error = false;
        } else {
            error = true;
        }
        return error;
    }

    public String[] carregaCategoria(Connection conn) {
        CarregaCategoria categorias = new CarregaCategoria();
        ArrayList<Categoria> lista = categorias.buscarCategoria(conn);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String[] saida = new String[lista.size()];
        Categoria categoria;
        for (int i = 0; i < lista.size(); i++) {
            categoria = lista.get(i);
            saida[i] = categoria.getNomeCategoria();
        } //{"Categoria","Titulo", "Data"}

        return saida;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnEnviar) {
            if (!validaForm()) {
                Connection conn = null;
                ConexaoBD bd = new ConexaoBD();
                Categoria categoria = new Categoria(cbCategoria.getSelectedItem().toString());
                try {
                    conn = bd.conectar();
                    categoria.carregar(conn);
                    conn.setAutoCommit(false);
                } catch (Exception e) {
                }
                Usuario user = Usuario.getInstance();
                Sugestao sugest = new Sugestao();

                Date now = new Date(System.currentTimeMillis());
                sugest.setDataCadSugestao(now);
                sugest.setTitulo(txtTitulo.getText());
                sugest.setTextSugestao(txtDescricao.getText());
                int idUsuario = user.getIdUser();
                sugest.setIdUser(idUsuario);
                sugest.setIdCategoria(categoria.getIdCategoria());
                int idStatus = 2;
                sugest.setIdStatus(idStatus);
                try {
                    conn = bd.conectar();
                    sugest.insertSugestao(conn);
                    conn.setAutoCommit(false);
                    JOptionPane.showMessageDialog(this, "Sugestão Enviada");
                    this.dispose();

                } catch (Exception e) {
                }

            } else {
                JOptionPane.showMessageDialog(null, "Campos obrigatórios não preenchidos.");
            }
        } else if (ae.getSource() == btnVoltar) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        ConexaoBD bd = new ConexaoBD();
        Connection conn = null;
        try {
            conn = bd.conectar();
            FrameNovaSugestao telaSugestao = new FrameNovaSugestao(conn);
        } catch (Exception e) {
        }
    }
}
