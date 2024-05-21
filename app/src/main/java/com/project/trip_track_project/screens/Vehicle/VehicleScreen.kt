package com.project.trip_track_project.screens.Vehicle

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.trip_track_project.models.Driver
import com.project.trip_track_project.models.StopLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleScreen(
    modifier: Modifier = Modifier,
    state: VehicleState,
    onEvent: (VehicleEvent) -> Unit = {},
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 10.dp, // BottomSheet's height when it's collapsed
        sheetShadowElevation = 12.dp,
        sheetContent = { StopSheet(state = state, onEvent = onEvent, scope = scope) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Vehicle: ${state.vehicleType.toString()}",
                style = MaterialTheme.typography.headlineMedium
            )
            // available drivers count
            Text(
                text = "Available Drivers: ${state.driverList.size}",
                style = MaterialTheme.typography.bodySmall
            )

            LazyColumn {
                items(state.driverList) { driver ->
                    DriverItem(
                        driver = driver,
                        onEvent = onEvent,
                        scaffoldState = scaffoldState,
                        scope = scope
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverItem(
    driver: Driver,
    onEvent: (VehicleEvent) -> Unit,
    scaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = {
            onEvent(VehicleEvent.OnDriverSelected(driver))
            scope.launch {
                scaffoldState.bottomSheetState.expand()
            }
        }) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = when (driver.vehicleType) {
                    "Car" -> painterResource(id = com.project.trip_track_project.R.drawable.ic_car)
                    "Bus" -> painterResource(id = com.project.trip_track_project.R.drawable.ic_bus)
                    else -> painterResource(id = com.project.trip_track_project.R.drawable.ic_car)
                },
                contentDescription = when (driver.vehicleType) {
                    "Car" -> "Car"
                    "Bus" -> "Bus"
                    else -> "Car"
                },
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = driver.name, style = MaterialTheme.typography.bodyMedium)
                Text(text = driver.vehicleType, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


@Composable
fun StopSheet(state: VehicleState, onEvent: (VehicleEvent) -> Unit, scope: CoroutineScope) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selected Driver: ${state.selectedDriver.name}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Vehicle Type: ${state.selectedDriver.vehicleType}",
            style = MaterialTheme.typography.bodySmall
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Current Location",
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Latitude: ${state.selectedDriver.latitude}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Longitude: ${state.selectedDriver.longitude}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Address: ${state.selectedDriver.address}",
                style = MaterialTheme.typography.bodySmall
            )
        }

        LazyColumn {
            if (state.selectedDriver.stops.isNotEmpty()) {
                items(state.selectedDriver.stops) { stop ->
                    StopItem(stop = stop, onEvent = onEvent, scope = scope)
                }
            } else {
                item {
                    Text(text = "No stops available", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StopItem(stop: StopLocation, onEvent: (VehicleEvent) -> Unit, scope: CoroutineScope) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = {
            val navIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=${stop.latitude},${stop.longitude}")
            )
            context.startActivity(navIntent)
        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stop.address,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Latitude: ${stop.latitude}",
                    fontSize = 8.sp
                )
                Text(
                    text = "Longitude: ${stop.longitude}",
                    fontSize = 8.sp
                )

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Navigate",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(text = "Navigate", fontSize = 8.sp)
            }
        }
    }
}

@Preview
@Composable
private fun StopItemPreview() {
    StopItem(
        stop = StopLocation(
            address = "1234 Main St",
            latitude = 0.0,
            longitude = 0.0
        ),
        onEvent = {},
        scope = rememberCoroutineScope()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun DriverItemPreview() {
    DriverItem(
        driver = Driver(
            name = "John Doe",
            vehicleType = "Car",
            stops = emptyList()
        ),
        onEvent = {},
        scaffoldState = rememberBottomSheetScaffoldState(),
        scope = rememberCoroutineScope()
    )
}

@Preview
@Composable
private fun StopSheetPreview() {
    StopSheet(
        state = VehicleState(
            driverList = emptyList(),
            selectedDriver = Driver(
                name = "John Doe",
                vehicleType = "Car",
                address = "1234 Main St",
                stops = listOf(
                    StopLocation(
                        address = "1234 Main St",
                        latitude = 0.0,
                        longitude = 0.0
                    )
                )
            )
        ),
        onEvent = {},
        scope = rememberCoroutineScope()
    )
}