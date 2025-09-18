package com.example.practicas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicas.ui.theme.PracticasTheme
import androidx.compose.foundation.layout.aspectRatio

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var pantalla by rememberSaveable { mutableStateOf("0") }
    var operacionActual by rememberSaveable { mutableStateOf("") }
    val historialOperaciones = remember { mutableStateListOf<String>() }
    var primerNumero by rememberSaveable { mutableStateOf<Double?>(null) }
    var operacion by rememberSaveable { mutableStateOf<String?>(null) }
    var operadorPresionado by rememberSaveable { mutableStateOf(false) }
    var mostrarHistorial by rememberSaveable { mutableStateOf(false) }

    fun limpiar() {
        pantalla = "0"
        operacionActual = ""
        primerNumero = null
        operacion = null
        operadorPresionado = false
    }

    fun limpiarTodo() {
        limpiar()
        historialOperaciones.clear()
    }

    fun borrarUltimo() {
        pantalla = if (pantalla.length > 1) pantalla.dropLast(1) else "0"
    }

    fun agregarNumero(num: String) {
        pantalla = if (pantalla == "0" || operadorPresionado) {
            num
        } else {
            pantalla + num
        }
        operadorPresionado = false
    }

    fun agregarDecimal() {
        if (!pantalla.contains(".")) pantalla += "."
    }

    fun elegirOperacion(op: String) {
        val numero = pantalla.toDoubleOrNull() ?: return
        if (primerNumero == null) {
            primerNumero = numero
            operacionActual = "${formatearResultado(numero)} $op"
        } else if (operacion != null) {
            primerNumero = calcular(primerNumero!!, numero, operacion!!)
            pantalla = formatearResultado(primerNumero!!)
            operacionActual = "${formatearResultado(primerNumero!!)} $op"
        }
        operacion = op
        operadorPresionado = true
    }

    fun calcularResultado() {
        val segundoNumero = pantalla.toDoubleOrNull() ?: return
        if (primerNumero != null && operacion != null) {
            val resultado = calcular(primerNumero!!, segundoNumero, operacion!!)
            if (resultado.isNaN()) {
                pantalla = "Error"
            } else {
                val opCompleta =
                    "$operacionActual ${formatearResultado(segundoNumero)} = ${formatearResultado(resultado)}"
                historialOperaciones.add(0, opCompleta)
                pantalla = formatearResultado(resultado)
            }
            primerNumero = null
            operacion = null
            operacionActual = ""
        }
    }

    // Interfaz moderna con degradados
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF667eea),
                        Color(0xFF764ba2)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Sección superior - Pantalla y historial
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
                    .shadow(8.dp, RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Calculadora Pro",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF667eea),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Surface(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { mostrarHistorial = !mostrarHistorial },
                            color = Color(0xFF667eea).copy(alpha = 0.2f),
                            shape = CircleShape
                        ) {
                            Text(
                                text = if (mostrarHistorial) "🧮" else "📝",
                                modifier = Modifier.padding(8.dp),
                                fontSize = 16.sp
                            )
                        }
                    }

                    if (mostrarHistorial && historialOperaciones.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(historialOperaciones.take(3)) { opTxt ->
                                Text(
                                    text = opTxt,
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Operación actual
                    Text(
                        text = operacionActual,
                        fontSize = 20.sp,
                        color = Color.Gray,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )

                    // Resultado principal
                    Text(
                        text = pantalla,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2D3748),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección de botones
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
                    .shadow(8.dp, RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val botones = listOf(
                        listOf("AC", "C", "⌫", "/"),
                        listOf("7", "8", "9", "*"),
                        listOf("4", "5", "6", "-"),
                        listOf("1", "2", "3", "+"),
                        listOf("00", "0", ".", "=")
                    )

                    botones.forEach { fila ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            fila.forEach { texto ->
                                ModernButton(
                                    text = texto,
                                    onClick = {
                                        when {
                                            texto.length == 1 && texto[0].isDigit() -> agregarNumero(texto)

                                            texto == "00" -> {
                                                agregarNumero("0")
                                                agregarNumero("0")
                                            }

                                            texto == "." -> agregarDecimal()
                                            texto == "C" -> limpiar()
                                            texto == "AC" -> limpiarTodo()
                                            texto == "⌫" -> borrarUltimo()
                                            texto in listOf("+", "-", "*", "/") -> elegirOperacion(texto)
                                            texto == "=" -> calcularResultado()
                                        }
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModernButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100)
    )

    val buttonColor = when {
        text == "=" -> Color(0xFF48bb78)
        text in listOf("+", "-", "*", "/") -> Color(0xFFed8936)
        text in listOf("C", "AC", "⌫") -> Color(0xFFf56565)
        else -> Color(0xFF4a5568)
    }

    val animatedColor by animateColorAsState(
        targetValue = if (isPressed) buttonColor.copy(alpha = 0.8f) else buttonColor,
        animationSpec = tween(100)
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()      // ocupa el ancho entregado por weight
            .aspectRatio(1f)     // lo vuelve cuadrado (sin pelear con weight)
            .scale(scale)
            .shadow(
                elevation = if (isPressed) 2.dp else 6.dp,
                shape = CircleShape
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                isPressed = true
                onClick()
                isPressed = false
            },
        shape = CircleShape,
        color = animatedColor
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun calcular(a: Double, b: Double, op: String): Double {
    return when (op) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> if (b == 0.0) Double.NaN else a / b
        else -> b
    }
}

fun formatearResultado(num: Double): String {
    return if (num % 1 == 0.0) num.toInt().toString() else num.toString()
}
