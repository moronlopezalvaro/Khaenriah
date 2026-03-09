# KHAENRI`AH (DAW1) GDD

- [KHAENRI`AH (1ºDAW)GDD](#KHAENRI`AH-1ºDAW-GDD)

- [1. Concepto de Alto Nivel (The Pitch)](#1-concepto-de-alto-nivel-the-pitch)
  - [Concepto:](#concepto)
  - [Narrativa:](#narrativa)
  - [MVP:](#MVP)

- [2. Mecánicas de Juego (Gameplay)](#2-mecánicas-de-juego-gameplay)
  - [Objetivo del juego:](#objetivo-del-juego)
  - [Mapa:](#mapa)
  - [Core Loop:](#core-loop)
  - [Nave:](#nave)
  - [Alcance:](#alcance)
  - [Visibilidad:](#visibilidad)

- [3. Aspectos Técnicos (Stack Tecnológico)](#3-aspectos-técnicos-stack-tecnológico)
  - [Estructura del Proyecto:](#estructura-del-proyecto)

- [4. Bucle de Juego (Game Loop)](#4-bucle-de-juego-game-loop)

- [5. Contenido, Niveles](#5-contenido-niveles)
  - [Asset List:](#asset-list)
  - [Diseño de Nivel (mapa):](#diseño-de-nivel-mapa)


# 1. Concepto de Alto Nivel (The Pitch)

## Concepto: 

Videojuego en java (maven) de shooter espacial, en el que el jugador controla una nave espacial encargada de atacar a una oleada de enemigos

## Narrativa:

En un futuro lejano, la humanidad conseguirá expandirsee por distintos sistemas estelares. Sin embargo, una civilización de alienigenas ha comenzado a invadirnos con el objetivo de destruirnos. 
El jugador toma el control de una nave espacial que nos defiende.
Durante la misión, el jugador debe enfrentarse a múltiples oleadas de enemigos que atacarán sin descanso.
Solo los jugadores más habilidosos podrán resistir los ataques y proteger la galaxia.

## MVP:

Esta versión inicial del GDD describe los elementos básicos para un MVP. También se darán apuntes sobre posibles mejoras para versiones posteriores.

# 2. Mecánicas de Juego (Gameplay)

## Objetivo del juego:

El juego es una batalla espacial en el que la nave tiene que derrotar a los enemigos en un espacio galáctico determinado. El objetivo es ir sobreviviendo y pasando de ronda hasta llegar a la última donde te enfrentas al Jefe, si consigues derrotarlo habrás ganado la batalla.

## Mapa: 

Para el MVP vamos a tener solo un mapa cuadrado donde solo se consigue la victoria de esta forma:

Eliminando a todos los enemigos alienígenas.
Eliminando en la ronda final al Jefe.

En el segundo panel te explicará en qué consiste el mapa y lo que tienes que hacer para defender la base de la nave.

## Core Loop:

Core loop posible:
- Mover la nave
- Disparar proyectiles
- Destruir enemigos
- Ganar puntos
- Aparecen más enemigos
- Eliminar Jefe

## Nave:

Velocidad: 15 unidades/segundo
Direcciones permitidas: izquierda / derecha
Movilidad de la nave en el teclado: Flechas de dirección
Vida máxima: 100%
Tipo de disparo: láser básico
Disparo en el teclado: Ratón
Cadencia de disparo: ?????
Daño por disparo: eliminación total del enemigo
Velocidad del proyectil: ?????
Número de proyectiles simultáneos: ????
Colisión por enemigo: daño directo al jugador.


## Alcance:
Los enemigos tienen un movimiento descendente por lo que el alcance si no los matas rápido va siendo cada vez más alto.
Visibilidad:
La nave tiene completa visibilidad sobre el objetivo.

# 3. Aspectos Técnicos (Stack Tecnológico).
El proyecto se desarrolla como un videojuego 2D de disparos espaciales para escritorio, utilizando tecnologías modernas del ecosistema Java. El objetivo es crear un juego fluido con gráficos 2D, gestión de enemigos y sistema de colisiones en tiempo real.
Lenguaje: Java 25.
Gestión del Proyecto: Maven para la resolución de dependencias, compilación y empaquetado.
Se emplea Git para el control de versiones del proyecto permitiendo el seguimiento de cambios en el código y facilitando el desarrollo colaborativo.

## Estructura de Paquetes:
com.ilerna.vista: Núcleo de lógica del juego.
com.ilerna.modelos: Definición de entidades.
com.ilerna.resources: Implementación de imágenes.


# 4. Bucle de Juego (Game Loop).
El juego se inicia y aparece la portada con el Start.
Después pasa a la pantalla de diálogo, donde aparecen dos personajes que comentan el juego.
Para finalizar, pasa a la última pantalla dónde se inicia la batalla.

# 5. Contenido.
Asset List: Lista de imágenes y sonido(resources).

## Diseño de nivel(mapa):
Elementos que puede haber en un mapa.
Un boceto o descripción del único mapa.
