package me.dfournier.instantapp.subscription

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_subscribe_plan.*
import kotlinx.android.synthetic.main.item_plan.view.*


class SubscriptionPlanFragment : BaseFragment() {

    private val plans = listOf(
        Plan("Plan A", "Free Tier", 0),
        Plan("Plan B", "Paid Tier", 5),
        Plan("Plan C", "Premium Tier", 10)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscribe_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plan_list.layoutManager = LinearLayoutManager(context!!)
        plan_list.adapter = PlanAdapter(plans, ::onItemSelected)

        next.setOnClickListener {
            goToNextStep(this::class)
        }
    }

    private fun onItemSelected(plan: Plan) {
        next.isEnabled = true
    }
}

data class Plan(
    val name: String,
    val description: String,
    val price: Int
)

class PlanAdapter(
    private val list: List<Plan>,
    private val listener: (Plan) -> Unit
) : RecyclerView.Adapter<PlanViewHolder>() {

    private var selectedItem: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plan, parent, false)
        return PlanViewHolder(view) {
            if (it != selectedItem) {
                val oldItem = selectedItem
                selectedItem = it
                if (oldItem != null) {
                    notifyItemChanged(oldItem)
                }
                notifyItemChanged(it)
                listener(list[it])
            }
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        holder.bind(list[position], selectedItem)
    }

}

class PlanViewHolder(itemView: View, listener: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            listener(this.adapterPosition)
        }
    }

    fun bind(plan: Plan, selectedItem: Int?) {
        itemView.plan_price.text = itemView.resources.getString(R.string.subscription_plan_price, plan.price)
        itemView.plan_text.text = plan.name
        itemView.plan_description.text = plan.description
        itemView.plan.isChecked = this.adapterPosition == selectedItem
    }

}