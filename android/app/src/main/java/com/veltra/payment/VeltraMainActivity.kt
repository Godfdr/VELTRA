package com.veltra.payment

import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.veltra.payment.data.VeltraRepository
import com.veltra.payment.ui.*
import com.veltra.payment.ui.auth.*
import com.veltra.payment.ui.profile.*
import com.veltra.payment.ui.theme.Base
import com.veltra.payment.ui.theme.VeltraTheme
import com.veltra.payment.ui.transaction.*
import com.veltra.payment.ui.pockets.*

class VeltraMainActivity : VeltraBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val app = application as VeltraApplication
        val repository = VeltraRepository(applicationContext)

        setContent {
            VeltraTheme {
                val mainViewModel: MainViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(repository) as T
                    }
                )
                
                val startState by mainViewModel.startDestination.collectAsState()

                if (startState is StartDestination.Ready) {
                    VeltraAppContent(repository, (startState as StartDestination.Ready).route, mainViewModel)
                } else {
                    Box(modifier = Modifier.fillMaxSize().background(Base))
                }
            }
        }
    }
}

@Composable
fun VeltraAppContent(repository: VeltraRepository, startDestination: String, mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current
    
    val authViewModel: AuthViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T = AuthViewModel(repository) as T
        }
    )
    
    val pinViewModel: ChangePinViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T = ChangePinViewModel(repository) as T
        }
    )

    val dashboardViewModel: DashboardViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T = DashboardViewModel(repository) as T
        }
    )

    val conversionViewModel: ConversionViewModel = viewModel()
    val airtimeViewModel: AirtimeViewModel = viewModel()
    val addFundsViewModel: AddFundsViewModel = viewModel()
    
    var isSelectingFromCurrency by remember { mutableStateOf(true) }
    var receiptData by remember { mutableStateOf<Map<String, String>?>(null) }

    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { authViewModel.updateProfilePhoto(it) }
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("onboarding") {
            OnboardingScreen(onGetStartedClick = {
                mainViewModel.setOnboardingComplete()
                navController.navigate("login") {
                    popUpTo("onboarding") { inclusive = true }
                }
            })
        }
        composable("login") {
            LoginScreen(
                onSignInClick = {
                    mainViewModel.setLoggedIn(true)
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
                onSignInClick = { navController.navigate("login") },
                onCreateAccountClick = { navController.navigate("createUsername") },
                viewModel = authViewModel
            )
        }
        composable("createUsername") {
            CreateUsernameScreen(
                onBackClick = { 
                    if (navController.previousBackStackEntry?.destination?.route == "userDetails") {
                        authViewModel.saveUpdatedUsername()
                    }
                    navController.popBackStack() 
                },
                onContinueClick = { 
                    if (navController.previousBackStackEntry?.destination?.route == "userDetails") {
                        authViewModel.saveUpdatedUsername()
                        navController.popBackStack()
                    } else {
                        mainViewModel.setOnboardingComplete()
                        mainViewModel.setLoggedIn(true)
                        navController.navigate("dashboard") {
                            popUpTo("signUp") { inclusive = true }
                        }
                    }
                },
                viewModel = authViewModel
            )
        }
        composable("dashboard") {
            DashboardScreen(
                onConvertClick = { navController.navigate("convert") },
                onAddMoneyClick = { navController.navigate("topUp") },
                onProfileClick = { navController.navigate("profile") },
                onTapGoClick = { navController.navigate("tapGo") },
                onHistoryClick = { navController.navigate("transactionHistory") },
                onPingMeClick = { navController.navigate("pingMeHub") },
                onTasksClick = { navController.navigate("tasks") },
                onPocketClick = { navController.navigate("pockets") },
                onDataClick = { navController.navigate("dataBills") },
                onElectricityClick = { navController.navigate("electricityBills") },
                onAirtimeClick = { navController.navigate("airtime") },
                viewModel = dashboardViewModel
            )
        }
        composable("profile") {
            ProfileScreen(
                onBackClick = { navController.popBackStack() },
                onUserDetailsClick = { navController.navigate("userDetails") },
                onVerificationClick = { navController.navigate("accountVerification") },
                onChangePinClick = { navController.navigate("changePin") },
                onLogoutClick = {
                    mainViewModel.setLoggedIn(false)
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onNotificationsClick = { navController.navigate("notifications") },
                viewModel = authViewModel
            )
        }
        composable("userDetails") {
            UserDetailsScreen(
                onBackClick = { navController.popBackStack() },
                onEditUsernameClick = { navController.navigate("createUsername") },
                onEditPhotoClick = { photoLauncher.launch("image/*") },
                viewModel = authViewModel
            )
        }
        composable("tasks") {
            TasksScreen(
                onBackClick = { navController.popBackStack() },
                onBvnClick = { navController.navigate("bvnVerification") }
            )
        }
        composable("bvnVerification") {
            BvnVerificationScreen(onBackClick = { navController.popBackStack() })
        }
        composable("accountVerification") {
            AccountVerificationScreen(onBackClick = { navController.popBackStack() })
        }
        composable("changePin") {
            ChangePinScreen(
                onBackClick = { navController.popBackStack() },
                viewModel = pinViewModel
            )
        }
        composable("topUp") {
            TopUpScreen(onBackClick = { navController.popBackStack() })
        }
        composable("convert") {
            ConvertScreen(
                onBackClick = { navController.popBackStack() },
                onSelectCurrency = { isFrom ->
                    isSelectingFromCurrency = isFrom
                    navController.navigate("selectCurrency")
                },
                onConvertSuccess = { 
                    receiptData = mapOf(
                        "amount" to conversionViewModel.state.value.fromAmount,
                        "recipient" to conversionViewModel.state.value.toCurrency.code,
                        "type" to "Currency Conversion"
                    )
                    navController.navigate("conversionSuccess") 
                },
                viewModel = conversionViewModel
            )
        }
        composable("selectCurrency") {
            SelectCurrencyScreen(
                onBackClick = { navController.popBackStack() },
                onCurrencySelected = { currency ->
                    if (isSelectingFromCurrency) {
                        conversionViewModel.updateFromCurrency(currency)
                    } else {
                        conversionViewModel.updateToCurrency(currency)
                    }
                }
            )
        }
        composable("conversionSuccess") {
            ConversionSuccessScreen(
                onDoneClick = {
                    navController.popBackStack("dashboard", inclusive = false)
                },
                onViewReceiptClick = {
                    navController.navigate("receipt")
                },
                viewModel = conversionViewModel
            )
        }
        composable("receipt") {
            ReceiptScreen(
                amount = receiptData?.get("amount") ?: "0",
                recipient = receiptData?.get("recipient") ?: "Unknown",
                type = receiptData?.get("type") ?: "Transfer",
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("transactionDetail") {
            TransactionDetailScreen(onBackClick = { navController.popBackStack() })
        }
        composable("transactionHistory") {
            TransactionHistoryScreen(onBackClick = { navController.popBackStack() })
        }
        composable("pingMeHub") {
            PingMeHubScreen(
                onBackClick = { navController.popBackStack() },
                onRequestMoneyClick = { navController.navigate("pingMe") },
                onActivityClick = { navController.navigate("pingActivity") }
            )
        }
        composable("pingMe") {
            PingMeScreen(
                onBackClick = { navController.popBackStack() },
                onSendPingClick = { navController.navigate("pingSent") }
            )
        }
        composable("pingSent") {
            PingSentScreen(onCancelClick = {
                navController.popBackStack("pingMeHub", inclusive = false)
            })
        }
        composable("pingActivity") {
            PingActivityScreen(onBackClick = { navController.popBackStack() })
        }
        composable("incomingPing") {
            IncomingPingOverlay(
                isVisible = true,
                onRejectClick = { navController.popBackStack() },
                onPayClick = { navController.navigate("dashboard") }
            )
        }
        composable("tapGo") {
            TapGoScreen(
                onBackClick = { navController.popBackStack() },
                onStartTapClick = { 
                    context.startActivity(android.content.Intent(context, VeltraNFCPaymentActivity::class.java))
                }
            )
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
                onPocketClick = { navController.navigate("pocketDetail") },
                onAddPocketClick = { navController.navigate("createPocket") }
            )
        }
        composable("createPocket") {
            CreatePocketScreen(
                onBackClick = { navController.popBackStack() },
                onPocketCreated = { navController.popBackStack("pockets", inclusive = false) }
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
                onDoneClick = { navController.popBackStack("pockets", inclusive = false) },
                viewModel = addFundsViewModel
            )
        }
        
        // Service Hubs
        composable("notifications") { NotificationsScreen({ navController.popBackStack() }) }
        composable("airtime") { AirtimeScreen(onBackClick = { navController.popBackStack() }, viewModel = airtimeViewModel) }
        composable("cable") { GenericPlaceholderScreen("Cable TV", { navController.popBackStack() }) }
        composable("education") { GenericPlaceholderScreen("Education", { navController.popBackStack() }) }
        composable("support") { SupportHubScreen({ navController.popBackStack() }) }
    }
}
