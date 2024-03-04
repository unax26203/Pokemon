import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class GameManager {
    private int milisecsTiempoLimite;
    private static GameManager miGameManager;
    private ArrayList<EntrenadorPokemon> listaEntrenadoresPokemon;
    private EntrenadorPokemon turnoActual;
    private Pokemon pokemonEnAtaque;
    private int pokemonVivosEntrenadorEnAtaque;
    private ArrayList <Pokemon> listaPokemonEnAtaque;
    private boolean primeraEjecucion;

    private TimerTask task;
    private Timer timer;
    private GameManager(){
        listaEntrenadoresPokemon = new ArrayList<EntrenadorPokemon>();
        turnoActual = null;
        pokemonEnAtaque = null;
    }
    public static GameManager getMiGameManager(){
        if (miGameManager == null){
            miGameManager = new GameManager();
        }
        return miGameManager;
    }

    public void generarEntrenadores(int numJugadores, int numNpc, int numPokemons){
        resetearJuego();
        listaPokemonEnAtaque = new ArrayList();
        for(int i= 0; i < numJugadores+numNpc; i++){
            if(i<numJugadores){
                Jugador jugador = new Jugador(i);
                jugador.añadirPokemons(numPokemons);
                listaEntrenadoresPokemon.add(jugador);
            }
            else{
                Bot bot = new Bot(i);
                bot.añadirPokemons(numPokemons);
                listaEntrenadoresPokemon.add(bot);
            }
        }
    }
    public ArrayList<EntrenadorPokemon> getListaEntrenadoresPokemon(){
        return this.listaEntrenadoresPokemon;
    }
    public void empezarJuego(int numMilisecs) throws IOException {
        listaEntrenadoresPokemon.forEach(e -> e.actualizarVistaPokemon());
        milisecsTiempoLimite = numMilisecs;
        startTimer();
        gestionarTurno();
    }

    private void resetearJuego() {
        listaEntrenadoresPokemon.clear();
        turnoActual = null;
        pokemonEnAtaque = null;
    }

    private void startTimer() {
        primeraEjecucion = true;
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if(primeraEjecucion) {
                    primeraEjecucion = false;
                    return;
                }
                gestionarTurno();
            }
        };
        timer.scheduleAtFixedRate(task,0,milisecsTiempoLimite);
    }
    public void resetearTimer() {
        task.cancel();
        timer.purge();
        startTimer();

    }

    public void gestionarTurno(){
        resetearTimer();
        if (this.getStream().filter(e -> e.getNumPokemonsVivos() > 0).count() == 1){ //si solo queda un entrenador con pokemons vivos
            //InterfazEntrenador.acabarPartida("Jugador",turnoActual.getId());
            List <EntrenadorPokemon> lEntrenadores= this.getStream() // buscamos un entrenador al que atacar
                    .filter( e -> e.getNumPokemonsVivos() > 0 && (!e.getClass().getSimpleName().equals("Jugador") || e.getId() != turnoActual.getId())).toList();
            if(lEntrenadores.size()==0){
                turnoActual.ponerGanador();
            }
        }else{
            Random random=new Random();
            listaEntrenadoresPokemon.forEach(e -> e.quitarTurno());
            turnoActual = this.listaEntrenadoresPokemon.get(random.nextInt(0, this.numEntrenadores()));
            turnoActual.ponerTurno();
            System.out.println("Turno de: " + turnoActual.id+ " " + turnoActual.getClass().getSimpleName());
            //using instanceoff to check the type of the object
            if(turnoActual instanceof Bot){
                gestionarTurnoBot();
                gestionarTurno();
            }
            this.getStream().forEach(e -> e.getStream().forEach(p -> p.deseleccionar()));
            for (int i = 0; i < this.numEntrenadores(); i++){
                EntrenadorPokemon e = this.listaEntrenadoresPokemon.get(i);
                List <EntrenadorPokemon> lEntrenadores= this.getStream()
                        .filter( m -> m.getNumPokemonsVivos() > 0 && (!m.getClass().getSimpleName().equals("Jugador") || m.getId() != turnoActual.getId())).toList();
            }
        }
    }
    public void gestionarTurnoBot() {
        Random random=new Random();
        for (int i = 0; i < turnoActual.getNumPokemons(); i++){
            if(turnoActual.obtenerPokemon(i).estaVivo()){
                List <EntrenadorPokemon> lEntrenadores= this.getStream() // buscamos un entrenador al que atacar
                        .filter( e -> e.getNumPokemonsVivos() > 0 && (!e.getClass().getSimpleName().equals("Bot") || e.getId() != turnoActual.getId())).toList();
                if (lEntrenadores.size() != 0){
                    EntrenadorPokemon entrenadorPokemonValido = lEntrenadores.get(random.nextInt(0,lEntrenadores.size()));//buscamos un pokemon al que atacar
                    List <Pokemon> lPokemon = entrenadorPokemonValido.getStream()
                            .filter(p -> p.estaVivo()).toList();
                    Pokemon pokemonAtacado = lPokemon.get(random.nextInt(0,lPokemon.size()));
                    //atacamos al pokemon
                    BattleManager.getBattleManager().gestionarAtaque(pokemonAtacado,turnoActual.obtenerPokemon(i).getAtaque(),turnoActual.obtenerPokemon(i).getTipo(),turnoActual.obtenerPokemon(i).estaEnEuforia());
                    if (turnoActual.obtenerPokemon(i).estaEnEuforia()){ //si ha atacadado con euforia, se resetea
                        turnoActual.obtenerPokemon(i).resetearEuforia();
                    }
                }else if(lEntrenadores.size() == 0){
                    turnoActual.ponerGanador();
                }
            }
        }
        for (int i = 0; i < this.numEntrenadores(); i++){
            EntrenadorPokemon e = this.listaEntrenadoresPokemon.get(i);
            List <EntrenadorPokemon> lEntrenadores= this.getStream()
                    .filter( m -> m.getNumPokemonsVivos() > 0 && (!m.getClass().getSimpleName().equals("Bot") || m.getId() != turnoActual.getId())).toList();
        }
    }


    public void botonGoPulsado(int id, String tipo){
        if (turnoActual.getId() == id && turnoActual.getClass().getSimpleName().equals(tipo)){
            gestionarTurno();
            listaPokemonEnAtaque.clear();
        }
    }
    public void addEntrenador(EntrenadorPokemon pEntrenadorPokemon){
        listaEntrenadoresPokemon.add(pEntrenadorPokemon);
    }

    public int numEntrenadores() {
        return listaEntrenadoresPokemon.size();
    }

    public Stream<EntrenadorPokemon> getStream() {
        return listaEntrenadoresPokemon.stream();
    }

    public Pokemon getPokemonDeEntrenador(int idPokemon, int idEntrenador) {
        return listaEntrenadoresPokemon.get(idEntrenador).getPokemon(idPokemon);
    }
    public EntrenadorPokemon getTurnoActual(){return this.turnoActual;}
    public ArrayList<Pokemon> getListaPokemonEnAtaque(){return this.listaPokemonEnAtaque;}
    public EntrenadorPokemon getEntrenador(int idEntrenador){
        return listaEntrenadoresPokemon.get(idEntrenador);
    }
}
