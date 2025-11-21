# Ejercicio 1

Implementa una cuenta atrás en el código (5...4...3...2...1)
Utiliza los Estados auxiliares para la cuenta atrás
Configura un cuadro de texto para mostrar la cuenta atrás
Cuando el usuario le da al "Start" empieza la cuenta atrás
Si la cuenta atrás llega a uno y el usuario aun no acertó, la app vuelve al estado INICIO

# Ejercicio 2

Modifica la aplicación para que el ViewModel sea un singleton

# Ejercicio 3

Añade a los estados auxiliares (sin cambiar el enum) una función própia que tendrá como parámetro una String y que devuelva una String

En el estado AUX1 devolverá la string sin modificarla
En el estado AUX2 devolverá la string en minúsculas
En el estado AUX3 devolverá la string en mayúscula
En la función estadosAuxiliares(msg: String = ""):

El logcat (mensaje (corutina))debe usar lo que devuelve las funciones de los estados para imprimir el mensaje (msg)