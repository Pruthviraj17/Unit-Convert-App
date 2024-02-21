package com.example.unitconverterapp

import android.graphics.Paint.Style
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverterapp.ui.theme.UnitConverterAppTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.time.format.TextStyle
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {
    var inputValue by remember {
        mutableStateOf("")
    }
    var outputValue by remember {
        mutableStateOf("")
    }
    var inputUnit by remember {
        mutableStateOf("Meters")
    }
    var outputUnit by remember {
        mutableStateOf("Meters")
    }
    var iExpanded by remember {
        mutableStateOf(false)
    }
    var oExpanded by remember {
        mutableStateOf(false)
    }
    val conversionFactors = remember {
        mutableDoubleStateOf(1.0)
    }
    val oConversionFactors = remember {
        mutableDoubleStateOf(1.0)
    }

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * conversionFactors.doubleValue * 100 / oConversionFactors.doubleValue).roundToInt() / 100.0
        outputValue=result.toString()
    }

    val customTextTheme= androidx.compose.ui.text.TextStyle(
        fontFamily = FontFamily.Default,
        color = Color.Red,
        fontSize = 16.sp
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Unit Converter", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = inputValue, onValueChange = {
            // Something that executes after value changed
            inputValue = it
            convertUnits()
        })
        Spacer(modifier = Modifier.height(16.dp))
        Row {
//            val context = LocalContext.current
            Box {
                Button(onClick = {
//                    Toast.makeText(context,"Welcome to Jetpack Compose",Toast.LENGTH_LONG).show()
                    iExpanded = true
                }) {
                    Text(text = inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "")
                }
                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                    DropdownMenuItem(text = { Text("Centimeters") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Centimeters"
                            conversionFactors.doubleValue = 0.01
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(text = { Text("Meters") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Meters"
                            conversionFactors.doubleValue = 1.0
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(text = { Text("Feet") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Feet"
                            conversionFactors.doubleValue = 0.3048
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(text = { Text("Millimeters") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "Millimeters"
                            conversionFactors.doubleValue = 0.001
                            convertUnits()
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = {
                    oExpanded = true
                }) {
                    Text(text = outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    DropdownMenuItem(text = { Text("Centimeters") },
                        onClick = {
                            oExpanded=false
                            outputUnit= "Centimeters"
                            oConversionFactors.doubleValue=0.01
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(text = { Text("Meters") },
                        onClick = {
                            oExpanded=false
                            outputUnit= "Meters"
                            oConversionFactors.doubleValue=1.00
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(text = { Text("Feet") },
                        onClick = {
                            oExpanded=false
                            outputUnit= "Feet"
                            oConversionFactors.doubleValue=0.3048
                            convertUnits()
                        }
                    )
                    DropdownMenuItem(text = { Text("Millimeters") },
                        onClick = {
                            oExpanded=false
                            outputUnit= "Millimeters"
                            oConversionFactors.doubleValue=0.001
                            convertUnits()
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // result text
        Text(
            text = "Result: $outputValue $outputUnit",
            style= MaterialTheme.typography.headlineSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}