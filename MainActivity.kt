package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.properties.Delegates


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MyApp()
                }
            }
        }
    }

    private lateinit var view : WebView
    private var load by Delegates.notNull<Boolean>()
    override fun onBackPressed() {
            if(view.canGoBack())
                view.goBack()
            else
                super.onBackPressed()
    }



    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    fun MyApp(){
        var url by remember{ mutableStateOf("")}
        var load by remember{ mutableStateOf(false)}

        Column(
            Modifier.fillMaxSize().padding(),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ){
            if(load)

                AndroidView(factory = {
                    WebView(it).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = WebViewClient()
                        view = this
                        settings.javaScriptEnabled = true
                        settings.setSupportZoom(true)
                        loadUrl(url)
                    }
                }, update = {it.loadUrl(url)})


            else {
                OutlinedTextField(
                    value = url,
                    onValueChange = {
                        url = it
                    },
                    placeholder = {Text("Enter Url")}
                )
                Spacer(Modifier.height(15.dp))
                Button(onClick = { load = true }) {
                    Text("Load Url", fontSize = 15.sp, fontWeight = FontWeight.W500)
                }
            }
        }

    }



}

