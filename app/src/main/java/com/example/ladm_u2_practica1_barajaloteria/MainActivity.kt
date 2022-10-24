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

    val cartas = arrayOf(R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7,R.drawable.a8,R.drawable.a9,R.drawable.a10,
                         R.drawable.a11,R.drawable.a12,R.drawable.a13,R.drawable.a14,R.drawable.a15,R.drawable.a16,R.drawable.a17,R.drawable.a18,R.drawable.a19,R.drawable.a20,
                         R.drawable.a21,R.drawable.a22,R.drawable.a23,R.drawable.a24,R.drawable.a25,R.drawable.a26,R.drawable.a27,R.drawable.a28,R.drawable.a29,R.drawable.a30,
                         R.drawable.a31,R.drawable.a32,R.drawable.a33,R.drawable.a34,R.drawable.a35,R.drawable.a36,R.drawable.a37,R.drawable.a38,R.drawable.a39,R.drawable.a40,
                         R.drawable.a41,R.drawable.a42,R.drawable.a43,R.drawable.a44,R.drawable.a45,R.drawable.a46,R.drawable.a47,R.drawable.a48,R.drawable.a49,R.drawable.a50,
                         R.drawable.a51,R.drawable.a52,R.drawable.a53,R.drawable.a54)
    val sonidos = arrayOf(R.raw.b1,R.raw.b2,R.raw.b3,R.raw.b4,R.raw.b5,R.raw.b6,R.raw.b7,R.raw.b8,R.raw.b9,R.raw.b10,
                          R.raw.b11,R.raw.b12,R.raw.b13,R.raw.b14,R.raw.b15,R.raw.b16,R.raw.b17,R.raw.b18,R.raw.b19,R.raw.b20,
                          R.raw.b21,R.raw.b22,R.raw.b23,R.raw.b24,R.raw.b25,R.raw.b26,R.raw.b27,R.raw.b28,R.raw.b29,R.raw.b30,
                          R.raw.b31,R.raw.b32,R.raw.b33,R.raw.b34,R.raw.b35,R.raw.b36,R.raw.b37,R.raw.b38,R.raw.b39,R.raw.b40,
                          R.raw.b41,R.raw.b42,R.raw.b43,R.raw.b44,R.raw.b45,R.raw.b46,R.raw.b47,R.raw.b48,R.raw.b49,R.raw.b50,
                          R.raw.b51,R.raw.b52,R.raw.b53,R.raw.b54)
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


         //var mp: MediaPlayer? = null

        //boton barajear
        barajear.setOnClickListener {
            try{
                //numeros.clear()
                var mp: MediaPlayer = MediaPlayer.create(this, R.raw.sevaysecorre)
                mp.release()
                mp = MediaPlayer.create(this, R.raw.sevaysecorre)
                mp?.start()

                hilo.start()
                Toast.makeText(this, "Se completo el barajeo", Toast.LENGTH_LONG)
                    .show()
                comenzar.isEnabled = true
                barajear.isEnabled = false
            }catch (e: Exception){
                Toast.makeText(this, "error", Toast.LENGTH_LONG)
                    .show()
            }
        }

        comenzar.setOnClickListener {
            mostrarImagen()
            ganador.isEnabled = true
            barajear.isEnabled = false
            comenzar.isEnabled = false
        }

        //boton Ganador
        ganador.setOnClickListener {
           /* var mp = MediaPlayer.create(this, R.raw.loteria) //no se escuchó
            mp.start()*/
            terminada = true
            comenzar.isEnabled = false
            restantes.isEnabled = true
            var mp: MediaPlayer = MediaPlayer.create(this, R.raw.loteria)
            mp.release()
            mp = MediaPlayer.create(this, R.raw.loteria)
            mp?.start()
            ganador.isEnabled = false

        }

        restantes.setOnClickListener {
            Toast.makeText(this, "Mostrando cartas restantes", Toast.LENGTH_LONG)
                .show()
            mostrarImagen()
            terminada = false
            restantes.isEnabled = false
            barajear.isEnabled = true
        }

    }
    //-------------------------------------------------------------------
    //mostrar imagenes
    fun mostrarImagen() = GlobalScope.launch {
        //var contador = 0

        while (contador < cartas.size && !terminada){
            println("conteo cartas mostradas: " + (contador+1))
            //num = (Math.random()*5+1).toInt()
            //comparaNumero()
                runOnUiThread {
                    carta.setImageResource(cartas[numeros[contador] - 1])
                }
                audio(numeros[contador] - 1)
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
                num = (Math.random() * cartas.size + 1).toInt()
                comparaNumero()
            }else{
                numeros.add(num)
                println("número agregado: "+ num)
            }

        }

    }

    //------------------------------------------------------
    fun audio( numero:Int) {
        /*var c=0
        while(c<sonidos.size){
            var mp = MediaPlayer.create(this, sonidos[c])
            mp.start()
            c++
        }*/
        var mp: MediaPlayer = MediaPlayer.create(this, sonidos[numero])
       // mp.reset()
       //mp.stop()
        mp.release()

        mp = MediaPlayer.create(this, sonidos[numero])
        mp?.start()
    }
    //-------------------------------------------------------
}

//------------Hilo-----------------------------------------------
class HiloBarajear(puntero:MainActivity) : Thread(){
    var p = puntero
    var cont = 0
    override fun run() {
        super.run()
        //sleep(2000)
        //p.numeros.clear()
        while (cont < p.cartas.size) {
            p.num = (Math.random()*p.cartas.size+1).toInt()
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
