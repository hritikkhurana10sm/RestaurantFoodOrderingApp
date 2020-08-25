package com.hritik.foodhunt.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hritik.foodhunt.R
import com.hritik.foodhunt.model.RestaurantMenu
import androidx.core.content.ContextCompat.getColor

class DetailAdapter(
    val context: Context,
    private val itemList: ArrayList<RestaurantMenu>,
    private val cartFoodNAme: ArrayList<String>,
    private val cartFoodCost: ArrayList<String>,
    private val cartFoodId: ArrayList<String>,
    val itemClick: (Boolean) -> Unit
) :
    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtProductName: TextView = view.findViewById(R.id.txtProductName)
        val txtProductCost: TextView = view.findViewById(R.id.txtProductCost)
        val cvDetail: CardView = view.findViewById(R.id.cvDetail)
        val txtAddToCart: TextView = view.findViewById(R.id.txtAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_detail_single_element, parent, false)
        return DetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val product = itemList[position]
        holder.txtProductName.text = product.name
        holder.txtProductCost.text = "Rs. ${product.cost_for_one}"

        holder.txtAddToCart.setOnClickListener {
            if (cartFoodNAme.contains(product.name) && cartFoodCost.contains(product.cost_for_one)) {
                cartFoodNAme.remove(product.name)
                cartFoodCost.remove(product.cost_for_one)
                cartFoodId.remove(product.id)
                holder.txtAddToCart.text = context.getString(R.string.add_to_cart)
                /*holder.cvDetail.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        color.on_button_not_pressed
                    )
                )*/
            } else {
                cartFoodNAme.add(product.name)
                cartFoodCost.add(product.cost_for_one)
                cartFoodId.add(product.id)
                holder.txtAddToCart.text = context.getString(R.string.remove)
              /*  holder.cvDetail.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                       colors.on_button_pressed
                    )
                )*/
            }
            itemClick(true)
        }
    }
}