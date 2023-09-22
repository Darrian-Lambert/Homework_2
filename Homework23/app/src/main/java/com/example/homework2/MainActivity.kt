@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.homework2

import android.icu.text.NumberFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homework2.ui.theme.Homework2Theme
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Homework2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator() {
    var loan by remember { mutableStateOf("") }
    var rate by remember { mutableStateOf("") }
    var years by remember { mutableStateOf("") }

    val mortgage = if (loan.isNotEmpty() && rate.isNotEmpty() && years.isNotEmpty()) {
        calculateMortgage(loan.toInt(), rate.toDouble(), years.toInt())
    } else {
        "$0.00"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )

        TextField(
            value = loan,
            onValueChange = { loan = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "test"
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.b1),
                    color = Color.Gray
                )
            }
        )

        TextField(
            value = rate,
            onValueChange = { rate = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            leadingIcon = { //imageVector = Icons.Filled.Email
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "test"
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.b2),
                    color = Color.Gray
                )
            }
        )

        TextField(
            value = years,
            onValueChange = { years = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            leadingIcon = { //imageVector = Icons.Filled.Email
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "test"
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.b3),
                    color = Color.Gray
                )
            }
        )

        Text(
            text = "Monthly Payment: $mortgage",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


private fun calculateMortgage(p: Int, r: Double, n: Int): String {
    val nr = r / 100.0 / 12.0 //rate to decimal
    val nn = n * 12 //years to months

    var mortgage = p * ( (nr*(1.0 + nr).pow(nn)) / ((1.0 + nr).pow(nn) - 1) )

    if (mortgage.isNaN() || mortgage.isInfinite()) mortgage = 0.0
    return NumberFormat.getCurrencyInstance().format(mortgage)
}