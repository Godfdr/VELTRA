package com.veltra.payment.ui.profile

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.veltra.payment.ui.SectionHeaderSmall
import com.veltra.payment.ui.auth.AuthViewModel
import com.veltra.payment.ui.theme.*

@Composable
fun UserDetailsScreen(
    onBackClick: () -> Unit,
    onEditUsernameClick: () -> Unit = {},
    onEditPhotoClick: () -> Unit = {},
    viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()

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
                    Text("User Details", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier.size(84.dp).clip(CircleShape).background(PrimaryGradient).border(3.dp, Color.White.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.profilePhotoUri != null) {
                        AsyncImage(
                            model = state.profilePhotoUri,
                            contentDescription = "Profile Photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text("A", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                    }
                }
                Box(
                    modifier = Modifier.size(26.dp).clip(CircleShape).background(Surface).border(2.dp, Base, CircleShape).clickable { onEditPhotoClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = Teal, modifier = Modifier.size(12.dp))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Alex Veltra", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text("@${state.username}", color = Teal, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist, modifier = Modifier.clickable { onEditUsernameClick() })
            
            Surface(
                color = Teal.copy(alpha = 0.12f),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, Teal.copy(alpha = 0.25f)),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Icon(Icons.Default.Shield, contentDescription = null, tint = Teal, modifier = Modifier.size(11.dp))
                    Text("Tier 2 Verified", color = Teal, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth().background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(16.dp)).border(1.dp, Border, RoundedCornerShape(16.dp)).padding(14.dp), horizontalArrangement = Arrangement.SpaceAround) {
                UserStatItem("18", "Transactions")
                Box(modifier = Modifier.width(1.dp).height(20.dp).background(Border))
                UserStatItem("7", "Wallets")
                Box(modifier = Modifier.width(1.dp).height(20.dp).background(Border))
                UserStatItem("2", "Years Active")
            }

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeaderSmall("Account Information")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp))) {
                UserInfoRow(Icons.Default.AccountBalance, "Veltra Account No", state.accountNumber)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                UserInfoRow(Icons.Default.AlternateEmail, "Veltra Tag", "@${state.username}")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                UserInfoRow(Icons.Default.Email, "Email address", "alex@veltra.app")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                UserInfoRow(Icons.Default.Phone, "Phone number", "+234 806 554 1342")
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            SectionHeaderSmall("Security & Status")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp))) {
                UserInfoRow(Icons.Default.CreditCard, "BVN status", "Verified")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                UserInfoRow(Icons.Default.Event, "Member since", "June 2024")
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun UserStatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
        Text(label, color = Muted, fontSize = 9.5.sp, fontFamily = Urbanist)
    }
}

@Composable
fun UserInfoRow(icon: ImageVector, label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(9.dp)).background(Royal.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = Royal, modifier = Modifier.size(16.dp))
        }
        Column {
            Text(label, color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist)
            Text(value, color = Color.White, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailsPreview() {
    VeltraTheme {
        UserDetailsScreen({}, {}, {})
    }
}
