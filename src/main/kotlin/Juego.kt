package es.iesra.prog2425_ahorcado

class Juego(val palabra: Palabra, val jugador: Jugador) {

    fun iniciar(){
        println("¡Bienvenido al juego del Ahorcado!\n La palabra tiene ${palabra.palabraOculta.length} letras. ")
        do {
            println("Palabra: ${palabra.obtenerProgreso()} \n" +
            "Intentos restantes: ${jugador.intentos} \n" +
            "Letras usadads: ${jugador.obtenerLetrasUsadas()}")
            val letra = pedirLetra()
            if (palabra.revelarLetra(letra)) {
                println("¡Bien hecho! La letra '$letra' está en la palabra.")
            } else {
                println("La letra '$letra' no está en la palabra.")
                jugador.fallarIntento()
            }
        } while (jugador.intentos > 0 || palabra.esCompleta())
        if (palabra.esCompleta()) {
            println("\n¡Felicidades! Has adivinado la palabra: ${palabra.obtenerProgreso()}")
        } else {
            println("\nLo siento, te has quedado sin intentos. La palabra era: ${palabra.palabraOculta}")
        }
    }

    fun pedirLetra(): Char?{

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
        return letra
    }

    fun preguntar(msj: String): Boolean {
        do {
            print("$msj (s/n): ")
            val respuesta = readln().trim().lowercase()
            when (respuesta) {
                "s" -> return true
                "n" -> return false
                else -> println("Respuesta no válida! Inténtelo de nuevo...")
            }
        } while (true)
    }
}