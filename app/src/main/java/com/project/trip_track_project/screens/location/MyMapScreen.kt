package com.project.trip_track_project.screens.location

import android.R
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.DefaultSettingsProvider
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.viewport.ViewportStatus
import com.project.trip_track_project.utils.CityLocations
import com.project.trip_track_project.utils.RequestLocationPermission
import kotlinx.coroutines.launch


@OptIn(MapboxExperimental::class)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    onClickNavigate: () -> Unit = {},
) {

    val ZOOM: Double = 0.0
    val PITCH: Double = 0.0
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var permissionRequestCount by remember {
        mutableStateOf(1)
    }
    var showMap by remember {
        mutableStateOf(false)
    }
    var showRequestPermissionButton by remember {
        mutableStateOf(false)
    }
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            center(CityLocations.HELSINKI)
            zoom(ZOOM)
            pitch(PITCH)
        }
    }

    MapScaffold(
        floatingActionButton = {
            // Show locate button when viewport is in Idle state, e.g. camera is controlled by gestures.
            if (mapViewportState.mapViewportStatus == ViewportStatus.Idle) {
                FloatingActionButton(
                    onClick = {
                        mapViewportState.transitionToFollowPuckState()
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_menu_mylocation),
                        contentDescription = "Locate button"
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomBar = {
            // Show bottom bar with some information
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text("Latitude: ${mapViewportState.cameraState.center.latitude()}")
                    Text("Longitude: ${mapViewportState.cameraState.center.longitude()}")
                    Text("Zoom: ${mapViewportState.cameraState.zoom}")
                    Text("Pitch: ${mapViewportState.cameraState.pitch}")
                    Text("Bearing: ${mapViewportState.cameraState.bearing}")
                    SendLocationDetailToFirebase(
                        LocalContext.current,
                        mapViewportState.cameraState.center.latitude(),
                        mapViewportState.cameraState.center.longitude()
                    )

                }
                FloatingActionButton(onClick = { onClickNavigate() }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_dialog_map),
                        contentDescription = "Navigate"
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            RequestLocationPermission(
                requestCount = permissionRequestCount,
                onPermissionDenied = {
                    scope.launch {
                        snackbarHostState.showSnackbar("You need to accept location permissions.")
                    }
                    showRequestPermissionButton = true
                },
                onPermissionReady = {
                    showRequestPermissionButton = false
                    showMap = true
                }
            )
            if (showMap) {
                MapboxMap(
                    Modifier
                        .fillMaxSize(),
                    mapViewportState = mapViewportState,
                    locationComponentSettings = DefaultSettingsProvider.defaultLocationComponentSettings(
                        LocalDensity.current.density
                    ).toBuilder()
                        .setLocationPuck(createDefault2DPuck(withBearing = true))
                        .setPuckBearingEnabled(true)
                        .setPuckBearing(PuckBearing.HEADING)
                        .setEnabled(true)
                        .build()
                ) {
                    LaunchedEffect(Unit) {
                        mapViewportState.transitionToFollowPuckState()
                    }
                }

            }
            if (showRequestPermissionButton) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = {
                                permissionRequestCount += 1
                            }
                        ) {
                            Text("Request permission again ($permissionRequestCount)")
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




