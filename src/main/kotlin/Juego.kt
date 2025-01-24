package es.iesra.prog2425_ahorcado

class Juego(val palabra: Palabra, val jugador: Jugador) {

    fun iniciar(){
        println("¡Bienvenido al juego del Ahorcado!\n La palabra tiene ${palabra.palabraOculta.length} letras. ")
        do {
            println("Palabra: ${palabra.obtenerProgreso()} \n" +
            "Intentos restantes: ${jugador.intentos} \n" +
            "Letras usadads: ${jugador.obtenerLetrasUsadas()}")
            val letra =





        } while (jugador.intentos > 0 || palabra.esCompleta())



    }

    fun pedirLetra(): Char{

        var letra: Char?
            do {
            var letraValida = false
            print("Introduce una letra: ")
            letra = readln().trim().lowercase().firstOrNull()
            if (letra == null || !jugador.intentarLetra(letra)) {
                println("Letra no valida o ya utilizada. Intentalo de nuevo")
            } else {
                letraValida = true
                return letra
            }
        } while (!letraValida)
    }
}