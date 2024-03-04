public class TipoPlanta extends Pokemon{
    public TipoPlanta(int idEntrenador, int idPokemon){

        super(idEntrenador,idPokemon);
    }


    @Override
    public void recibirDaño(int ataque, String tipo, boolean estaEnEuforia) {
        if (estaEnEuforia){
            ataque += 100;
        }
        int dañoRecibido=0;
        if (tipo.equals("TipoFuego")){
            if (super.estaEnEuforia()){
                dañoRecibido =ataque * 2 - (defensa + 100);
                if (dañoRecibido > 0) {
                    this.vida -= dañoRecibido;
                }
            }
            else{
                dañoRecibido = ataque * 2 - defensa;
                if (dañoRecibido > 0) {
                    this.vida -= dañoRecibido;
                }
            }
        }
        else{
            if(super.estaEnEuforia()){
                dañoRecibido =ataque - (defensa + 100);
                if (dañoRecibido > 0) {
                    this.vida -= dañoRecibido;
                }
            }
            else{
                dañoRecibido = ataque - defensa;
                if (dañoRecibido > 0) {
                    this.vida -= dañoRecibido;
                }
            }
        }
        if (vida<0){
            vida=0;
            changeState(new PocaVida());
            evo.evolucionar(this);
        } else if (vida <= maxVida * 0.2 && (evo instanceof MitadVida)) {
            changeState(new PocaVida());
            evo.evolucionar(this);
        } else if (vida <= maxVida * 0.5 && (evo instanceof VidaFull)) {
            changeState(new MitadVida());
            evo.evolucionar(this);
        }
        super.ataquesRecibidos++;
        comprobarEuforia();
        super.actualizarInterfaz();
    }
}
