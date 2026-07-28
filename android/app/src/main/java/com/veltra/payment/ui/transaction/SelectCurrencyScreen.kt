package com.veltra.payment.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun SelectCurrencyScreen(onBackClick: () -> Unit) {
    Scaffold(
        containerColor = Base,
        topBar = {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(HeaderStart)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(HeaderGradient)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(34.dp)
                            .background(Color.White.copy(alpha = 0.08f), CircleShape)
                            .border(1.dp, Border, CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Select Currency", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            
            // Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(14.dp))
                    .border(1.dp, Border, RoundedCornerShape(14.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Muted, modifier = Modifier.size(15.dp))
                Text("Search country or currency", color = Muted, fontSize = 12.sp, fontFamily = Urbanist)
            }

            Spacer(modifier = Modifier.height(20.dp))

            SectionHeaderLabel("Popular")
            Column(modifier = Modifier.fillMaxWidth()) {
                CountryRow(flag = "🇳🇬", name = "Nigeria", code = "NGN · ₦ Naira", isSelected = true)
                CountryRow(flag = "🇺🇸", name = "United States", code = "USD · $ Dollar")
                CountryRow(flag = "🇬🇧", name = "United Kingdom", code = "GBP · £ Pound")
            }

            Spacer(modifier = Modifier.height(20.dp))

            SectionHeaderLabel("All Countries")
            Column(modifier = Modifier.fillMaxWidth()) {
                CountryRow(flag = "🇪🇺", name = "European Union", code = "EUR · € Euro")
                CountryRow(flag = "🇬🇭", name = "Ghana", code = "GHS · ₵ Cedi")
                CountryRow(flag = "🇰🇪", name = "Kenya", code = "KES · KSh Shilling")
                CountryRow(flag = "🇿🇦", name = "South Africa", code = "ZAR · R Rand")
                CountryRow(flag = "🇨🇦", name = "Canada", code = "CAD · $ Dollar")
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun SectionHeaderLabel(text: String) {
    Text(
        text = text.uppercase(),
        color = Muted,
        fontSize = 10.5.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(bottom = 4.dp, start = 2.dp),
        fontFamily = Urbanist
    )
}

@Composable
fun CountryRow(flag: String, name: String, code: String, isSelected: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 12.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Card)
                .border(1.dp, Border, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(flag, fontSize = 17.sp)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(name, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text(code, color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(PrimaryGradient),
                contentAlignment = Alignment.Center
            ) {
                Text("✓", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        } else {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, Border, CircleShape)
            )
        }
    }
    HorizontalDivider(color = Border, thickness = 1.dp)
}

@Preview
@Composable
fun SelectCurrencyPreview() {
    VeltraTheme {
        SelectCurrencyScreen({})
    }
}
