package com.example.polygonspolylines

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MapScreen()
                }
            }
        }
    }
}

@Composable
fun MapScreen() {
    val context = LocalContext.current

    // Sample coordinates for the hiking trail (polyline) and park area (polygon)
    val trailPoints = listOf(
        LatLng(37.4219999, -122.0840575),
        LatLng(37.4225, -122.0842),
        LatLng(37.4230, -122.0835)
    )
    val parkArea = listOf(
        LatLng(37.424, -122.086),
        LatLng(37.424, -122.082),
        LatLng(37.421, -122.082),
        LatLng(37.421, -122.086)
    )

    // Customization
    var polylineColor by remember { mutableStateOf(Color.Red) }
    var polylineWidth by remember { mutableStateOf(10f) }
    var polygonStrokeColor by remember { mutableStateOf(Color.Blue) }
    var polygonStrokeWidth by remember { mutableStateOf(5f) }
    var polygonFillColor by remember { mutableStateOf(Color(0x330000FF)) }

    // Set up the camera centered on the trail's first point
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(trailPoints.first(), 15f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(compassEnabled = true, zoomControlsEnabled = true)
        ) {
            Polyline(
                points = trailPoints,
                color = polylineColor,
                width = polylineWidth,
                clickable = true,
                onClick = {
                    Toast.makeText(context, "Hiking Trail Info: Enjoy your hike!", Toast.LENGTH_SHORT).show()
                }
            )
            Polygon(
                points = parkArea,
                strokeColor = polygonStrokeColor,
                strokeWidth = polygonStrokeWidth,
                fillColor = polygonFillColor,
                clickable = true,
                onClick = {
                    Toast.makeText(context, "Park Area Info: Beautiful park with lots of greenery.", Toast.LENGTH_SHORT).show()
                }
            )
        }

        // Overlay UI controls for customization
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                .padding(8.dp)
                .align(Alignment.TopCenter)
        ) {
            Text("Customize Overlays", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))

            // Polyline customization
            Text("Polyline Width: ${polylineWidth.toInt()} px")
            Slider(
                value = polylineWidth,
                onValueChange = { polylineWidth = it },
                valueRange = 1f..20f,
                steps = 18
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                listOf(Color.Red, Color.Green, Color.Magenta).forEach { colorOption ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(colorOption)
                            .clickable { polylineColor = colorOption }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Polygon customization
            Text("Polygon Stroke Width: ${polygonStrokeWidth.toInt()} px")
            Slider(
                value = polygonStrokeWidth,
                onValueChange = { polygonStrokeWidth = it },
                valueRange = 1f..20f,
                steps = 18
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                listOf(Color.Blue, Color.Yellow, Color.Cyan).forEach { colorOption ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(colorOption)
                            .clickable { polygonStrokeColor = colorOption }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    MaterialTheme {
        MapScreen()
    }
}
