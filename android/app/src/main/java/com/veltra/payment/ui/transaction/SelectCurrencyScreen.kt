package com.veltra.payment.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.SectionHeaderSmall
import com.veltra.payment.ui.theme.*

@Composable
fun SelectCurrencyScreen(onBackClick: () -> Unit, onCurrencySelected: (CurrencyData) -> Unit = {}) {
    var searchQuery by remember { mutableStateOf("") }

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
                    Text("Select Currency", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(14.dp))
                    .border(1.dp, Border, RoundedCornerShape(14.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = null, tint = Muted, modifier = Modifier.size(18.dp))
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.weight(1f),
                    textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 13.sp, fontFamily = Urbanist),
                    cursorBrush = SolidColor(Teal),
                    decorationBox = { innerTextField ->
                        if (searchQuery.isEmpty()) Text("Search country or currency", color = Muted, fontSize = 13.sp, fontFamily = Urbanist)
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            SectionHeaderSmall("Popular")
            
            val popular = listOf(
                CurrencyData("Nigeria", "NGN", "₦", "🇳🇬"),
                CurrencyData("United States", "USD", "$", "🇺🇸"),
                CurrencyData("United Kingdom", "GBP", "£", "🇬🇧")
            )
            popular.filter { it.country.contains(searchQuery, true) || it.code.contains(searchQuery, true) }.forEach { currency ->
                CurrencyRow(currency, currency.code == "NGN") { onCurrencySelected(currency); onBackClick() }
            }

            Spacer(modifier = Modifier.height(20.dp))
            SectionHeaderSmall("All Countries")
            
            val all = listOf(
                CurrencyData("European Union", "EUR", "€", "🇪🇺"),
                CurrencyData("Ghana", "GHS", "₵", "🇬🇭"),
                CurrencyData("Kenya", "KES", "KSh", "🇰🇪"),
                CurrencyData("South Africa", "ZAR", "R", "🇿🇦"),
                CurrencyData("Canada", "CAD", "$", "🇨🇦")
            )
            all.filter { it.country.contains(searchQuery, true) || it.code.contains(searchQuery, true) }.forEach { currency ->
                CurrencyRow(currency, false) { onCurrencySelected(currency); onBackClick() }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun CurrencyRow(data: CurrencyData, isSelected: Boolean, onClick: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 12.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier.size(38.dp).clip(CircleShape).background(Card).border(1.dp, Border, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(data.flag, fontSize = 18.sp)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(data.country, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
                Text("${data.code} · ${data.symbol} ${data.code}", color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist)
            }
            Box(
                modifier = Modifier.size(20.dp).clip(CircleShape).background(if (isSelected) Royal else Color.Transparent).border(1.5.dp, if (isSelected) Royal else Border, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
            }
        }
        HorizontalDivider(color = Border)
    }
}

@Preview(showBackground = true)
@Composable
fun SelectCurrencyPreview() {
    VeltraTheme {
        SelectCurrencyScreen({})
    }
}
