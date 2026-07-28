package com.veltra.payment

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.veltra.payment.ui.DashboardScreen
import com.veltra.payment.ui.auth.OnboardingScreen
import com.veltra.payment.ui.auth.LoginScreen
import com.veltra.payment.ui.auth.SignUpScreen
import com.veltra.payment.ui.auth.CreateUsernameScreen
import com.veltra.payment.ui.profile.UserDetailsScreen
import com.veltra.payment.ui.profile.ProfileScreen
import com.veltra.payment.ui.profile.TasksScreen
import com.veltra.payment.ui.profile.BvnVerificationScreen
import com.veltra.payment.ui.profile.AccountVerificationScreen
import com.veltra.payment.ui.profile.ChangePinScreen
import com.veltra.payment.ui.theme.VeltraTheme
import com.veltra.payment.ui.transaction.ConvertScreen
import com.veltra.payment.ui.transaction.TopUpScreen
import com.veltra.payment.ui.transaction.SelectCurrencyScreen
import com.veltra.payment.ui.transaction.ConversionSuccessScreen
import com.veltra.payment.ui.transaction.TransactionDetailScreen
import com.veltra.payment.ui.transaction.PingMeScreen
import com.veltra.payment.ui.transaction.TapGoScreen
import com.veltra.payment.ui.transaction.TransactionHistoryScreen
import com.veltra.payment.ui.transaction.PingMeHubScreen
import com.veltra.payment.ui.transaction.AnalyticsScreen
import com.veltra.payment.ui.transaction.UtilityBillsScreen
import com.veltra.payment.ui.transaction.UtilityType
import com.veltra.payment.ui.transaction.PingSentScreen
import com.veltra.payment.ui.transaction.IncomingPingScreen
import com.veltra.payment.ui.transaction.PingActivityScreen
import com.veltra.payment.ui.pockets.PocketsScreen
import com.veltra.payment.ui.pockets.PocketDetailScreen
import com.veltra.payment.ui.pockets.AddFundsPocketScreen

class VeltraMainActivity : VeltraBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            VeltraTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "onboarding") {
                    composable("onboarding") {
                        OnboardingScreen(onGetStartedClick = {
                            navController.navigate("login")
                        })
                    }
                    composable("login") {
                        LoginScreen(
                            onSignInClick = {
                                navController.navigate("dashboard") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onSignUpClick = {
                                navController.navigate("signUp")
                            }
                        )
                    }
                    composable("signUp") {
                        SignUpScreen(
                            onBackClick = { navController.popBackStack() },
                            onSignInClick = { navController.navigate("login") }
                        )
                    }
                    composable("createUsername") {
                        CreateUsernameScreen(
                            onBackClick = { navController.popBackStack() },
                            onContinueClick = { navController.navigate("dashboard") }
                        )
                    }
                    composable("dashboard") {
                        DashboardScreen(
                            onConvertClick = {
                                navController.navigate("convert")
                            },
                            onAddMoneyClick = {
                                navController.navigate("topUp")
                            },
                            onProfileClick = {
                                navController.navigate("profile")
                            },
                            onTapGoClick = {
                                navController.navigate("tapGo")
                            },
                            onHistoryClick = {
                                navController.navigate("transactionHistory")
                            },
                            onPingMeClick = {
                                navController.navigate("pingMeHub")
                            },
                            onTasksClick = {
                                navController.navigate("tasks")
                            },
                            onPocketClick = {
                                navController.navigate("pockets")
                            },
                            onDataClick = {
                                navController.navigate("dataBills")
                            },
                            onElectricityClick = {
                                navController.navigate("electricityBills")
                            }
                        )
                    }
                    composable("profile") {
                        ProfileScreen(
                            onBackClick = { navController.popBackStack() },
                            onUserDetailsClick = { navController.navigate("userDetails") },
                            onVerificationClick = { navController.navigate("accountVerification") },
                            onChangePinClick = { navController.navigate("changePin") },
                            onLogoutClick = {
                                navController.navigate("login") {
                                    popUpTo("dashboard") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("userDetails") {
                        UserDetailsScreen(onBackClick = {
                            navController.popBackStack()
                        })
                    }
                    composable("tasks") {
                        TasksScreen(
                            onBackClick = { navController.popBackStack() },
                            onBvnClick = { navController.navigate("bvnVerification") }
                        )
                    }
                    composable("bvnVerification") {
                        BvnVerificationScreen(onBackClick = {
                            navController.popBackStack()
                        })
                    }
                    composable("accountVerification") {
                        AccountVerificationScreen(onBackClick = {
                            navController.popBackStack()
                        })
                    }
                    composable("changePin") {
                        ChangePinScreen(onBackClick = {
                            navController.popBackStack()
                        })
                    }
                    composable("topUp") {
                        TopUpScreen(onBackClick = {
                            navController.popBackStack()
                        })
                    }
                    composable("convert") {
                        ConvertScreen(onBackClick = {
                            navController.popBackStack()
                        })
                    }
                    composable("selectCurrency") {
                        SelectCurrencyScreen(onBackClick = {
                            navController.popBackStack()
                        })
                    }
                    composable("conversionSuccess") {
                        ConversionSuccessScreen(onDoneClick = {
                            navController.popBackStack("dashboard", inclusive = false)
                        })
                    }
                    composable("transactionDetail") {
                        TransactionDetailScreen(onBackClick = {
                            navController.popBackStack()
                        })
                    }
                    composable("transactionHistory") {
                        TransactionHistoryScreen(onBackClick = {
                            navController.popBackStack()
                        })
                    }
                    composable("pingMeHub") {
                        PingMeHubScreen(
                            onBackClick = { navController.popBackStack() },
                            onRequestMoneyClick = { navController.navigate("pingMe") },
                            onActivityClick = { navController.navigate("pingActivity") }
                        )
                    }
                    composable("pingMe") {
                        PingMeScreen(onBackClick = {
                            navController.navigate("pingSent")
                        })
                    }
                    composable("pingSent") {
                        PingSentScreen(onCancelClick = {
                            navController.popBackStack("pingMeHub", inclusive = false)
                        })
                    }
                    composable("pingActivity") {
                        PingActivityScreen(onBackClick = {
                            navController.popBackStack()
                        })
                    }
                    composable("incomingPing") {
                        IncomingPingScreen(
                            onRejectClick = { navController.popBackStack() },
                            onPayClick = { navController.navigate("dashboard") }
                        )
                    }
                    composable("tapGo") {
                        TapGoScreen(onBackClick = {
                            navController.popBackStack()
                        })
                    }
                    composable("analytics") {
                        AnalyticsScreen(onBackClick = { navController.popBackStack() })
                    }
                    composable("dataBills") {
                        UtilityBillsScreen(type = UtilityType.DATA, onBackClick = { navController.popBackStack() })
                    }
                    composable("electricityBills") {
                        UtilityBillsScreen(type = UtilityType.ELECTRICITY, onBackClick = { navController.popBackStack() })
                    }
                    composable("pockets") {
                        PocketsScreen(
                            onBackClick = { navController.popBackStack() },
                            onPocketClick = { navController.navigate("pocketDetail") }
                        )
                    }
                    composable("pocketDetail") {
                        PocketDetailScreen(
                            onBackClick = { navController.popBackStack() },
                            onAddFundsClick = { navController.navigate("addFundsPocket") }
                        )
                    }
                    composable("addFundsPocket") {
                        AddFundsPocketScreen(
                            onBackClick = { navController.popBackStack() },
                            onDoneClick = { navController.popBackStack("pockets", inclusive = false) }
                        )
                    }
                }
            }
        }
    }
}
