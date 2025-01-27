package es.iesra.prog2425_ahorcado


import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.runBlocking


class Palabra(val palabraOculta: String) {
    private val progreso: Array<Char> = Array(palabraOculta.length){'_'}

    companion object{
        fun generarPalabras(cantidad: Int, tamanioMin: Int, tamanioMax: Int, idioma: Idioma = Idioma.ES): MutableSet<Palabra> {
            val client = HttpClient {
                install(ContentNegotiation) {
                    gson()
                }
            }

            val palabras = mutableSetOf<Palabra>() // Usamos un conjunto para evitar repeticiones
            val url = "https://random-word-api.herokuapp.com/word?number=${cantidad * 5}&lang=${idioma.codigo}"

            val patron = if (idioma == Idioma.ES) {
                "^[a-záéíóúüñ]+$"
            } else {
                "^[a-z]+$"
            }

            runBlocking {
                try {
                    while (palabras.size < cantidad) {
                        // Hacemos la solicitud GET
                        val respuesta: Array<String> = client.get(url).body()

                        // Filtramos las palabras según las condiciones
                        val filtradas = respuesta
                            .map { it.trim().lowercase() } // Convertimos a minúsculas
                            .filter { it.length in tamanioMin..tamanioMax } // Filtramos por tamaño
                            .filter { it.matches(Regex(patron)) } // Solo letras
                            .filter { !it.contains(" ") } // Excluye palabras que contengan espacios
                            .map { Palabra(it) } // Mapeamos a la data class

                        palabras.addAll(filtradas)
                    }
                } catch (e: Exception) {
                    println("Error al obtener las palabras: ${e.message}")
                }
            }

            client.close()
            return palabras.take(cantidad).toMutableSet()
        }
    }


    fun revelarLetra(letra: Char): Boolean {
        var i = 0
        for (letraPalabra in palabraOculta) {

            if (letra.quitarAcentos() == letraPalabra.quitarAcentos()) {
                //if (letra == letraPalabra) {
                    progreso[i] = letraPalabra
                //} else progreso[i]  = letra.quitarAcentos()
            }
            i++
        }
        if (letra in palabraOculta){
            return true
        } else {
            return false
        }
    }

    fun obtenerProgreso(): String{
        var mensaje = ""
        for (letra in progreso){
            mensaje += "$letra  "
        }
        return mensaje
    }

    fun esCompleta(): Boolean{

        // if('_' in progreso)

        if (!progreso.all { it.isLetter() }) {
            return false
        } else {
            return true
        }
    }



}