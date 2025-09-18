package com.example.practicas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicas.ui.theme.PracticasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Inicio()

                }

            }
        }
    }
}

@Composable
fun Inicio() {
    var isr by remember { mutableStateOf("") }
    var salarioBruto by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(70.dp))
        Row(){
            Image(painter = painterResource(id=R.drawable.money),
                contentDescription = null)
        }
        Column(
            modifier = Modifier.padding(10.dp, 40.dp, 0.dp, 30.dp)

        ) {
            Text(
                text = "CALCULO DEL ISR",
                fontSize = 40.sp,
                )
        }


        Row(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 30.dp)) {
            TextField(
                value = salarioBruto, onValueChange = { salarioBruto = it },
                placeholder = { Text(text = "Ingresa tu sueldo quincenal") }
            )
        }

        Row(modifier = Modifier.padding(20.dp, 5.dp, 20.dp, 20.dp)) {
            Button(
                onClick = {
                    val salBruto = salarioBruto.toDouble()
                    var isr1: Double = 0.0
                    var res = 0.0
                    var lInferior: Double = 0.0
                    var porcentaje: Double = 0.0
                    var cuota: Double = 0.0

                    when (salBruto) {
                        in 0.01..368.10 -> {
                            lInferior = 0.01
                            porcentaje = 1.92
                            cuota = 0.00
                        }
                        in 368.11..3124.35 -> {
                            lInferior = 368.11
                            porcentaje = 6.40
                            cuota = 7.05
                        }
                        in 3124.36..5490.75 -> {
                            lInferior = 3124.36
                            porcentaje = 10.88
                            cuota = 183.45
                        }
                        in 5490.76..6382.80 -> {
                            lInferior = 5490.76
                            porcentaje = 16.00
                            cuota = 441.00
                        }
                        in 6382.81..7641.90 -> {
                            lInferior = 6382.81
                            porcentaje = 17.92
                            cuota = 583.65
                        }
                        in 7641.91..15412.80 -> {
                            lInferior = 7641.91
                            porcentaje = 21.36
                            cuota = 809.25
                        }
                        in 15412.81..24292.65 -> {
                            lInferior = 15412.81
                            porcentaje = 23.52
                            cuota = 2469.15
                        }
                        in 24292.66..46378.50 -> {
                            lInferior = 24292.66
                            porcentaje = 30.00
                            cuota = 4557.75
                        }
                        in 46378.51..61838.10 -> {
                            lInferior = 46378.51
                            porcentaje = 32.00
                            cuota = 11183.40
                        }
                        in 61838.11..185514.30 -> {
                            lInferior = 61838.11
                            porcentaje = 34.00
                            cuota = 16130.55
                        }
                        in 185514.31..250000.00 -> {
                            lInferior = 185514.31
                            porcentaje = 35.00
                            cuota = 58180.35
                        }
                    }

                    isr1 = ((salBruto - lInferior) * (porcentaje / 100)) + cuota
                    res = salBruto - isr1
                    isr = isr1.toString()
                    resultado = res.toString()

                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
            )
            ) {
                Text(text = "CALCULAR")
            }
        }

        Row(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 30.dp)) {
            Text(
                text = " ISR:   ",
                fontSize = 25.sp,
            )
            TextField(
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp),
                value = isr, onValueChange = { nuevoTexto ->
                    isr = nuevoTexto
                }
            )
        }
        Row(
            modifier = Modifier
                .padding(16.dp)
                .align(
                    Alignment.CenterHorizontally
                )
        ) {
            OutlinedTextField(
                value = resultado,
                label = { Text("Sueldo Neto") },
                onValueChange = { resultado = it }
            )
        }


    }

}