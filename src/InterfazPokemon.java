import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class InterfazPokemon extends JPanel implements Observer {
    private int idEntrenador;
    private int idPokemon;
    private ArrayList info;
    private String tipoEntrenador;
    private Controller controller;
    private JLabel lblAtaque;
    private JLabel lblDefensa;
    private JLabel lblVida;
    private JLabel lblTipo;
    private JButton botonPokemon;
    private ImageIcon imagenPokemonOriginal;
    private JProgressBar barraVida;
    private JProgressBar barraEuforia;
    public InterfazPokemon(int pIdEntrenador, int pIdPokemon, String pTipoEntrenador){
        this.idEntrenador = pIdEntrenador;
        this.idPokemon = pIdPokemon;
        this.tipoEntrenador = pTipoEntrenador;
        GameManager.getMiGameManager().getPokemonDeEntrenador(idPokemon,idEntrenador).addObserver(this);
    }

    private void inicializarComponentes() {
        inicializarPanel();
        barraVida.setMaximum((int) info.get(0)); //TODO poner maximos y mínimos
        barraEuforia.setMaximum((int) info.get(6));
    }

    private void inicializarPanel() {
        this.setLayout(new BorderLayout());
        this.add(getinfoPanel(),BorderLayout.NORTH);
        this.add(getInfoPanelPokemonBarra(), BorderLayout.CENTER);
        //TODO
    }

    private JPanel getinfoPanel(){

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(new GridLayout(4,1));
        lblAtaque = new JLabel("Ataque: " + info.get(1));
        infoPanel.add(lblAtaque);
        lblDefensa = new JLabel("Defensa: " + info.get(2));
        infoPanel.add(lblDefensa);
        lblVida = new JLabel("Vida: " + info.get(0));
        infoPanel.add(lblVida);
        lblTipo = new JLabel("Tipo: " + info.get(8));
        infoPanel.add(lblTipo);
        return infoPanel;
    }

    private JPanel getInfoPanelPokemonBarra(){
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(new BorderLayout());
        //TODO cambiar esto cuando pongamos todos los pokemon
        infoPanel.add(getBotonImagenPokemon(), BorderLayout.CENTER);
        infoPanel.add(getProgressBar(), BorderLayout.SOUTH);
        return infoPanel;
    }
    private JPanel getBotonImagenPokemon(){

        ImageIcon imagen= null;

        if (info.get(8).equals("TipoAgua")){
            imagen = new ImageIcon(getClass().getResource("sprites/Water/0squirtle.png"));
        }else if (info.get(8).equals("TipoFuego")){
            imagen = new ImageIcon(getClass().getResource("sprites/Fire/0charmander.png"));
        }else if (info.get(8).equals("TipoPlanta")){
            imagen = new ImageIcon(getClass().getResource("sprites/Grass/0bulbasaur.png"));
        }else if (info.get(8).equals("TipoElectrico")){
            imagen = new ImageIcon(getClass().getResource("sprites/Electric/0pikachu.png"));
        }
        imagenPokemonOriginal = new ImageIcon(imagen.getImage());
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.add(getBotonPokemon(imagenPokemonOriginal));

        return panel;
    }
    private JButton getBotonPokemon(ImageIcon imagen) {
        botonPokemon = new JButton(imagen);
        botonPokemon.setBorderPainted(false);
        botonPokemon.setContentAreaFilled(false);
        botonPokemon.setFocusPainted(false);
        botonPokemon.setOpaque(false);
        botonPokemon.setBorder(BorderFactory.createEmptyBorder());
        botonPokemon.addActionListener(getController());
        return botonPokemon;
    }
    private InterfazPokemon.Controller getController() {
        if(controller == null){
            controller = new Controller();
        }
        return controller;
    }

    private JPanel getProgressBar(){
        if (barraVida == null){
            barraVida = new JProgressBar();
        }
        if (barraEuforia == null){
            barraEuforia = new JProgressBar();
        }
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridLayout(2,1));
        barraVida.setStringPainted(true);
        barraVida.setString("Health");
        barraVida.setForeground(Color.GREEN);
        panel.add(barraVida);
        barraEuforia.setStringPainted(true);
        barraEuforia.setForeground(Color.RED);
        barraEuforia.setString("Charged attack");
        panel.add(barraEuforia);
        return panel;
    }

    private class Controller implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BattleManager.getBattleManager().pokemonPulsado(idEntrenador,tipoEntrenador,idPokemon);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        info = (ArrayList) arg;
        if (this.getComponentCount() == 0){
            inicializarComponentes();
        }else {
            lblAtaque.setText("Ataque: " + info.get(1));
            lblDefensa.setText("Defensa: " + info.get(2));
            lblVida.setText("Vida: " + info.get(0));
            lblTipo.setText("Tipo: " + info.get(8));
            barraVida.setValue((int) info.get(0));
            barraEuforia.setValue((int) info.get(5));
            barraEuforia.setMaximum((int) info.get(6));
            if ((boolean) info.get(7)) {
                botonPokemon.setIcon(obtenerImagenOscurecida(imagenPokemonOriginal, 100));
            } else if ((int) info.get(0) <= 0) {
                botonPokemon.setIcon(obtenerImagenOscurecida(imagenPokemonOriginal, 400));
            } else {
                botonPokemon.setIcon(imagenPokemonOriginal);
            }
            if (info.get(8).equals("TipoAgua")) {
                imagenPokemonOriginal = new ImageIcon(getClass().getResource("sprites/Water/" + info.get(3) + "squirtle.png"));
            } else if (info.get(8).equals("TipoFuego")) {
                imagenPokemonOriginal = new ImageIcon(getClass().getResource("sprites/Fire/" + info.get(3) + "charmander.png"));
            } else if (info.get(8).equals("TipoPlanta")) {
                imagenPokemonOriginal = new ImageIcon(getClass().getResource("sprites/Grass/" + info.get(3) + "bulbasaur.png"));
            } else if (info.get(8).equals("TipoElectrico")) {
                imagenPokemonOriginal = new ImageIcon(getClass().getResource("sprites/Electric/" + info.get(3) + "pikachu.png"));
            }
        }
    }

    private ImageIcon obtenerImagenOscurecida(ImageIcon imagenOriginal, int factorOscurecimiento) {
        ImageIcon imagenOscurecida = new ImageIcon(imagenOriginal.getImage());


        BufferedImage imagenBuffered = new BufferedImage(
                imagenOscurecida.getIconWidth(),
                imagenOscurecida.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D gr = imagenBuffered.createGraphics();
        imagenOscurecida.paintIcon(null, gr, 0, 0);
        gr.dispose();

        // Recorremos cada píxel de la imagen y aplicamos una transformación de oscurecimiento
        for (int x = 0; x < imagenBuffered.getWidth(); x++) {
            for (int y = 0; y < imagenBuffered.getHeight(); y++) {
                // Obtenemos el valor RGB del píxel
                int rgb = imagenBuffered.getRGB(x, y);

                // Obtenemos los valores de los componentes rojo, verde y azul
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;

                // Aplicamos una transformación de oscurecimiento solo a los valores RGB
                r = Math.max(r - factorOscurecimiento, 0);
                g = Math.max(g - factorOscurecimiento, 0);
                b = Math.max(b - factorOscurecimiento, 0);

                // Creamos un nuevo valor RGB a partir de los valores transformados
                int nuevoRGB = (rgb & 0xff000000) | (r << 16) | (g << 8) | b;

                // Asignamos el nuevo valor RGB al píxel en la imagen transformada
                imagenBuffered.setRGB(x, y, nuevoRGB);
            }
        }

        // Creamos un nuevo ImageIcon a partir de la imagen transformada
        ImageIcon imagenFinal = new ImageIcon(imagenBuffered);
        return imagenFinal;
    }
}
