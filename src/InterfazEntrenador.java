import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class InterfazEntrenador extends JFrame implements Observer {
    private int id;
    private ArrayList <InterfazPokemon> panelPokemons;
    private JPanel panelEntrenador;
    private int numPokemons;
    private JPanel contentPane;
    private Controller controller;
    private String tipo;


    private JButton botonGo;

    public InterfazEntrenador(int pId, int numPokemons, String tipo){
        id = pId;
        this.numPokemons = numPokemons;
        this.tipo = tipo;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicializar();
        GameManager.getMiGameManager().getEntrenador(id).addObserver(this);
    }

    private void inicializar() {
        setSize (1100,450);
        this.setContentPane(getContentPane());
        setTitle(tipo + id );
        setLocationRelativeTo(null);
        setVisible(true);
        this.pack();
    }

    @Override
    public JPanel getContentPane() {
        if (contentPane == null) {
            panelEntrenador = getPanelEntrenador();
            panelPokemons = new ArrayList<InterfazPokemon>();
            contentPane = new JPanel();
            contentPane.setBackground(Color.WHITE);
            contentPane.setLayout(new GridLayout(1, numPokemons + 1));
            contentPane.add(panelEntrenador);
            for (int i = 0; i < numPokemons; i++) {
                InterfazPokemon panelPokemon = new InterfazPokemon(id,i,tipo);
                contentPane.add(panelPokemon);
                panelPokemons.add(panelPokemon);
            }
        }
        return contentPane;
    }

    private JPanel getPanelEntrenador() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        panel.add(getBotonGo(), BorderLayout.NORTH);
        Random random = new Random();
        ImageIcon imagenMenu = new ImageIcon(getClass().getResource("sprites/trainer" +random.nextInt(0,5) + ".png"));
        imagenMenu = new ImageIcon(imagenMenu.getImage().getScaledInstance(150, 350, Image.SCALE_SMOOTH));
        JLabel imagenMenuLbl = new JLabel(imagenMenu);
        panel.add(imagenMenuLbl);
        return panel;
    }

    private JButton getBotonGo() {
        botonGo = new JButton("WAIT");
        botonGo.setBackground(Color.RED);
        botonGo.setBorderPainted(false);
        botonGo.setFocusPainted(false);
        botonGo.addActionListener(getController());
        return botonGo;
    }
    @Override
    public void update(Observable o, Object arg) {
        ArrayList info=(ArrayList) arg;
        if(info.get(0).equals(true)){
            botonGo.setText("GO!");
            botonGo.setBackground(Color.GREEN);
            botonGo.setBorderPainted(false);
            botonGo.setFocusPainted(false);}
        else if(info.get(0).equals(false)){
            botonGo.setText("WAIT");
            botonGo.setBackground(Color.RED);
            botonGo.setBorderPainted(false);
            botonGo.setFocusPainted(false);
        }
        if(info.get(1).equals(true)){
            JOptionPane.showMessageDialog(null,"¡¡Ha ganado: "+tipo+id+" !!");
            Window[] windows = Window.getWindows();

            for (Window window : windows) {
                window.dispose();
            }
            new InterfazMenu();
        }
    }

    //public static void acabarPartida(String tipo, int id){
    //    JOptionPane.showMessageDialog(null,"El ganador es: "+tipo+id);
    //}
    private Controller getController() {
        if(controller == null){
            controller = new Controller();
        }
        return controller;
    }

    private class Controller implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(botonGo)) {
                GameManager.getMiGameManager().botonGoPulsado(id, tipo);
            }
        }
    }
}
