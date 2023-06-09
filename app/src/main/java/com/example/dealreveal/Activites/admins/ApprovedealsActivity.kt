package com.example.dealreveal.Activites.admins

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dealreveal.Activites.*
import com.example.dealreveal.Activites.client.DealRevealActivity
import com.example.dealreveal.Activites.shared.HelpOverviewActivity
import com.example.dealreveal.Activites.shared.Pendingapproval
import com.example.dealreveal.R
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_admin_approve_clients.*


class ApprovedealsActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    lateinit var auth1: FirebaseAuth
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Pendingapproval>
    lateinit var imageID : Array<Int>
    lateinit var heading : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approvedeals)
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
        headerandbottom()

        auth1 = FirebaseAuth.getInstance()

        newRecyclerView = findViewById(R.id.recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf()
        getUserdata()


    }
    private fun getUserdata() {
        val db = FirebaseFirestore.getInstance()


        val query = db.collection("ReviewMeals1").document("Deals").collection("Deals")
        val config = PagedList.Config.Builder().setEnablePlaceholders(false)
            .setPrefetchDistance(1)
            .setPageSize(1)
            .build()

        val options = FirestorePagingOptions.Builder<Pendingapproval>().setQuery(query,config, Pendingapproval::class.java)
            .setLifecycleOwner(this).build()
        val adapter = object: FirestorePagingAdapter<Pendingapproval, PendingapprovalViewHolder>(options) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): PendingapprovalViewHolder {
                val itemView =
                    LayoutInflater.from(parent.context).inflate(R.layout.list_client, parent, false)
                return PendingapprovalViewHolder(itemView)
            }

            override fun onBindViewHolder(
                holder: PendingapprovalViewHolder,
                position: Int,
                model: Pendingapproval
            ) {
                val titleImage: ShapeableImageView = holder.itemView.findViewById(R.id.title_image)
                val tvheading: TextView = holder.itemView.findViewById(R.id.tvheading)
                tvheading.text = model.Title
                Glide.with(this@ApprovedealsActivity)
                    .load(model.MealImageUrl.toString())
                    .into(titleImage)
                holder.itemView.setOnClickListener {
                    Log.i("test", "im a god")
                    var Address = model.Address
                    var CompanyURL = model.CompanyURL
                    var DayofDeal = model.DayofDeal
                    var EndTime = model.EndTime
                    var EndTimeNumber = model.EndTimeNumber
                    var Facebook = model.Facebook
                    var MealImageUrl = model.MealImageUrl
                    var PhoneNumber = model.PhoneNumber
                    var RestaurantName = model.RestaurantName
                    var StartTime = model.StartTime
                    var StartTimeNumber = model.StartTimeNumber
                    var Title = model.Title
                    var Yelp = model.Yelp
                    var category = model.category
                    var date = model.date
                    var description = model.description
                    var latitude = model.latitude
                    var longitude = model.longitude
                    var price = model.price
                    var resid = model.resid
                    var uid = model.uid


                    val intent = Intent(this@ApprovedealsActivity, DealRevealActivity::class.java)
                    intent.putExtra("Address", Address)
                    intent.putExtra("CompanyURL", CompanyURL)
                    intent.putExtra("DayofDeal", DayofDeal)
                    intent.putExtra("EndTime", EndTime)
                    intent.putExtra("EndTimeNumber", EndTimeNumber)
                    intent.putExtra("Facebook", Facebook)
                    intent.putExtra("MealImageUrl", MealImageUrl)
                    intent.putExtra("PhoneNumber", PhoneNumber)
                    intent.putExtra("RestaurantName", RestaurantName)
                    intent.putExtra("StartTime", StartTime)
                    intent.putExtra("StartTimeNumber", StartTimeNumber)
                    intent.putExtra("Title", Title)
                    intent.putExtra("Yelp", Yelp)
                    intent.putExtra("category", category)
                    intent.putExtra("date", date)
                    intent.putExtra("description", description)
                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)
                    intent.putExtra("price", price)
                    intent.putExtra("resid", resid)
                    intent.putExtra("uid", uid)
                    intent.putExtra("admincheck","approve new deal")


                    startActivity(intent)
                    
                }
            }

            override fun onLoadingStateChanged(state: LoadingState) {
                when (state) {
                    LoadingState.LOADING_INITIAL -> {
                        Log.d("TAG","LOADING_INITIAL" )

                    }

                    LoadingState.LOADING_MORE -> {
                        Log.d("TAG","LOADING_MORE" )
                    }

                    LoadingState.LOADED -> {
                        Log.d("TAG","LOADED a total of " +newRecyclerView.adapter!!.itemCount.toString() )
                    }

                    LoadingState.ERROR -> {
                        Toast.makeText(
                            applicationContext,
                            "Error Occurred!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    LoadingState.FINISHED -> {
                        Log.d("TAG","FINISHED" )
                    }
                }
            }

        }
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

    }

    private fun headerandbottom(){
        val leftIcon = findViewById<ImageView>(R.id.left_icon)
        val rightIcon = findViewById<ImageView>(R.id.right_icon)
        val title = findViewById<TextView>(R.id.info)

        leftIcon.setOnClickListener {
            finish()
        }
        rightIcon.setOnClickListener {
            val intent = Intent(this, HelpOverviewActivity::class.java)
            startActivity(intent)
        }
        title.setText("New Deals")

        val bottomNavigationView: BottomNavigationView
        bottomNavigationView = findViewById<View>(R.id. bottomNav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.deal

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.client -> {
                    val intent = Intent(this, AdminApproveClients::class.java)
                    startActivity(intent);
                    true
                }
                R.id.deal-> {

                    true

                }
                R.id.liar -> {
                    val intent = Intent(this, LiarreportActivity::class.java)
                    startActivity(intent);
                    true
                }
                R.id.update -> {
                    val intent = Intent(this, AdminbusinesschangeActivity::class.java)
                    startActivity(intent);
                    true
                }
                else -> false
            }
        }
    }

}
