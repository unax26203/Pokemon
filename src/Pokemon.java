import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public abstract class Pokemon extends Observable {

    /*private String ubicacionIcono;*/

    protected int ataque;
    protected int defensa;
    protected int vida;
    protected StateEvolucion evo;
    protected boolean seleccionado;
    protected final int maxVida;
    protected int idEntrenador;
    protected int idPokemon;
    protected int maxEuforia;
    protected int ataquesRecibidos;
    protected StateEuforia euforia;
    protected Pokemon(int idEntrenador, int idPokemon){
        Random rand= new Random();
        this.ataque=11+ rand.nextInt(7);
        this.defensa=3+ rand.nextInt(4);
        this.vida=200+ rand.nextInt(20);
        this.maxVida=this.vida;
        this.idEntrenador=idEntrenador;
        this.idPokemon=idPokemon;
        this.seleccionado=false;
        this.ataquesRecibidos=0;
        this.maxEuforia= rand.nextInt(3,7);
        evo = new VidaFull();
    }

    //TODO: cambiar de get a un método que haga todo
    public String getTipo() {
        return this.getClass().getSimpleName();
    }

    public int getAtaque() {
        return ataque;
    }

    public abstract void recibirDaño(int ataque, String tipo, boolean estaEnEuforia);

    public void changeState(StateEvolucion evo){
       this.evo=evo;
       actualizarInterfaz();
    }

    public boolean estaEnEuforia(){

        return euforia instanceof ConEuforia;
    }

    public void resetearEuforia() {
        Random rand= new Random();
        ataquesRecibidos=0;
        maxEuforia= rand.nextInt(3,7);
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public void seleccionar(){
        seleccionado=true;
        actualizarInterfaz();
    }

    public void deseleccionar(){
        seleccionado=false;
        actualizarInterfaz();
    }

    protected void actualizarInterfaz(){
        ArrayList info = new ArrayList();
        info.add(vida);
        info.add(ataque);
        info.add(defensa);
        if (evo instanceof VidaFull){
            info.add(0);
        } else if (evo instanceof MitadVida){
            info.add(1);
        } else if (evo instanceof PocaVida){
            info.add(2);
        }
        info.add(estaEnEuforia());
        info.add(ataquesRecibidos);
        info.add(maxEuforia);
        info.add(seleccionado);
        info.add(this.getClass().getSimpleName());

        setChanged();
        notifyObservers(info);
    }
    public void comprobarEuforia(){
        if (ataquesRecibidos>=maxEuforia){
            euforia = new ConEuforia();
        }else{
            euforia = new SinEuforia();
        }
    }
    public void aumentarEstadisticas(int ataque, int defensa){
        this.ataque+=ataque;
        this.defensa+=defensa;
    }
}

