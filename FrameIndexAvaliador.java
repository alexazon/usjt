
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class FrameIndexAvaliador extends JFrame implements ActionListener, ItemListener, ListSelectionListener {

    final int LARGURA_TELA = 600;
    final int ALTURA_TELA = 300;
    final int LARGURA_SCROLL_PANE = LARGURA_TELA - 200;
    final int ALTURA_SCROLL_PANE = ALTURA_TELA - 110;

    private JScrollPane rolagem;
    private JLabel lblSistema;
    private JButton btnPerfil;
    private JSeparator jSeparator1;
    private String[] colunas = {"Cod. Sugestao", "Categoria", "Titulo", "Usuario", "Data"};
    private JTable tabela;
    private Connection conn;
    private Object[][] dados;
    private Container caixa;
    private JPanel pnlCentro;
    private Connection Conn;

    private static FrameIndexAvaliador intanciaUnica;

    public static synchronized FrameIndexAvaliador getInstance() {
        if (intanciaUnica == null) {
            ConexaoBD bd = new ConexaoBD();
            Connection conn = null;
            try {
                conn = bd.conectar();
                intanciaUnica = new FrameIndexAvaliador(conn);
            } catch (Exception e) {
            }
        }
        return intanciaUnica;
    }

    public FrameIndexAvaliador(Connection conn) {
        super("Sugestão e Devolutias");
        Usuario user = Usuario.getInstance();
        btnPerfil = new JButton("Perfil");

        lblSistema = new JLabel("Usuario: " + user.getNomeUser());

        jSeparator1 = new JSeparator();
        //dados = carregaDados(conn);

        dados = carregaSugestaoAvaliador(conn);
        instanciaJTableEScrollPane();

        caixa = getContentPane();
        caixa.setLayout(new BorderLayout());

        JPanel pnltop = new JPanel(new FlowLayout());
        JPanel pnlright = new JPanel(new FlowLayout());
        JPanel pnlComboBox = new JPanel(new FlowLayout());
        pnlCentro = new JPanel(new FlowLayout());

        pnltop.add(lblSistema);
        pnlright.add(btnPerfil);
        pnltop.add(pnlright, BorderLayout.EAST);
        add(jSeparator1);
        pnlCentro.add(rolagem);

        add(pnltop, BorderLayout.NORTH);
        add(pnlCentro, BorderLayout.CENTER);

        btnPerfil.addActionListener(this);

        setVisible(true);
        //setResizable(false);
        setSize(955, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btnPerfil.setFont(new Font("Arial Black", 0, 11));
        lblSistema.setFont(new Font("Tahoma", 0, 18));
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            ConexaoBD bd = new ConexaoBD();
            Connection conn = null;
            try {
                conn = bd.conectar();
               System.out.println(tabela.getValueAt(tabela.getSelectedRow(),0));
                FrameAvaliaSugestao tela = new FrameAvaliaSugestao(conn);

            } catch (Exception e2) {
            }
        } else {
        }
        
        /* colocar dentro deste if porque o evento e disparado duas vezes e assim 
         filtra-se somente um deles 
      if(e.getValueIsAdjusting()){
         String resultadoCategoriaTitulo = 
            tabela.getColumnName(0)+": "+
            tabela.getValueAt(tabela.getSelectedRow(),0)+
            "\n"+tabela.getColumnName(1)+": "+
            tabela.getValueAt(tabela.getSelectedRow(),1);
                 
            String resultadoUserData = tabela.getColumnName(2)+": "+
            tabela.getValueAt(tabela.getSelectedRow(),2)+
            "\n"+tabela.getColumnName(3)+": "+
                 tabela.getValueAt(tabela.getSelectedRow(), 3);
         
         JOptionPane.showMessageDialog(this, resultadoCategoriaTitulo);
      }*/
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnPerfil) {
            ConexaoBD bd = new ConexaoBD();
            Connection conn = null;
            try {
                conn = bd.conectar();
                FrameUpdatePerfil tela = new FrameUpdatePerfil(conn);
            } catch (Exception e) {
            }
        }
    }
    public void instanciaJTableEScrollPane() {
        tabela = new JTable();
        tabela.setModel(
                new DefaultTableModel(dados, colunas) {

            public boolean isCellEditable(int row, int col) {
                return false;
            }

        }
        );
        tabela.getSelectionModel().addListSelectionListener(this);

        //coloca a JTable em um scroll pane para ter a barra de rolagem
        rolagem = new JScrollPane(tabela);
        /*
       * fixa a dimensao do scroll pane, senao ele fica grande o bastante para
       * que a JTable sempre caiba nele e por isso a barra de rolagem nao aparece
       * nunca
         */
        rolagem.setPreferredSize(new Dimension(LARGURA_SCROLL_PANE, ALTURA_SCROLL_PANE));

    }

    public void refazTela() {
        /* 
          * para refazer a tela com novos objetos e preciso remover os anteriores;
          * entao tiramos o painel do JFrame e o scrollpane do painel; instancia
          * outro scrollpane, configura de novo o tamanho preferido para aparecer a
          * barra de rolagem, adiciona no painel e coloca o painel de novo no JFrame
         */
        caixa.remove(pnlCentro);
        pnlCentro.remove(rolagem);
        //instancia uma nova JTable e uma nova barra de rolagem, pois nao da para mudar o conteudo da antiga
        instanciaJTableEScrollPane();
        //coloca de volta
        pnlCentro.add(rolagem);
        caixa.add(pnlCentro, BorderLayout.CENTER);
        /* adicionar elementos torna o container invalido
          * por isso precisa revalidar
         */
        validate();
        //redesenha o container
        repaint();

    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            ConexaoBD bd = new ConexaoBD();
            Connection conn = null;
            try {
                conn = bd.conectar();
            } catch (Exception e2) {
            }
            dados = carregaSugestaoAvaliador(conn);
            tabela = new JTable(dados, colunas);
            caixa.remove(pnlCentro);
            pnlCentro.remove(rolagem);
            rolagem = new JScrollPane(tabela);
            pnlCentro.add(rolagem);
            caixa.add(pnlCentro, BorderLayout.CENTER);
            //adicionar elementos torna o container invalido
            //por isso precisa revalidar
            validate();
            //redesenha o container
            repaint();
        }
    }

    public String[][] carregaSugestaoAvaliador(Connection conn) {
        CarregaSugestao mural = new CarregaSugestao();
        ArrayList<Sugestao> lista = mural.buscarSugestaoAvaliador(conn);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String[][] saida = new String[lista.size()][colunas.length];
        Sugestao sugestao;
        for (int i = 0; i < lista.size(); i++) {
            sugestao = lista.get(i);
            saida[i][0] = sugestao.getIdSugestao() + "";
            saida[i][1] = sugestao.getCategoria();
            saida[i][2] = sugestao.getTitulo();
            saida[i][3] = sugestao.getUserName();
            saida[i][4] = formatter.format(sugestao.getDataCadSugestao());

        } //{"Categoria","Titulo", "Data"}

        return saida;
    }

    /*public String[][] carregaDados(Connection conn) {
        CarregSugestao mural = new CarregSugestao();
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
     */
    public static void main(String[] args) {

        FrameIndexAvaliador tela = FrameIndexAvaliador.getInstance();

    }
}
