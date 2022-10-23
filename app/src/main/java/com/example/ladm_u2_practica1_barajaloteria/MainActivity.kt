package com.example.ladm_u2_practica1_barajaloteria

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val cartas = arrayOf(R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6)
    val sonidos = arrayOf(R.raw.b1,R.raw.b2,R.raw.b3,R.raw.b4,R.raw.b5,R.raw.b6)
    var num = 0
    var numeros = ArrayList<Int>()
    var hilo =  HiloBarajear(this)

    var contador = 0
    var terminada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //

        comenzar.isEnabled = false
        ganador.isEnabled = false
        restantes.isEnabled = false

        //boton barajear
        barajear.setOnClickListener {
            hilo.start()
            Toast.makeText(this, "Se completo el barajeo", Toast.LENGTH_LONG)
                .show()
            comenzar.isEnabled = true
            barajear.isEnabled = false
        }

        comenzar.setOnClickListener {
            mostrarImagen()
            ganador.isEnabled = true
            barajear.isEnabled = false
            comenzar.isEnabled = false
        }

        //boton Ganador
        ganador.setOnClickListener {
            terminada = true
            comenzar.isEnabled = false
            restantes.isEnabled = true
            ganador.isEnabled = false
        }

        restantes.setOnClickListener {
            Toast.makeText(this, "Mostrando cartas restantes", Toast.LENGTH_LONG)
                .show()
            mostrarImagen()
            terminada = false
            restantes.isEnabled = false
        }

    }
    //-------------------------------------------------------------------
    //mostrar imagenes
    fun mostrarImagen() = GlobalScope.launch {
        //var contador = 0

        while (contador < cartas.size && !terminada){
            //num = (Math.random()*5+1).toInt()
            //comparaNumero()
                runOnUiThread {
                    carta.setImageResource(cartas[numeros[contador]-1])
                }
                audio(numeros[contador]-1)
                contador++

                delay(2500)
            if (contador == cartas.size){
                terminada = true
                return@launch
            }
        }
        /*for(mensaje in mensajes){
            //Esto es por si traba la interfaz
            runOnUiThread {
                et_2doPlanoSincrona.text = mensaje
            }//
            delay(2500)
        }*/
    }

    //-------------------------------------------------------------------
    fun comparaNumero(){
        var existe=0
        if(!numeros.size.equals(null)) {
            for (n in numeros) {
                if (num == n) {
                    existe=1
                }
            }//for
            if(existe==1){
                num = (Math.random() * 5 + 1).toInt()
                comparaNumero()
            }else{
                numeros.add(num)
                println("número agregado: "+ num)
            }

        }

    }

    //------------------------------------------------------
    fun audio(numero:Int) {
        /*var c=0
        while(c<sonidos.size){
            var mp = MediaPlayer.create(this, sonidos[c])
            mp.start()
            c++
        }*/
        var mp = MediaPlayer.create(this, sonidos[numero])
        mp.start()

    }
    //-------------------------------------------------------
}

//------------Hilo-----------------------------------------------
class HiloBarajear(puntero:MainActivity) : Thread(){
    var p = puntero
    var cont = 0
    override fun run() {
        super.run()
        p.cartas.isEmpty()
        while (cont < p.cartas.size) {
            p.num = (Math.random()*6+1).toInt()
            runCatching {
                p.comparaNumero()
            }
            //p.comparaNumero()
            //p.numeros.add(p.num)
            cont++
            sleep(200)
        }

    }
}
//-----------------------------------------------------------
