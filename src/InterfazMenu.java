import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InterfazMenu extends JFrame{
    private JPanel contentPane;
    private JPanel  panelDerecho;
    private JPanel  panelParametros;
    private JPanel  panelExtras;
    private JPanel  panelBotones;
    private JLabel lblPlayers;
    private JLabel lblNpc;
    private JLabel  lblMilisecs;
    private JLabel  lblNumPokemons;
    private JTextField  txtPlayers;
    private JTextField  txtNpc;
    private JTextField  txtMilisecs;
    private JTextField  txtNumPokemons;
    private JButton btnReadMe;
    private JButton btnFree4All;
    private JLabel  imagenMenuLbl;
    private ImageIcon imagenMenu;

    private Controller controller = null;
    public InterfazMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicializar();
    }

    private void inicializar() {
        setSize (1100,475);
        this.setContentPane(getContentPane());
        setTitle("Menu");
        setLocationRelativeTo(null);
        setVisible(true);
    }
    @Override
    public JPanel getContentPane() {
        if (contentPane == null) {
            contentPane = new JPanel();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(getPanelDerecho(), BorderLayout.EAST);
            contentPane.add(getImagenMenu(), BorderLayout.CENTER);
            contentPane.setBackground(Color.WHITE);
        }
        return contentPane;
    }

    private JLabel getImagenMenu() {
        if (imagenMenu == null){
            imagenMenu = new ImageIcon(getClass().getResource("sprites/main.png"));
            imagenMenu = new ImageIcon(imagenMenu.getImage().getScaledInstance(500, 350, Image.SCALE_SMOOTH));
            imagenMenuLbl = new JLabel(imagenMenu);
        }
        return imagenMenuLbl;
    }

    private JPanel getPanelDerecho() {
        if (panelDerecho == null){
            panelDerecho = new JPanel();
            panelDerecho.setLayout(new GridLayout(2,1));
            panelDerecho.add(getPanelParametros());
            panelDerecho.add(getPanelExtras());
            panelDerecho.setBackground(Color.BLACK);
        }
        return panelDerecho;
    }

    private JPanel getPanelExtras() {
        if (panelExtras == null){
            panelExtras = new JPanel();
            panelExtras.setLayout(new GridLayout(1,2));
            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);
            panelExtras.add(panel);
            panelExtras.add(getPanelBotones());
        }
        return panelExtras;
    }

    private JPanel getPanelBotones() {
        if (panelBotones == null){
            panelBotones = new JPanel();
            panelBotones.setLayout(new FlowLayout());
            panelBotones.add(getBtnFree4All());
            panelBotones.add(getBtnReadMe());
            panelBotones.setBackground(Color.WHITE);
        }
        return panelBotones;
    }

    private JButton getBtnFree4All() {
        if (btnFree4All == null){
            btnFree4All = new JButton("Free4All");
            btnFree4All.setPreferredSize(new Dimension(120, 45));
            btnFree4All.setFont(new Font("Arial", Font.BOLD, 18));
            btnFree4All.setForeground(Color.GREEN);
            btnFree4All.setBackground(Color.LIGHT_GRAY);
            btnFree4All.addActionListener(getController());
        }
        return btnFree4All;
    }

    private JButton getBtnReadMe() {
        if (btnReadMe == null){
            btnReadMe = new JButton("ReadMe");
            btnReadMe.setPreferredSize(new Dimension(120, 45));
            btnReadMe.setFont(new Font("Arial", Font.BOLD, 18));
            btnReadMe.setForeground(Color.ORANGE);
            btnReadMe.setBackground(Color.LIGHT_GRAY);
            btnReadMe.addActionListener(getController());
        }
        return btnReadMe;
    }

    private JPanel getPanelParametros() {
        if (panelParametros == null){
            panelParametros = new JPanel();
            panelParametros.setLayout(new GridLayout(4,2));
            panelParametros.add(getLblPlayers());
            panelParametros.add(getTxtPlayers());
            panelParametros.add(getLblNpc());
            panelParametros.add(getTxtNpc());
            panelParametros.add(getLblMilisecs());
            panelParametros.add(getTxtMilisecs());
            panelParametros.add(getLblNumPokemons());
            panelParametros.add(getTxtNumPokemons());
            panelParametros.setBackground(Color.WHITE);
        }
        return panelParametros;
    }

    private JTextField getTxtNumPokemons() {
        if (txtNumPokemons == null){
            txtNumPokemons = new JTextField();
            txtNumPokemons.setPreferredSize(new Dimension(200, 50));
            txtNumPokemons.setFont(new Font("Arial", Font.BOLD, 20));
            txtNumPokemons.setBackground(Color.LIGHT_GRAY);
            txtNumPokemons.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 2),
                    BorderFactory.createLineBorder(Color.WHITE, 2)));
            txtNumPokemons.setForeground(Color.GREEN);
            txtNumPokemons.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return txtNumPokemons;
    }

    private JLabel getLblNumPokemons() {
        if (lblNumPokemons == null){
            lblNumPokemons = new JLabel("Num Pokemons");
            lblNumPokemons.setVerticalAlignment(SwingConstants.CENTER);
            lblNumPokemons.setHorizontalAlignment(SwingConstants.CENTER);
            lblNumPokemons.setFont(new Font("Arial", Font.BOLD, 20));
            lblNumPokemons.setBackground(Color.WHITE);
        }
        return lblNumPokemons;
    }

    private JTextField getTxtMilisecs() {
        if (txtMilisecs == null){
            txtMilisecs = new JTextField();
            txtMilisecs.setFont(new Font("Arial", Font.BOLD, 20));
            txtMilisecs.setBackground(Color.LIGHT_GRAY);
            txtMilisecs.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 2),
                    BorderFactory.createLineBorder(Color.WHITE, 2)));
            txtMilisecs.setForeground(Color.GREEN);
            txtMilisecs.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return txtMilisecs;
    }

    private JLabel getLblMilisecs() {
        if (lblMilisecs == null){
            lblMilisecs = new JLabel("Milisecs");
            lblMilisecs.setVerticalAlignment(SwingConstants.CENTER);
            lblMilisecs.setHorizontalAlignment(SwingConstants.CENTER);
            lblMilisecs.setFont(new Font("Arial", Font.BOLD, 20));
            lblMilisecs.setBackground(Color.WHITE);
        }
        return lblMilisecs;
    }
    private JTextField getTxtNpc() {
        if (txtNpc == null){
            txtNpc = new JTextField();
            txtNpc.setFont(new Font("Arial", Font.BOLD, 20));
            txtNpc.setBackground(Color.LIGHT_GRAY);
            txtNpc.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 2),
                    BorderFactory.createLineBorder(Color.WHITE, 2)));
            txtNpc.setForeground(Color.GREEN);
            txtNpc.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return txtNpc;
    }
    private JLabel getLblNpc() {
        if (lblNpc == null){
            lblNpc = new JLabel("NPC");
            lblNpc.setVerticalAlignment(SwingConstants.CENTER);
            lblNpc.setHorizontalAlignment(SwingConstants.CENTER);
            lblNpc.setFont(new Font("Arial", Font.BOLD, 20));
            lblNpc.setBackground(Color.WHITE);
        }
        return lblNpc;
    }

    private JTextField getTxtPlayers() {
        if (txtPlayers == null){
            txtPlayers = new JTextField();
            txtPlayers.setFont(new Font("Arial", Font.BOLD, 20));
            txtPlayers.setBackground(Color.LIGHT_GRAY);
            txtPlayers.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 2),
                    BorderFactory.createLineBorder(Color.WHITE, 2)));
            txtPlayers.setForeground(Color.GREEN);
            txtPlayers.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return txtPlayers;
    }

    private JLabel getLblPlayers() {
        if (lblPlayers == null){
            lblPlayers = new JLabel("Players");
            lblPlayers.setVerticalAlignment(SwingConstants.CENTER);
            lblPlayers.setHorizontalAlignment(SwingConstants.CENTER);
            lblPlayers.setFont(new Font("Arial", Font.BOLD, 20));
        }
        return lblPlayers;
    }

    private Controller getController() {
        if(controller == null){
            controller = new Controller();
        }
        return controller;
    }

    private class Controller implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btnFree4All)){
                if (txtMilisecs.getText().equals("") || txtNpc.getText().equals("") || txtNumPokemons.getText().equals("") || txtPlayers.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Rellena todos los campos");
                }else {
                    try {
                        GameManager.getMiGameManager().generarEntrenadores(Integer.parseInt(txtPlayers.getText()),
                                Integer.parseInt(txtNpc.getText()), Integer.parseInt(txtNumPokemons.getText()));
                        int i=0;
                        while(i<Integer.parseInt(txtPlayers.getText())+Integer.parseInt(txtNpc.getText())){
                            if (i >= Integer.parseInt(txtPlayers.getText())){
                                new InterfazEntrenador(i,Integer.parseInt(txtNumPokemons.getText()), "Bot");
                            }else{
                                new InterfazEntrenador(i,Integer.parseInt(txtNumPokemons.getText()), "Jugador");
                            }
                            i++;
                        }
                        GameManager.getMiGameManager().empezarJuego(Integer.parseInt(txtMilisecs.getText()));

                        setVisible(false);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else if (e.getSource().equals(btnReadMe)){
                JOptionPane.showMessageDialog(null, "Para empezar el juego, rellena todos los campos y pulsa el boton Free4All");
            }
        }
    }
}
