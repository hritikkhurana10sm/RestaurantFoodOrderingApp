package com.hritik.foodhunt.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hritik.foodhunt.R
import com.hritik.foodhunt.adapter.HomeAdapter
import com.hritik.foodhunt.model.Restaurants
import com.hritik.foodhunt.util.ConnectionManager
import org.json.JSONException
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap


class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    lateinit var recyclerAdapter: HomeAdapter
    private lateinit var progressLayout: RelativeLayout
    private lateinit var progressBar: ProgressBar

    val restaurantList = arrayListOf<Restaurants>()
    var ratingComparator = Comparator<Restaurants> { Restaurant1, Restaurant2 ->
        Restaurant1.rating.compareTo(Restaurant2.rating, true)
    }

    var priceComparator = Comparator<Restaurants> { Restaurant1, Restaurant2 ->
        Restaurant1.cost_for_one.compareTo(Restaurant2.cost_for_one, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerHome)
        layoutManager = LinearLayoutManager(activity as Context)
        setHasOptionsMenu(true)

        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v2/restaurants/fetch_result/"

        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout.visibility = View.VISIBLE

        if (ConnectionManager().checkConnectivity(activity as Context)) {
            val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener
                { response ->
                    try {
                        progressLayout.visibility = View.GONE
                        val it = response.getJSONObject("data")
                        val success = it.getBoolean("success")
                        if (success) {
                            val data = it.getJSONArray("data")
                            for (i in 0 until data.length()) {
                                val resJsonObject = data.getJSONObject(i)
                                val restaurantObject = Restaurants(
                                    resJsonObject.getString("id"),
                                    resJsonObject.getString("name"),
                                    resJsonObject.getString("rating"),
                                    resJsonObject.getString("cost_for_one"),
                                    resJsonObject.getString("image_url")
                                )
                                restaurantList.add(restaurantObject)
                                recyclerAdapter = HomeAdapter(activity as Context, restaurantList)
                                recyclerView.adapter = recyclerAdapter
                                recyclerView.layoutManager = layoutManager
                            }
                        } else {
                            Toast.makeText(
                                activity as Context,
                                "Server could not be reached!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            activity as Context,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener {
                    if (activity != null) {
                        Toast.makeText(
                            activity as Context,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val header = HashMap<String, String>()
                    header["Content-Type"] = "application/json"
                    header["token"] = "daf9a13c879372"
                    return header
                }
            }
            queue.add(jsonObjectRequest)
        } else {
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is not Found")
            dialog.setPositiveButton("Open Settings") { _, _ ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }

            dialog.setNegativeButton("Exit") { _, _ ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_sort) {
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Sort By-")
            val options = arrayOf("Cost(Low-High)", "Cost(High-Low)", "Rating")

            dialog.setSingleChoiceItems(options, -1) { dialog, which ->
                when (which) {
                    0 -> {
                        Collections.sort(restaurantList, priceComparator)
                    }
                    1 -> {
                        Collections.sort(restaurantList, priceComparator)
                        restaurantList.reverse()
                    }
                    2 -> {
                        Collections.sort(restaurantList, ratingComparator)
                        restaurantList.reverse()
                    }
                }
            }

            dialog.setPositiveButton("Ok") { text, listner ->
                recyclerAdapter.notifyDataSetChanged()
            }

            dialog.create()
            dialog.show()

        }

        return super.onOptionsItemSelected(item)
    }

}