public class PocaVida implements StateEvolucion {

    public PocaVida(){}
    @Override
    public void evolucionar(Pokemon pokemon) {
        pokemon.aumentarEstadisticas(7,5);
    }
}
