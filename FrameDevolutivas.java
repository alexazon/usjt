
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FrameDevolutivas extends JFrame implements ActionListener, ItemListener {

    private JScrollPane rolagem;
    private JLabel lblSistema;
    private JButton btnVoltar, btnSugestao;
    private JSeparator jSeparator1;
    private String[] colunas = {"Categoria", "Titulo", "Status", "Data"};
    private JTable tabela;
    private Connection conn;
    private JScrollPane barraRolagem;
    private Object[][] dados;
    private Container caixa;
    private JPanel pnlCentro;

    public FrameDevolutivas(Connection conn) {
        super("Sugestão e Devolutias");
        Usuario user = Usuario.getInstance();
        btnSugestao = new JButton("Nova Sugestao");
        btnVoltar = new JButton("Voltar");

        lblSistema = new JLabel("Usuario: " + user.getNomeUser());

        jSeparator1 = new JSeparator();
        //dados = carregaDados(conn);

        dados = carregaDados(conn);
        tabela = new JTable(dados, colunas);
        barraRolagem = new JScrollPane(tabela);

        caixa = getContentPane();
        caixa.setLayout(new BorderLayout());

        JPanel pnltop = new JPanel(new FlowLayout());
        JPanel pnlright = new JPanel(new FlowLayout());
        JPanel pnlComboBox = new JPanel(new FlowLayout());
        pnlCentro = new JPanel(new FlowLayout());

        pnltop.add(lblSistema);
        pnlright.add(btnSugestao);
        pnlright.add(btnVoltar);
        pnltop.add(pnlright, BorderLayout.EAST);
        add(jSeparator1);
        pnlCentro.add(barraRolagem);

        add(pnltop, BorderLayout.NORTH);
        add(pnlCentro, BorderLayout.CENTER);

        btnSugestao.addActionListener(this);
        btnVoltar.addActionListener(this);

        setVisible(true);
        //setResizable(false);
        setSize(955, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        btnSugestao.setFont(new Font("Arial Black", 0, 11));
        btnVoltar.setFont(new Font("Arial Black", 0, 11));
        lblSistema.setFont(new Font("Tahoma", 0, 18));
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnSugestao) {
            Connection conn = null;
            ConexaoBD bd = new ConexaoBD();
            try {
                conn = bd.conectar();
                new FrameNovaSugestao(conn);
            } catch (Exception e) {
            }

        } else if (ae.getSource() == btnVoltar) {
            this.dispose();
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            ConexaoBD bd = new ConexaoBD();
            Connection conn = null;
            try {
                conn = bd.conectar();
            } catch (Exception e2) {
            }
            dados = carregaDados(conn);
            tabela = new JTable(dados, colunas);
            caixa.remove(pnlCentro);
            pnlCentro.remove(barraRolagem);
            barraRolagem = new JScrollPane(tabela);
            pnlCentro.add(barraRolagem);
            caixa.add(pnlCentro, BorderLayout.CENTER);
            //adicionar elementos torna o container invalido
            //por isso precisa revalidar
            validate();
            //redesenha o container
            repaint();
        }
    }

    public String[][] carregaDados(Connection conn) {
        CarregaSugestao mural = new CarregaSugestao();
        Usuario user = Usuario.getInstance();
        ArrayList<Sugestao> lista = mural.buscarSugestaoRealizada(conn);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String[][] saida = new String[lista.size()][colunas.length];
        Sugestao sugestao;
        for (int i = 0; i < lista.size(); i++) {
            sugestao = lista.get(i);
            saida[i][0] = sugestao.getCategoria();
            saida[i][1] = sugestao.getTitulo();
            if (sugestao.getIdStatus() == 2) {
                saida[i][2] = "Pendente";
            }
            saida[i][3] = formatter.format(sugestao.getDataCadSugestao());
        } //{"Categoria","Titulo", "Data"}

        return saida;
    }

    public static void main(String[] args) {
        ConexaoBD bd = new ConexaoBD();
        Connection conn = null;
        try {
            conn = bd.conectar();
            FrameDevolutivas tela = new FrameDevolutivas(conn);
        } catch (Exception e) {
        }
    }
}
