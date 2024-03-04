import java.util.ArrayList;
import java.util.Observable;
import java.util.stream.Stream;

public abstract class EntrenadorPokemon extends Observable {
    protected int id;

    protected String tipo;
    protected boolean ganador;
    protected boolean turno;
    protected ArrayList<Pokemon> listaPokemon;

    public EntrenadorPokemon(int pId){
        id = pId;
        listaPokemon = new ArrayList<Pokemon>();
    }

    public void añadirPokemons(int numPokemons) {
        for(int i = 0; i < numPokemons; i++){
            listaPokemon.add(PokemonFactory.getPokemonFactory().crearPokemonAleatorio(id,i));
        }
    }
    public int getId(){
        return id;
    }
    public int getNumPokemons() {
        return listaPokemon.size();
    }

    public Pokemon obtenerPokemon(int idPokemon) {
        return listaPokemon.get(idPokemon);
    }
    public int getNumPokemonsVivos() {
        int numPokemonsVivos = 0;
        for (Pokemon pokemon : listaPokemon) {
            if (pokemon.estaVivo()) {
                numPokemonsVivos++;
            }
        }
        return numPokemonsVivos;
    }

    public Stream<Pokemon> getStream() {
        return listaPokemon.stream();
    }

    public Pokemon getPokemon(int idPokemon) {
        return listaPokemon.get(idPokemon);
    }
    public void ponerTurno(){
        this.turno=true;
        actualizarInfo();
    }
    public void quitarTurno(){
        this.turno=false;
        actualizarInfo();
    }
    public void actualizarInfo(){
        ArrayList info=new ArrayList();
        info.add(turno);
        info.add(ganador);
        setChanged();
        notifyObservers(info);
    }
    public void actualizarVistaPokemon(){
        for (Pokemon pokemon : listaPokemon) {
            pokemon.actualizarInterfaz();
        }
    }

    public void ponerGanador() {
        ganador=true;
        actualizarInfo();
    }
}
