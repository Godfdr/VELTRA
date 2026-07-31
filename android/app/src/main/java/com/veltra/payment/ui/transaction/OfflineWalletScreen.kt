package com.veltra.payment.ui.transaction

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun OfflineWalletScreen(
    onBackClick: () -> Unit,
    viewModel: OfflineSyncViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()
    OfflineWalletContent(
        state = state,
        onBackClick = onBackClick,
        onSyncClick = { viewModel.syncNow() }
    )
}

@Composable
fun OfflineWalletContent(
    state: OfflineSyncState,
    onBackClick: () -> Unit,
    onSyncClick: () -> Unit
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
                    Text("Offline Wallet", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            
            Box(
                modifier = Modifier.size(80.dp).background(InfoPurple.copy(alpha = 0.1f), CircleShape).border(1.dp, InfoPurple.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.CloudOff, contentDescription = null, tint = InfoPurple, modifier = Modifier.size(32.dp))
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Text("Veltra Offline Mode", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            Text("Payments are queued securely when you're disconnected", color = Muted, fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp), fontFamily = Urbanist)

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                modifier = Modifier.fillMaxWidth().border(1.dp, Border, RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = Card),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Pending Sync", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Text("${state.pendingTransactions} Transactions", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(top = 8.dp), fontFamily = Urbanist)
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    val buttonBrush = if (state.pendingTransactions > 0) PrimaryGradient else SolidColor(Sub)
                    Button(
                        onClick = onSyncClick,
                        modifier = Modifier.fillMaxWidth().height(54.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(),
                        shape = RoundedCornerShape(12.dp),
                        enabled = state.pendingTransactions > 0 && !state.isSyncing
                    ) {
                        Box(modifier = Modifier.fillMaxSize().background(buttonBrush), contentAlignment = Alignment.Center) {
                            if (state.isSyncing) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                            } else {
                                Text("Sync with Cloud ⚡", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                            }
                        }
                    }
                    
                    if (state.pendingTransactions > 0 && !state.isSyncing) {
                        Text(
                            "Sync will start automatically once connected",
                            color = Teal,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Urbanist,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth().background(Card2, RoundedCornerShape(16.dp)).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(Icons.Default.History, contentDescription = null, tint = Muted, modifier = Modifier.size(20.dp))
                Column {
                    Text("Last successful sync", color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
                    Text(state.lastSyncTime ?: "Never", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OfflineWalletPreview() {
    VeltraTheme {
        OfflineWalletContent(
            state = OfflineSyncState(pendingTransactions = 3, lastSyncTime = "Oct 24, 2024 - 14:20"),
            onBackClick = {},
            onSyncClick = {}
        )
    }
}
