package com.example.carpark

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun MainActivityComposable(
    saveClicked: () -> Unit,
    getClicked: () -> Unit,
) {
    val buttonSize = 200.dp
    val padding = 20.dp
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(padding).fillMaxSize()
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
            shape = CircleShape,
            modifier = Modifier.size(buttonSize),
            onClick = saveClicked
        ) {
            Image(
                painterResource(R.drawable.save_location),
                stringResource(R.string.add_location),
                modifier = Modifier.fillMaxSize().padding(padding)
            )
        }
        Spacer(modifier = Modifier.height(padding))
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
            shape = CircleShape,
            modifier = Modifier.size(buttonSize),
            onClick = getClicked
        ) {
            Image(
                painterResource(R.drawable.last_location),
                stringResource(R.string.take_to_location),
                modifier = Modifier.fillMaxSize().padding(padding)
            )
        }
    }
}