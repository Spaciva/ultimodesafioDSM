package com.spaciva.aularecursos

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var tvRecursos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { view, insets ->
            val barras = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                barras.left,
                barras.top,
                barras.right,
                barras.bottom
            )

            insets
        }

        tvRecursos = findViewById(R.id.tvRecursos)

        // Consulta la API al abrir la pantalla.
        cargarRecursos()
    }

    private fun cargarRecursos() {
        tvRecursos.setText(R.string.cargando_recursos)

        // enqueue ejecuta la petición sin bloquear la pantalla.
        RetrofitClient.servicio.listarRecursos().enqueue(
            object : Callback<List<Recurso>> {

                override fun onResponse(
                    call: Call<List<Recurso>>,
                    response: Response<List<Recurso>>
                ) {
                    if (!response.isSuccessful) {
                        val mensaje = getString(
                            R.string.error_consulta,
                            response.code()
                        )
                        mostrarError(mensaje)
                        return
                    }

                    val recursos = response.body()

                    if (recursos == null) {
                        mostrarError(
                            getString(
                                R.string.error_consulta,
                                response.code()
                            )
                        )
                        return
                    }

                    if (recursos.isEmpty()) {
                        tvRecursos.setText(R.string.sin_recursos)
                        return
                    }

                    // Convierte cada recurso en texto para mostrarlo.
                    val contenido = recursos.joinToString("\n\n") { recurso ->
                        getString(
                            R.string.formato_recurso,
                            recurso.id.orEmpty(),
                            recurso.titulo,
                            recurso.descripcion,
                            recurso.tipo,
                            recurso.enlace
                        )
                    }

                    tvRecursos.text = contenido
                }

                override fun onFailure(
                    call: Call<List<Recurso>>,
                    t: Throwable
                ) {
                    android.util.Log.e(
                        "API_RECURSOS",
                        "Error al consultar recursos",
                        t
                    )

                    mostrarError(
                        getString(R.string.error_conexion) +
                                "\n\n" + t.javaClass.simpleName +
                                ": " + t.message.orEmpty()
                    )
                }
            }
        )
    }

    private fun mostrarError(mensaje: String) {
        tvRecursos.text = mensaje
        Toast.makeText(
            this,
            mensaje,
            Toast.LENGTH_LONG
        ).show()
    }
}