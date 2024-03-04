public class MitadVida implements StateEvolucion {

    public MitadVida(){}
    @Override
    public void evolucionar(Pokemon pokemon) {
        pokemon.aumentarEstadisticas(5,3);
    }
}
