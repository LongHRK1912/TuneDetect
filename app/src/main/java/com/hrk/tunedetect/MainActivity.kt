package com.hrk.tunedetect

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.Manifest.permission.ACCESS_WIFI_STATE
import android.Manifest.permission.INTERNET
import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.acrcloud.rec.ACRCloudClient
import com.acrcloud.rec.ACRCloudConfig
import com.acrcloud.rec.ACRCloudResult
import com.acrcloud.rec.IACRCloudListener
import com.hrk.tunedetect.ui.theme.TuneDetectTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject

class MainActivity : ComponentActivity(), IACRCloudListener {
    private val TAG = "asdasdasdasdasd"

    private var mProcessing = false
    private var mAutoRecognizing = false
    private var initState = false

    private var path = ""

    private var startTime: Long = 0
    private val stopTime: Long = 0

    private val PRINT_MSG = 1001

    private var mConfig: ACRCloudConfig = ACRCloudConfig()
    private var mClient: ACRCloudClient = ACRCloudClient()

    private var mVolume = MutableStateFlow("")
    private var mResult = MutableStateFlow("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            verifyPermissions()

            this.mConfig.acrcloudListener = this
            this.mConfig.context = this

            this.mConfig.host = "identify-ap-southeast-1.acrcloud.com"
            this.mConfig.accessKey = "c38a8e285089e689e5abb33385e8bea2"
            this.mConfig.accessSecret = "eRHYxaSaMLV6zIgehvsIqnqgGE3w4HX5i369e8Yg"

            this.mConfig.recorderConfig.isVolumeCallback = true

            this.mClient = ACRCloudClient()
            this.initState = this.mClient.initWithConfig(this.mConfig)

            TuneDetectTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val result by mResult.collectAsState()
                    val volume by mVolume.collectAsState()

                    Text(
                        result
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        volume
                    )

                    Button(
                        onClick = {
                            start()
                        }
                    ) {
                        Text("Start")
                    }

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = {
                            cancel()
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            }

        }
    }

    private fun start() {
        if (!this.initState) {
            Toast.makeText(this, "init error", Toast.LENGTH_SHORT).show()
            return
        }

        if (!mProcessing) {
            mProcessing = true
            mVolume.value = ""
            mResult.value = ""
            if (!this.mClient.startRecognize()) {
                mProcessing = false
                Toast.makeText(this, "start error!", Toast.LENGTH_SHORT).show()
            }
            startTime = System.currentTimeMillis()
        }
    }

    private fun cancel() {
        if (mProcessing) {
            this.mClient.cancel()
        }

        this.reset()
    }

    private fun reset() {
        mProcessing = false
    }

    override fun onResult(results: ACRCloudResult?) {
        this.reset()
        mResult.value = results?.result.toString()
        Log.d(TAG, "onResult: ${JSONObject(results!!.result)}")
        startTime = System.currentTimeMillis()
    }

    override fun onVolumeChanged(curVolume: Double) {

    }

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS = arrayOf(
        ACCESS_NETWORK_STATE,
        ACCESS_WIFI_STATE,
        INTERNET,
        RECORD_AUDIO
    )

    private fun verifyPermissions() {
        for (i in PERMISSIONS.indices) {
            val permission = ActivityCompat.checkSelfPermission(this, PERMISSIONS[i])
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this, PERMISSIONS,
                    REQUEST_EXTERNAL_STORAGE
                )
                break
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.e("MainActivity", "release")
        this.mClient.release()
        this.initState = false
    }
}