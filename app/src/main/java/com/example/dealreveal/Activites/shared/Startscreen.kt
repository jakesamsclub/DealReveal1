package com.example.dealreveal.Activites.shared


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dealreveal.Activites.client.BusinessSigninActivity
import com.example.dealreveal.Activites.users.LoginActivity
import com.example.dealreveal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


var userlong = ""
var userlat = ""
class Startscreen : AppCompatActivity(), LocationListener {

    var clickCount = 0
    private lateinit var auth: FirebaseAuth
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    val db = FirebaseFirestore.getInstance()

    var simpleVideoView: VideoView? = null

    var mediaControls: MediaController? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startscreen)
        buttonsetup()
        getLocationsafetycheck()
        video()
        
        }

    fun video() {


        val buttonTxt = findViewById<TextView>(R.id.textView18)


        simpleVideoView = findViewById<View>(R.id.videoView) as VideoView
        val animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        //starting the animation
        simpleVideoView!!.startAnimation(animation)
        buttonTxt.startAnimation(animation)

        if (mediaControls == null) {
            // creating an object of media controller class
            mediaControls = MediaController(this)

            // set the anchor view for the video view
            mediaControls!!.setAnchorView(this.simpleVideoView)
        }

        // set the media controller for video view
        simpleVideoView!!.setMediaController(mediaControls)

        // set the absolute path of the video file which is going to be played
        simpleVideoView!!.setVideoURI(
            Uri.parse(
                "android.resource://"
                        + packageName + "/" +  R.raw.magichat
            )
        )

        simpleVideoView!!.requestFocus()
        simpleVideoView!!.setMediaController(null)

        // starting the video
        simpleVideoView!!.start()

        // display a toast message
        // after the video is completed
        simpleVideoView!!.setOnCompletionListener {
            simpleVideoView!!.start()
            Toast.makeText(
                applicationContext, "Video completed",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    override fun onResume() {
        super.onResume()
        simpleVideoView = findViewById<View>(R.id.videoView) as VideoView
        if (simpleVideoView  != null) {
            simpleVideoView!!.start()
        }
    }


    fun buttonsetup(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }


        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener{
            val helpid = "user"
            val intent = Intent(this, HelpReminderActivity::class.java)
            intent.putExtra("HELPID",helpid)
            startActivity(intent)
        }
//        val button1 = findViewById<Button>(R.id.button9)
//        button1.setOnClickListener{
//            FirebaseAuth.getInstance().signOut()
//        }
        val buttonTxt = findViewById<Button>(R.id.button0)
        buttonTxt.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val businesssignupbutton = findViewById<Button>(R.id.button4)
        businesssignupbutton.setOnClickListener{
            val helpid = "Business"
            val intent = Intent(this, HelpReminderActivity::class.java)
            intent.putExtra("HELPID",helpid)
            startActivity(intent)
        }
        val businesssignin = findViewById<Button>(R.id.button3)
        businesssignin.setOnClickListener{
            val intent = Intent(this, BusinessSigninActivity::class.java)
            startActivity(intent)
        }


//        val hodor = findViewById<Button>(R.id.Hodor)
//        hodor.setOnClickListener {
//            clickCount++
//            if (clickCount == 5) {
//                val intent = Intent(this, HodorActivity::class.java)
//                startActivity(intent)
//
//            }
//        }
    }

     fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }
    override fun onLocationChanged(location: Location) {
        userlong= location.longitude.toString()
        userlat = location.latitude.toString()
        Log.e("Object", userlong)
        Log.e("Object", userlat)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun getLocationsafetycheck(){
        if (userlat == ""){
            getLocation()
        }
    }



    }
