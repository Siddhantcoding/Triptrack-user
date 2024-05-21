package com.project.trip_track_project.screens.location

import android.R
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.DefaultSettingsProvider
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.viewport.ViewportStatus
import com.project.trip_track_project.models.VehicleChoices
import com.project.trip_track_project.utils.CityLocations
import com.project.trip_track_project.utils.RequestLocationPermission
import kotlinx.coroutines.launch


@OptIn(MapboxExperimental::class, ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    state: LocationState,
    onEvent: (LocationEvent) -> Unit = {},
) {

    val context = LocalContext.current
    val zoom: Double = 0.0
    val pitch: Double = 0.0

    val snackBarHostState = remember { SnackbarHostState() }
    val driverSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            center(CityLocations.LUCKNOW)
            zoom(zoom)
            pitch(pitch)
        }
    }

    MapScaffold(
        floatingActionButton = {
            // Show locate button when viewport is in Idle state, e.g. camera is controlled by gestures.
            if (mapViewportState.mapViewportStatus == ViewportStatus.Idle) {
                FloatingActionButton(
                    onClick = { mapViewportState.transitionToFollowPuckState() }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_menu_mylocation),
                        contentDescription = "Locate button"
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)

        },
        bottomBar = {}
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            RequestLocationPermission(
                requestCount = state.permissionRequestState,
                onPermissionDenied = {
                    scope.launch {
                        snackBarHostState.showSnackbar("You need to accept location permissions.")
                    }
                    onEvent(LocationEvent.ShowRequestPermissionButton(true))
                },
                onPermissionReady = {
                    onEvent(LocationEvent.ShowRequestPermissionButton(false))
                    onEvent(LocationEvent.ShowMap(true))
                }
            )
            if (state.showMap) {
                MapboxMap(
                    Modifier.fillMaxSize(),
                    mapViewportState = mapViewportState,
                    locationComponentSettings = DefaultSettingsProvider.defaultLocationComponentSettings(
                        LocalDensity.current.density
                    ).toBuilder()
                        .setLocationPuck(createDefault2DPuck(withBearing = true))
                        .setPuckBearingEnabled(true)
                        .setPuckBearing(PuckBearing.HEADING)
                        .setEnabled(true)
                        .build(),
                ) {
                    LaunchedEffect(Unit) {
                        mapViewportState.transitionToFollowPuckState()
                    }
                    state.driverList.forEach { driver ->
                        PointAnnotation(
                            point = Point.fromLngLat(
                                driver.longitude,
                                driver.latitude
                            ),
                            iconImageBitmap = when (driver.vehicleType) {
                                VehicleChoices.E_RICKSHAW.toString() -> BitmapFactory.decodeResource(
                                    context.resources,
                                    com.project.trip_track_project.R.drawable.ecar
                                )

                                VehicleChoices.BUS.toString() -> BitmapFactory.decodeResource(
                                    context.resources,
                                    com.project.trip_track_project.R.drawable.ic_bus
                                )

                                else -> {
                                    BitmapFactory.decodeResource(
                                        context.resources,
                                        com.project.trip_track_project.R.drawable.ic_car
                                    )
                                }
                            },
                            iconSize = 1.2,
                            onClick = {
                                onEvent(LocationEvent.OnDriverSelected(driver))
                                true
                            }
                        )
                        CircleAnnotation(
                            point = Point.fromLngLat(driver.longitude, driver.latitude),
                            circleColorInt = Color.argb(50, 100, 100, 100),
                            circleRadius = 5.0,
                            circleStrokeWidth = 1.0,
                            circleStrokeColorInt = Color.argb(255, 100, 100, 100)
                        )
                    }
                }
            }
            if (state.showRequestPermissionButton) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = {
                                onEvent(LocationEvent.OnPermissionRequest(state.permissionRequestState + 1))
                            }
                        ) {
                            Text("Request permission again (${state.permissionRequestState})")
                        }
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = {
                                val intent = Intent()
                                intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                                intent.data =
                                    Uri.fromParts("package", context.packageName, null)
                                context.startActivity(intent)
                            }
                        ) {
                            Text("Show App Settings page")
                        }
                    }
                }
            }
            if (state.showDriverBottomSheet) {
                DriverBottomSheet(
                    state = state,
                    onEvent = onEvent,
                    sheetState = driverSheetState
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverBottomSheet(
    state: LocationState,
    onEvent: (LocationEvent) -> Unit,
    sheetState: SheetState,
) {
    ModalBottomSheet(
        onDismissRequest = {
            onEvent(LocationEvent.OnDriverUnSelected)
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.padding(16.dp).height(400.dp)
        ) {
            Text("Driver: ${state.selectedDriver.name}")
            Text("Vehicle: ${state.selectedDriver.vehicleType}")
            Text("Phone: ${state.selectedDriver.phoneNumber}")
            Button(onClick = {
                onEvent(LocationEvent.OnDriverUnSelected)
            }) {
                Text("Close")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScaffold(
    modifier: Modifier = Modifier,
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    val name = "Your Location"
    Scaffold(
        modifier = modifier,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(text = name)
                },
            )
        },
        bottomBar = bottomBar,
        content = content,
        snackbarHost = snackbarHost
    )
}


//@Preview
//@Composable
//private fun LocationScreenPreview() {
//    Trip_Track_ProjectTheme {
//        LocationScreen(
//            onClickNavigate = {},
//        )
//    }
//}



