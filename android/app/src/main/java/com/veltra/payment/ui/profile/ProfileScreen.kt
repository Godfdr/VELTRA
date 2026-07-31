package com.veltra.payment.ui.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.VeltraFooter
import com.veltra.payment.ui.SectionHeaderSmall
import com.veltra.payment.ui.auth.AuthViewModel
import com.veltra.payment.ui.auth.AuthState
import com.veltra.payment.ui.theme.*

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    onUserDetailsClick: () -> Unit,
    onVerificationClick: () -> Unit,
    onChangePinClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onNotificationsClick: () -> Unit = {},
    viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()

    ProfileContent(
        state = state,
        onBackClick = onBackClick,
        onUserDetailsClick = onUserDetailsClick,
        onVerificationClick = onVerificationClick,
        onChangePinClick = onChangePinClick,
        onLogoutClick = onLogoutClick,
        onNotificationsClick = onNotificationsClick
    )
}

@Composable
fun ProfileContent(
    state: AuthState,
    onBackClick: () -> Unit,
    onUserDetailsClick: () -> Unit,
    onVerificationClick: () -> Unit,
    onChangePinClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    Scaffold(
        containerColor = Base,
        topBar = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().background(HeaderGradient).padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(34.dp).background(Color.White.copy(alpha = 0.08f), CircleShape).border(1.dp, Border, CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Your Profile", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        },
        bottomBar = {
            VeltraFooter(
                activeRoute = "profile",
                onDashboardClick = { },
                onPocketsClick = { },
                onProfileClick = { }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(64.dp).clip(CircleShape).background(PrimaryGradient).border(3.dp, Color.White.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("A", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Alex Veltra", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text("@${state.username}", color = Muted, fontSize = 12.sp, fontFamily = Urbanist)

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                onClick = onVerificationClick,
                color = Royal.copy(alpha = 0.15f),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, Royal.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(modifier = Modifier.size(8.dp).background(Teal, CircleShape))
                        Column {
                            Text("Final Touches ✏️", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                            Text("Just finish these quick tasks!", color = Muted, fontSize = 11.sp, fontFamily = Urbanist)
                        }
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Teal, modifier = Modifier.size(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            SectionHeaderSmall("Account")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp))) {
                ProfileRow(Icons.Default.Person, "Personal details", onUserDetailsClick)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                ProfileRow(Icons.Default.VerifiedUser, "Account verification", onVerificationClick)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                ProfileRow(Icons.Default.Notifications, "Notifications", onNotificationsClick)
            }

            Spacer(modifier = Modifier.height(20.dp))

            SectionHeaderSmall("Security")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp))) {
                ProfileRow(Icons.Default.Password, "Password", { })
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                ProfileRow(Icons.Default.LockReset, "Change PIN", onChangePinClick)
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text("Logout", color = ErrorRed, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onLogoutClick() }, fontFamily = Urbanist)
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    VeltraTheme {
        ProfileContent(
            state = com.veltra.payment.ui.auth.AuthState(username = "alexveltra"),
            onBackClick = {},
            onUserDetailsClick = {},
            onVerificationClick = {},
            onChangePinClick = {},
            onLogoutClick = {},
            onNotificationsClick = {}
        )
    }
}

@Composable
fun ProfileRow(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier.size(32.dp).clip(RoundedCornerShape(10.dp)).background(Royal.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Royal, modifier = Modifier.size(16.dp))
        }
        Text(label, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist, modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Muted, modifier = Modifier.size(16.dp))
    }
}
