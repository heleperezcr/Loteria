package com.example.ladm_u2_practica1_barajaloteria

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val cartas = arrayOf(R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6)
    val sonidos = arrayOf(R.raw.b1,R.raw.b2,R.raw.b3,R.raw.b4,R.raw.b5,R.raw.b6,)
    var num = 0
    var numeros = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        comenzar.setOnClickListener {
            mostrarImagen()
            //esto es para reproducir sonidos
            //var mp = MediaPlayer.create(this, sonidos[0])
            //mp.start()'
            //
        }

        sonido.setOnClickListener {
            var mp = MediaPlayer.create(this, sonidos[0])
            mp.start()
        }

    }
    //-------------------------------------------------------------------
    //mostrar imagenes
    fun mostrarImagen() = GlobalScope.launch {
        var contador = 0

        while (contador <cartas.size){

            num = (Math.random()*5+1).toInt()
            comparaNumero()

            runOnUiThread {
                carta.setImageResource(cartas[num])
            }
            audio(num)
            contador++
            delay(2500)
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
        var c=0
        if(!numeros.size.equals(null)) {
            for (n in numeros) {
                if (num == n) {
                    c=1
                }
            }//for
            if(c==1){
                num = (Math.random() * 5 + 1).toInt()
                comparaNumero()
            }else{
                numeros.add(num)
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
}