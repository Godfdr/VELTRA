package com.veltra.payment.ui.pockets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.SectionHeaderSmall
import com.veltra.payment.ui.VeltraTextField
import com.veltra.payment.ui.theme.*

@Composable
fun CreatePocketScreen(onBackClick: () -> Unit, onPocketCreated: () -> Unit = {}) {
    var name by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf(Icons.Default.Savings) }

    Scaffold(
        containerColor = Base,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp)
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(34.dp).background(Color.White.copy(alpha = 0.08f), CircleShape).border(1.dp, Border, CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("New Pocket", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            }
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                Button(
                    onClick = onPocketCreated,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(50.dp),
                    enabled = name.isNotEmpty() && goal.isNotEmpty()
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(if (name.isNotEmpty()) PrimaryGradient else SolidColor(Sub)), contentAlignment = Alignment.Center) {
                        Text("Create Pocket", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Set your savings goal", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            
            SectionHeaderSmall("Pocket Details")
            VeltraTextField(value = name, onValueChange = { name = it }, icon = null, placeholder = "What are you saving for?")
            VeltraTextField(value = goal, onValueChange = { goal = it }, icon = null, placeholder = "Target Amount (₦)", keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number))

            SectionHeaderSmall("Choose Icon")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                PocketIconItem(Icons.Default.Savings, selectedIcon == Icons.Default.Savings) { selectedIcon = it }
                PocketIconItem(Icons.Default.FlightTakeoff, selectedIcon == Icons.Default.FlightTakeoff) { selectedIcon = it }
                PocketIconItem(Icons.Default.Home, selectedIcon == Icons.Default.Home) { selectedIcon = it }
                PocketIconItem(Icons.Default.DirectionsCar, selectedIcon == Icons.Default.DirectionsCar) { selectedIcon = it }
                PocketIconItem(Icons.Default.School, selectedIcon == Icons.Default.School) { selectedIcon = it }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun PocketIconItem(icon: ImageVector, isSelected: Boolean, onClick: (ImageVector) -> Unit) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(if (isSelected) Royal else Card)
            .border(1.dp, if (isSelected) Royal else Border, CircleShape)
            .clickable { onClick(icon) },
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = if (isSelected) Color.White else Muted, modifier = Modifier.size(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePocketPreview() {
    VeltraTheme {
        CreatePocketScreen({})
    }
}
