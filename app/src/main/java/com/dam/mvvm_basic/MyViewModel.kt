

package com.dam.mvvm_basic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyViewModel(): ViewModel() {

    private val TAG_LOG = "miDebug"
    val estadoActual = MutableStateFlow(Estados.INICIO)
    var _numbers = MutableStateFlow(0)

    private val _cuentaAtras = MutableStateFlow(5)
    val cuentaAtras: StateFlow<Int> = _cuentaAtras

    private var cuentaAtrasJob: Job? = null

    init {
        Log.d(TAG_LOG, "Inicializamos ViewModel - Estado: ${estadoActual.value}")
    }

    fun crearRandom() {
        cuentaAtrasJob?.cancel()
        _cuentaAtras.value = 5

        estadoActual.value = Estados.GENERANDO
        _numbers.value = (0..3).random()
        Log.d(TAG_LOG, "creamos random ${_numbers.value} - Estado: ${estadoActual.value}")
        actualizarNumero(_numbers.value)

        iniciarCuentaAtras()
    }

    fun actualizarNumero(numero: Int) {
        Log.d(TAG_LOG, "actualizamos numero en Datos - Estado: ${estadoActual.value}")
        Datos.numero = numero
        estadoActual.value = Estados.ADIVINANDO
    }

    //Esta funcion sirve para iniciar la cuenta atras, desde 5 a 1
    private fun iniciarCuentaAtras() {
        cuentaAtrasJob = viewModelScope.launch {
            //For para la cuentra atras en orden descendiente, de 5 a 1
            for (i in 5 downTo 1) {
                _cuentaAtras.value = i
                Log.d(TAG_LOG, "Cuenta atrás: $i")
                delay(1000)
            }
            //Este if sirve para comprobar si el tiempo se acabó.
            //Si el tiempo se acabó, el estado vuelve a ser INICIO
            if (estadoActual.value == Estados.ADIVINANDO) {
                Log.d(TAG_LOG, "Tiempo agotado. Volviendo a INICIO.")
                estadoActual.value = Estados.INICIO
            }
        }
    }

    fun comprobarCorrecto(ordinal: Int, enum_color: Colores): Boolean {
        cuentaAtrasJob?.cancel()

        Log.d(TAG_LOG, "comprobamos color ${enum_color.txt} con ordinal ${ordinal}")
        return if (ordinal == Datos.numero) {
            Log.d(TAG_LOG, "es correcto")
            estadoActual.value = Estados.INICIO
            Log.d(TAG_LOG, "GANAMOS - Estado: ${estadoActual.value}")
            estadosAuxiliares("Ganador")
            true
        } else {
            Log.d(TAG_LOG, "no es correcto")
            estadoActual.value = Estados.ADIVINANDO
            Log.d(TAG_LOG, "otro intento - Estado: ${estadoActual.value}")
            estadosAuxiliares("Fallo")
            false
        }
    }

    fun estadosAuxiliares(msg: String = "") {
        viewModelScope.launch {
            var estadoAux = EstadosAuxiliares.AUX1
            Log.d(TAG_LOG, "estado (corutina): ${estadoAux}")
            Log.d(TAG_LOG, "mensaje (corutina): ${msg}")
            delay(1500)
            estadoAux = EstadosAuxiliares.AUX2
            Log.d(TAG_LOG, "estado (corutina): ${estadoAux}")
            Log.d(TAG_LOG, "mensaje (corutina): ${msg}")
            delay(1500)
            estadoAux = EstadosAuxiliares.AUX3
            Log.d(TAG_LOG, "estado (corutina): ${estadoAux}")
            Log.d(TAG_LOG, "mensaje (corutina): ${msg}")
            delay(1500)
        }
    }
}