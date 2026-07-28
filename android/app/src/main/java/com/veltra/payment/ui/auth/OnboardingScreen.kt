package com.veltra.payment.ui.auth

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onGetStartedClick: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val scope = rememberCoroutineScope()

    val slides = listOf(
        OnboardingSlide(
            "Bank smarter,\nnot harder",
            "Manage your money, send and receive payments — all in one secure app.",
            Icons.Default.AccountBalanceWallet,
            Royal
        ),
        OnboardingSlide(
            "Tap & Go\nanywhere",
            "Transfer money instantly with a tap — phone to phone, card to phone, or at any NFC reader.",
            Icons.Default.PhonelinkRing,
            Teal
        ),
        OnboardingSlide(
            "Request, share\nor split money",
            "Ping a contact, share your tag, or split a group bill in seconds with anyone.",
            Icons.Default.NotificationsActive,
            InfoPurple
        ),
        OnboardingSlide(
            "Your money,\nfully protected",
            "Bank-grade encryption, biometric security, and real-time fraud detection keep you safe.",
            Icons.Default.Shield,
            WarningOrange
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Base)
    ) {
        // Dynamic Gradient Background based on page
        val currentSlide = slides[pagerState.currentPage]
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(currentSlide.color.copy(alpha = 0.22f), Base)
                    )
                )
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Skip Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    "Skip",
                    color = Muted,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Urbanist,
                    modifier = Modifier.clickable { onGetStartedClick() }
                )
            }

            // Pager for Slides
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                val slide = slides[page]
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Hero Icon
                    Box(
                        modifier = Modifier
                            .size(130.dp)
                            .clip(RoundedCornerShape(34.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(slide.color.copy(alpha = 0.25f), slide.color.copy(alpha = 0.08f))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(82.dp)
                                .clip(CircleShape)
                                .background(slide.color.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(slide.icon, contentDescription = null, tint = slide.color, modifier = Modifier.size(38.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        slide.title,
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 31.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = Urbanist
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        slide.sub,
                        color = Muted,
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Indicator Dots
            Row(
                modifier = Modifier.padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                repeat(slides.size) { index ->
                    val isActive = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .height(6.dp)
                            .width(if (isActive) 22.dp else 6.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (isActive) Teal else Card2)
                    )
                }
            }

            // CTA Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Button(
                    onClick = {
                        if (pagerState.currentPage < 3) {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        } else {
                            onGetStartedClick()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(PrimaryGradient),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if (pagerState.currentPage < 3) "Next" else "Get Started",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Urbanist
                        )
                    }
                }
            }
        }
    }
}

data class OnboardingSlide(val title: String, val sub: String, val icon: ImageVector, val color: Color)

@Preview
@Composable
fun OnboardingPreview() {
    VeltraTheme {
        OnboardingScreen({})
    }
}
