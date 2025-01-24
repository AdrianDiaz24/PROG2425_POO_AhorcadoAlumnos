package es.iesra.prog2425_ahorcado

class Jugador(intentos: Int) {
    var intentos = intentos
        private set

    private val letrasUsada = mutableSetOf<Char>()

    fun intentarLetra(letra: Char): Boolean{
        if (letra in letrasUsada) {
            return false
        } else {
            letrasUsada.add(letra)
            return true
        }
    }

    fun fallarIntento(){
        intentos -= 1
    }

    fun obtenerLetrasUsadas(): String {
        var mensaje = ""
        for (letra in letrasUsada){
            mensaje += "$letra  "
        }
        return mensaje
    }
}