package com.hritik.foodhunt.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hritik.foodhunt.R
import com.hritik.foodhunt.adapter.FaqRecyclerAdapter
import com.hritik.foodhunt.model.Faq

class FAQFragment : Fragment() {


    lateinit var recyclerFaq: RecyclerView

    lateinit var linearLayout: RecyclerView.LayoutManager

    lateinit var FaqRecyclerAdapter: FaqRecyclerAdapter

    var faq = arrayListOf(
        Faq(
            "Is there any food delivery charge ?",
            "Yes, Delivery of food costs you Rs 150/-. We deliver the food without  delivery charge on occasions."
        ),
        Faq(
            " Can I place order on call?",
            " Sorry, we don’t accept orders on call. However you can call us on +91-9998886666 for any help related to placing order."
        ),
        Faq(
            " When I have to pay?",
            " You will have to pay at the time of delivery as per the payment details we send to you via email."
        ),
        Faq(
            "Will I get an invoice?",
            "Yes. You will get your invoice from our supplier at the time of delivery."
        ),
        Faq(
            "How are you guys are different?",
            " Best products - We only list the products which have passed our tests and are the best suitable for delivery. They will help in giving a better consumer experience, so you can get get repeated orders.\n" +
                    "\n" +
                    "Best prices - We have negotiated rates with our distributors exclusive for Restaurant.\n" +
                    "\n" +
                    "Convenience - No need to haggle for prices. No need to follow-up on delivery. We track all orders to ensure you have a hassle free experience."
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_f_a_q, container, false)
        recyclerFaq = view.findViewById(R.id.recyclerFaq)
        linearLayout = LinearLayoutManager(activity)

        FaqRecyclerAdapter = FaqRecyclerAdapter(activity as Context, faq)

        recyclerFaq.layoutManager = linearLayout
        recyclerFaq.adapter = FaqRecyclerAdapter

        return view
    }
}