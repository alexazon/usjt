
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class IndexMural extends JFrame implements ActionListener {

    private JScrollPane rolagem;
    private JLabel lblSistema;
    private JButton btnDevolutivas, btnSugestao, btnPerfil;
    private JSeparator jSeparator1;
    private String[] categoriaString;
    private JComboBox<String> categoriaList;
    private String[] colunas = {"Categoria", "Titulo", "Data"};
    private JTable tabela;
    private JScrollPane barraRolagem;

    public IndexMural(Connection conn) {
        super("Sugestão e Devolutias");
        CadUsuario user = CadUsuario.getInstance();
        btnSugestao = new JButton("Nova Sugestao");
        btnPerfil = new JButton("Perfil");
        btnDevolutivas = new JButton("Devolutivas");
        
        lblSistema = new JLabel("Usuario: " + user.getNomeUser());
        
        
        jSeparator1 = new JSeparator();
        //dados = carregaDados(conn);
        Object[][] dados = carregaDados(conn);
        tabela = new JTable(dados, colunas);
        barraRolagem = new JScrollPane(tabela);

        Container caixa = getContentPane();
        caixa.setLayout(new BorderLayout());

        JPanel pnltop = new JPanel(new GridLayout());
        JPanel pnlright = new JPanel(new FlowLayout());
        JPanel pnlCentro = new JPanel(new FlowLayout());

        pnltop.add(lblSistema);
        pnlright.add(btnSugestao);
        pnlright.add(btnPerfil);
        pnlright.add(btnDevolutivas);
        pnltop.add(pnlright, BorderLayout.EAST);
        add(jSeparator1);
        add(barraRolagem);

        add(pnltop, BorderLayout.NORTH);

        btnSugestao.addActionListener(this);
        btnPerfil.addActionListener(this);
        btnDevolutivas.addActionListener(this);

        setVisible(true);
        //setResizable(false);
        setSize(955, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btnSugestao.setFont(new Font("Arial Black", 0, 11));
        btnPerfil.setFont(new Font("Arial Black", 0, 11));
        btnDevolutivas.setFont(new Font("Arial Black", 0, 11));
        lblSistema.setFont(new Font("Tahoma", 0, 18));
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnSugestao) {

        } else if (ae.getSource() == btnSugestao) {

        } else if (ae.getSource() == btnDevolutivas) {

        }
    }
   

    public String[][] carregaDados(Connection conn) {
        CarregaMural mural = new CarregaMural();
        ArrayList<Sugestao> lista = mural.buscarSugestao(conn);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String[][] saida = new String[lista.size()][colunas.length];
        Sugestao sugestao;
        for (int i = 0; i < lista.size(); i++) {
            sugestao = lista.get(i);
            saida[i][0] = sugestao.getCategoria();
            saida[i][1] = sugestao.getTitulo();
            saida[i][2] = formatter.format(sugestao.getDataCadSugestao());
        } //{"Categoria","Titulo", "Data"}

        return saida;
    }

    /*public static void main(String[] args) {
        ConexaoBD bd = new ConexaoBD();
        Connection conn = null;
        try {
            conn = bd.conectar();
            IndexMural tela = new IndexMural(conn);
        } catch (Exception e) {
        }
    }*/
}
