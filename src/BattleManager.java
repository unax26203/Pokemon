import java.util.ArrayList;
import java.util.List;

public class BattleManager {
    private static BattleManager mBattleManager;
    private Pokemon pokemonEnAtaque;
    private int pokemonVivosEntrenadorEnAtaque;
    private BattleManager(){

    }
    public static BattleManager getBattleManager(){
        if(mBattleManager==null){
            mBattleManager=new BattleManager();
        }
        return mBattleManager;
    }
    public void gestionarAtaque(Pokemon pokemon,int ataque,String tipo,boolean euforico){
        pokemon.recibirDaño(ataque,tipo,euforico);
    }
    public void pokemonPulsado(int idEntrenador, String tipoEntrenador,int idPokemon){
        EntrenadorPokemon turnoActual= GameManager.getMiGameManager().getTurnoActual();
        ArrayList<Pokemon> listaPokemonEnAtaque = GameManager.getMiGameManager().getListaPokemonEnAtaque();
        //pokemonPulsado pasa la información del pokemon del entrenador que ha sido pulsado
        EntrenadorPokemon entrenadorPulsado = GameManager.getMiGameManager().getStream()
                .filter(e -> e.getId() == idEntrenador && e.getClass().getSimpleName().equals(tipoEntrenador))
                .findFirst().get();
        Pokemon pokemonPulsado = entrenadorPulsado.obtenerPokemon(idPokemon);
        //si no ha atacado el pokemon y es su turno y esta vivo lo seleccionamos
        List<EntrenadorPokemon> lEntrenadores= GameManager.getMiGameManager().getStream() // buscamos un entrenador al que atacar
                .filter( e -> e.getNumPokemonsVivos() > 0 && (!e.getClass().getSimpleName().equals("Bot") || e.getId() != turnoActual.getId())).toList();
        if(lEntrenadores.size()==0){turnoActual.ponerGanador();}
        if (!listaPokemonEnAtaque.contains(pokemonPulsado) && entrenadorPulsado.equals(turnoActual) && pokemonPulsado.estaVivo()) {
            pokemonVivosEntrenadorEnAtaque = entrenadorPulsado.getNumPokemonsVivos();
            for (int i = 0; i < entrenadorPulsado.getNumPokemonsVivos(); i++) {
                entrenadorPulsado.obtenerPokemon(i).deseleccionar();
            }
            pokemonPulsado.seleccionar(); // seleccionamos el pokemon
            pokemonEnAtaque = pokemonPulsado; //el pokemon pulsado es el que se pone en modo ataque
        }else if(pokemonEnAtaque != null && !entrenadorPulsado.equals(turnoActual) && pokemonPulsado.estaVivo() && !listaPokemonEnAtaque.contains(pokemonPulsado)){ //si el pokemon pulsado es del otro entrenador y esta vivo y no es un pokemon q, atacamos
            BattleManager.getBattleManager().gestionarAtaque(pokemonPulsado,pokemonEnAtaque.getAtaque(),pokemonEnAtaque.getTipo(),pokemonEnAtaque.estaEnEuforia());
            listaPokemonEnAtaque.add(pokemonEnAtaque); //añadimos el pokemon a la lista de pokemon en ataque
            if (pokemonEnAtaque.estaEnEuforia()) { // si el pokemon ha atacado con euforia se le quita
                pokemonEnAtaque.resetearEuforia();
            }
            for (int i = 0; i < listaPokemonEnAtaque.size(); i++) {
                listaPokemonEnAtaque.get(i).deseleccionar();
            }
            if (listaPokemonEnAtaque.size() == pokemonVivosEntrenadorEnAtaque){
                listaPokemonEnAtaque.clear();
                GameManager.getMiGameManager().gestionarTurno();
            }
            pokemonEnAtaque = null;
        }
        for (int i = 0; i < GameManager.getMiGameManager().numEntrenadores(); i++){
            EntrenadorPokemon e = GameManager.getMiGameManager().getListaEntrenadoresPokemon().get(i);
        }
    }
}
