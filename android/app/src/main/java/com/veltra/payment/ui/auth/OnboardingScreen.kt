package com.veltra.payment.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.veltra.payment.R
import com.veltra.payment.ui.theme.*
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun OnboardingScreen(onGetStartedClick: () -> Unit) {
    val onboardingData = listOf(
        OnboardingData(
            title = "Smart Banking for\nthe Next Generation",
            description = "Experience seamless multi-currency transfers,\nsecure offline payments, and smart savings.",
            iconRes = R.drawable.ic_onboarding_offline
        ),
        OnboardingData(
            title = "Connect and Pay\nwith Social Pings",
            description = "Send and request money from friends instantly\nwith our unique Ping Me feature.",
            iconRes = R.drawable.ic_onboarding_spot
        ),
        OnboardingData(
            title = "Tap & Go\nContactless Payments",
            description = "Pay at merchants or transfer between phones\nwith a simple tap using NFC technology.",
            iconRes = R.drawable.ic_onboarding_nfc
        ),
        OnboardingData(
            title = "Personal & Group\nSmart Pockets",
            description = "Save for your goals alone or with friends\nusing our collaborative smart pockets.",
            iconRes = R.drawable.veltra_logo_type2
        )
    )

    val pagerState = rememberPagerState(pageCount = { onboardingData.size })
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize().background(Base)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.weight(1f)) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    key = { onboardingData[it].title },
                    userScrollEnabled = true
                ) { page ->
                    val data = onboardingData[page]
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 40.dp)
                            .graphicsLayer {
                                val pageOffset = (
                                    (pagerState.currentPage - page) + pagerState
                                        .currentPageOffsetFraction
                                    ).absoluteValue
                                
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                                
                                scaleX = lerp(
                                    start = 0.85f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                                scaleY = scaleX
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(80.dp))
                        
                        Box(
                            modifier = Modifier
                                .size(240.dp)
                                .clip(RoundedCornerShape(32.dp))
                                .background(Color.White.copy(alpha = 0.08f))
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = data.iconRes),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.height(60.dp))

                        Text(
                            data.title,
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center,
                            lineHeight = 36.sp,
                            fontFamily = Urbanist
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            data.description,
                            color = Muted,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp,
                            fontFamily = Urbanist
                        )
                    }
                }
            }

            // Pager Indicators
            Row(
                modifier = Modifier.height(50.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(onboardingData.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Teal else Border
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(if (pagerState.currentPage == iteration) 10.dp else 6.dp)
                    )
                }
            }

            Button(
                onClick = {
                    if (pagerState.currentPage < onboardingData.size - 1) {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    } else {
                        onGetStartedClick()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 20.dp, bottom = 40.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(50.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PrimaryGradient),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        if (pagerState.currentPage == onboardingData.size - 1) "Get Started" else "Next",
                        color = Color.White, 
                        fontSize = 16.sp, 
                        fontWeight = FontWeight.Bold, 
                        fontFamily = Urbanist
                    )
                }
            }
        }
    }
}

data class OnboardingData(val title: String, val description: String, val iconRes: Int)

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    VeltraTheme {
        OnboardingScreen({})
    }
}
