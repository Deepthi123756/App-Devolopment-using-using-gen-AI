package com.example.nammajatre

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home") {

                composable("home") { HomeScreen(navController) }
                composable("schedule") { ScheduleScreen(navController) }
                composable("lostfound") { LostFoundScreen(navController) }
                composable("map") { MapScreen(navController) }
                composable("emergency") { EmergencyScreen(navController) }
                composable("stories") { StoriesScreen(navController) }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = { navController.navigate("schedule") }) {
                Text("Live Schedule")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { navController.navigate("lostfound") }) {
                Text("Lost & Found")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { navController.navigate("map") }) {
                Text("Map")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { navController.navigate("emergency") }) {
                Text("Emergency")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { navController.navigate("stories") }) {
                Text("Cultural Stories")
            }
        }
    }
}
@Composable
fun ScheduleScreen(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {

        // Background
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        // Dark overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.5f))
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Live Schedule",
                color = androidx.compose.ui.graphics.Color.White,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔴 Ratha (LIVE)
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.8f)
                    .background(
                        androidx.compose.ui.graphics.Color.Red,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp)
            ) {
                Text("🔴 LIVE: Ratha - 4 PM", color = androidx.compose.ui.graphics.Color.White)
            }

            // Wrestling
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.8f)
                    .background(
                        androidx.compose.ui.graphics.Color.White.copy(alpha = 0.2f),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp)
            ) {
                Text("Wrestling - 6 PM", color = androidx.compose.ui.graphics.Color.White)
            }

            // Drama
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.8f)
                    .background(
                        androidx.compose.ui.graphics.Color.White.copy(alpha = 0.2f),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp)
            ) {
                Text("Drama - 8 PM", color = androidx.compose.ui.graphics.Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }
        }
    }
}

@Composable
fun LostFoundScreen(navController: NavController) {

    val context = LocalContext.current
    var text by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> imageUri = uri }

    Box(modifier = Modifier.fillMaxSize()) {

        // 🔹 Background Image
        Image(
            painter = painterResource(id = R.drawable.lost_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        // 🔥 Dark Overlay (MOST IMPORTANT)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.5f))
        )

        // 🔹 UI Content
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                "Lost & Found",
                color = androidx.compose.ui.graphics.Color.White,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { launcher.launch("image/*") }) {
                Text("Select Photo")
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (imageUri != null) {
                Text("Image Selected ✅", color = androidx.compose.ui.graphics.Color.White)
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter item details") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = androidx.compose.ui.graphics.Color.White,
                    unfocusedTextColor = androidx.compose.ui.graphics.Color.White,
                    focusedBorderColor = androidx.compose.ui.graphics.Color.White,
                    unfocusedBorderColor = androidx.compose.ui.graphics.Color.White
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                if (text.isEmpty() || imageUri == null) {
                    Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Uploaded ✅", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Submit")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }
        }
    }
}

@Composable
fun MapScreen(navController: NavController) {

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        // 🔹 Map Wallpaper Background
        Image(
            painter = painterResource(id = R.drawable.map_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        // 🔹 Light overlay (so buttons visible)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.3f))
        )

        // 🔹 UI on top
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Festival Map",
                color = androidx.compose.ui.graphics.Color.White,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                val uri = Uri.parse("geo:12.9716,77.5946?q=Festival Location")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps")
                context.startActivity(intent)
            }) {
                Text("Open Live Map")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }
        }
    }
}

@Composable
fun EmergencyScreen(navController: NavController) {

    val context = LocalContext.current
    var message by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        // 🔴 Background wallpaper
        Image(
            painter = painterResource(id = R.drawable.emergency_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 🔴 Dark overlay (important)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.5f))
        )

        // 🔴 Main Card (EDGE DESIGN)
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔥 Card with rounded edges
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.9f)
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        "🚨 Emergency Help",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text("Enter emergency message") }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(onClick = {

                        if (message.isEmpty()) {
                            Toast.makeText(context, "Enter message", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val mapLink = "https://maps.google.com/?q=12.9716,77.5946"
                        val finalMessage = "$message\nLocation: $mapLink"

                        val ref = FirebaseDatabase.getInstance()
                            .getReference("EmergencyAlerts")

                        val id = ref.push().key!!

                        val item = EmergencyItem(
                            id,
                            finalMessage,
                            System.currentTimeMillis().toString()
                        )

                        ref.child(id).setValue(item)

                        Toast.makeText(context, "Alert Sent 🚨", Toast.LENGTH_SHORT).show()

                        message = ""

                    }) {
                        Text("Send Alert")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }
        }
    }
}
@Composable
fun StoriesScreen(navController: NavController) {

    val stories = listOf(
        StoryItem(
            "Ratha Festival",
            "Chariot festival celebration",
            "https://www.google.com/imgres?q=ratha%20image&imgurl=https%3A%2F%2Fthumbs.dreamstime.com%2Fb%2Felaborately-carved-wooden-ratha-chariot-rath-yatra-hindu-traditionally-carved-wooden-ratha-chariot-rath-yatra-366247621.jpg&imgrefurl=https%3A%2F%2Fwww.dreamstime.com%2Felaborately-carved-wooden-ratha-chariot-rath-yatra-hindu-traditionally-carved-wooden-ratha-chariot-rath-yatra-image366247621&docid=jLPFHRTzyV2VAM&tbnid=-Ianbpt1YAGlnM&vet=12ahUKEwjB6rfluZyUAxVhUGcHHUOEIyMQnPAOegQIExAB..i&w=800&h=800&hcb=2&ved=2ahUKEwjB6rfluZyUAxVhUGcHHUOEIyMQnPAOegQIExAB"
        ),
        StoryItem(
            "Wrestling",
            "Village strength tradition",
            "https://images.unsplash.com/photo-1605296867304-46d5465a13f1"
        ),
        StoryItem(
            "Drama",
            "Cultural storytelling",
            "https://images.unsplash.com/photo-1503095396549-807759245b35"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            "Cultural Stories",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        stories.forEach { story ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            ) {

                Column(modifier = Modifier.padding(10.dp)) {

                    val painter = rememberAsyncImagePainter(
                        model = story.imageUrl,
                        error = painterResource(R.drawable.ratha_bg)
                    )

                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        story.title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(story.description)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}