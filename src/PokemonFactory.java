import java.util.Random;

public class PokemonFactory {
    private static PokemonFactory miPokemonFactory;
    private PokemonFactory(){}

    public static PokemonFactory getPokemonFactory(){
        if(miPokemonFactory==null){
            miPokemonFactory= new PokemonFactory();
        }
        return miPokemonFactory;
    }

    public Pokemon crearPokemon (/*String nombre,*/ String tipo/*, int vida, int ataque, int defensa, String ubicacionIcono*/, int idEntrenador, int idPokemon){
        Pokemon miPokemon=null;
        if(tipo.equals("TipoAgua")){ miPokemon= new TipoAgua(idEntrenador,idPokemon);}
        else if(tipo.equals("TipoFuego")){ miPokemon= new TipoFuego(idEntrenador,idPokemon);}
        else if(tipo.equals("TipoElectrico")){ miPokemon= new TipoElectrico(idEntrenador,idPokemon);}
        else if(tipo.equals("TipoPlanta")){miPokemon= new TipoPlanta(idEntrenador,idPokemon);}
        return miPokemon;
    }

    public Pokemon crearPokemonAleatorio(int idEntrenador, int idPokemon){
        Random rand= new Random();
        int tipo= rand.nextInt(4);
        Pokemon miPokemon=null;
        if(tipo==0){ miPokemon= crearPokemon("TipoAgua",idEntrenador,idPokemon);}
        else if(tipo==1){ miPokemon= crearPokemon("TipoFuego",idEntrenador,idPokemon);}
        else if(tipo==2){ miPokemon= crearPokemon("TipoElectrico",idEntrenador,idPokemon);}
        else if(tipo==3){miPokemon= crearPokemon("TipoPlanta",idEntrenador,idPokemon);}
        return miPokemon;
    }
}
