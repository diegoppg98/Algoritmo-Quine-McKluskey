# Algoritmo-Quine-McKluskey
Implementation in java of algorithm Quine-McKluskey
Utilización del .jar :

-Ejecución: java -jar algoritmoMcKluskey.jar

-Entrada:
	-¿Cuántas variables tiene la función?
		-Introducir un número entero positivo. Es el número de variables que poseerá la función.

	-¿Cómo se va a introducir la función?
		-Introducir la opción (1:formato no canónico, 2:maxterms o 3:minterms) de como se va a introducir la función.

	-Opción 1 (formato no canónico):
		-Introducir los caracteres deseados y que entren en las posibles opciones (A..Z). Dependiendo del número de variables elegidas se podrán introducir un número diferente de caracteres, llendo las opciones desde la A hasta Z (en ese orden). 

		-Para la introducción de variables positivas se usará el literal en minúscula (Ej: a), y para las variable negativas el literal en mayúscula (Ej: A).

		-Para la introducción de la función mediante suma de productos se introduciran los términos (literales sin separación) separados por signos de suma (Ej: aB+ab+A), y para la introducción de la función mediante producto de sumas se introduciran los términos (literales separados mediante sumas) separados por asteriscos (Ej: A+b*a+b*A). En ambos casos sin paréntesis.
	
	-Opción 2 y 3 (maxterms o minterms):
		-Introducir números enteros positivos (desde 0 hasta 2 elevado a número de variables menos 1) separados por comas.

		-Para introducir los términos NO/NI introducir números enteros positivos (desde 0 hasta 2 elevado a número de variables menos 1) separados por comas o salto de linea en el caso de que no existan.
		
-Salida:
	-Los literales negados iran seguidos de una comilla simple (Ej: A').
	-Se mostrará la salida de la función mediante minterms y maxterms en formato no canónico, y se dirá cual es la opción más simplificada.
