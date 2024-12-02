package pl.meksu.rentcar.presentation.logged_user_activity

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.paypal.android.corepayments.CoreConfig
import com.paypal.android.corepayments.Environment
import com.paypal.android.corepayments.PayPalSDKError
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutClient
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutFundingSource
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutListener
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutRequest
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutResult
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONObject
import pl.meksu.rentcar.common.Constants
import pl.meksu.rentcar.presentation.ui.theme.RentCarTheme
import java.util.UUID

@AndroidEntryPoint
class LoggedUserActivity : AppCompatActivity() {
    var accessToken = ""
    private lateinit var uniqueId: String
    private var orderId = ""

    private val loggedUserViewModel: LoggedUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen()

        setContent {
            RentCarTheme {
                LoggedUserView(
                    onPayClick = { id, price ->
                        startOrder(id, price)
                    },
                    viewModel = loggedUserViewModel
                )
            }
        }
        fetchAccessToken()
    }

    private fun handlerOrderID(orderID: String) {
        val config = CoreConfig(Constants.CLIENT_ID, environment = Environment.SANDBOX)
        val payPalWebCheckoutClient = PayPalWebCheckoutClient(this@LoggedUserActivity, config, Constants.RETURN_URL)
        payPalWebCheckoutClient.listener = object : PayPalWebCheckoutListener {
            override fun onPayPalWebSuccess(result: PayPalWebCheckoutResult) {}

            override fun onPayPalWebFailure(error: PayPalSDKError) {}

            override fun onPayPalWebCanceled() {}
        }

        orderId = orderID
        val payPalWebCheckoutRequest =
            PayPalWebCheckoutRequest(orderID, fundingSource = PayPalWebCheckoutFundingSource.PAYPAL)
        payPalWebCheckoutClient.start(payPalWebCheckoutRequest)

    }

    private fun startOrder(id: Int, price: String) {
        loggedUserViewModel.setReservationId(id)
        uniqueId = UUID.randomUUID().toString()

        val orderRequestJson = JSONObject().apply {
            put("intent", "CAPTURE")
            put("purchase_units", JSONArray().apply {
                put(JSONObject().apply {
                    put("reference_id", uniqueId)
                    put("amount", JSONObject().apply {
                        put("currency_code", "PLN")
                        put("value", price)
                    })
                })
            })
            put("payment_source", JSONObject().apply {
                put("paypal", JSONObject().apply {
                    put("experience_context", JSONObject().apply {
                        put("payment_method_preference", "IMMEDIATE_PAYMENT_REQUIRED")
                        put("brand_name", "SH Developer")
                        put("locale", "pl-PL")
                        put("landing_page", "LOGIN")
                        put("shipping_preference", "NO_SHIPPING")
                        put("user_action", "PAY_NOW")
                        put("return_url", Constants.RETURN_URL)
                        put("cancel_url", "https://example.com/cancelUrl")
                    })
                })
            })
        }

        AndroidNetworking.post("https://api-m.sandbox.paypal.com/v2/checkout/orders")
            .addHeaders("Authorization", "Bearer $accessToken")
            .addHeaders("Content-Type", "application/json")
            .addHeaders("PayPal-Request-Id", uniqueId)
            .addJSONObjectBody(orderRequestJson)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    handlerOrderID(response.getString("id"))
                }

                override fun onError(error: ANError) {
                }
            })
    }

    private fun fetchAccessToken() {
        val clientId = Constants.CLIENT_ID
        val secretId = Constants.SECRET_ID
        val authString = "$clientId:$secretId"
        val encodedAuthString = Base64.encodeToString(authString.toByteArray(), Base64.NO_WRAP)

        AndroidNetworking.post("https://api-m.sandbox.paypal.com/v1/oauth2/token")
            .addHeaders("Authorization", "Basic $encodedAuthString")
            .addHeaders("Content-Type", "application/x-www-form-urlencoded")
            .addBodyParameter("grant_type", "client_credentials")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    accessToken = response.getString("access_token")
                }

                override fun onError(error: ANError) {

                }
            })
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.data!!.getQueryParameter("opType") == "payment") {
            captureOrder(orderId)
            loggedUserViewModel.setNavigateToReservations()
        } else if (intent.data!!.getQueryParameter("opType") == "cancel") {
            Toast.makeText(this, "Płatność anulowana", Toast.LENGTH_SHORT).show()
            loggedUserViewModel.setNavigateToReservations()
        }
    }

    private fun captureOrder(orderID: String) {
        AndroidNetworking.post("https://api-m.sandbox.paypal.com/v2/checkout/orders/$orderID/capture")
            .addHeaders("Authorization", "Bearer $accessToken")
            .addHeaders("Content-Type", "application/json")
            .addJSONObjectBody(JSONObject())
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val orderId = response.optString("id", "Unknown ID")
                    loggedUserViewModel.addPayment(orderId)
                    Toast.makeText(this@LoggedUserActivity, "Płatność zakończona sukcesem!", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: ANError) {
                    Toast.makeText(this@LoggedUserActivity, "Płatność nie powiodła się!", Toast.LENGTH_SHORT).show()
                }
            })
    }
}