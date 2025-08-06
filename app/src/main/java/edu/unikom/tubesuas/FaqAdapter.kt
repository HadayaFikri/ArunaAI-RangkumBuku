package edu.unikom.tubesuas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import edu.unikom.tubesuas.data.FaqItem
import androidx.recyclerview.widget.RecyclerView

class FaqAdapter(private val faqList: List<FaqItem>) :
    RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {

    class FaqViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionLayout: View = view.findViewById(R.id.question_layout)
        val questionTextView: TextView = view.findViewById(R.id.tv_question)
        val answerTextView: TextView = view.findViewById(R.id.tv_answer)
        val arrowImageView: ImageView = view.findViewById(R.id.iv_arrow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_faq, parent, false)
        return FaqViewHolder(view)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val faqItem = faqList[position]
        holder.questionTextView.text = faqItem.question
        holder.answerTextView.text = faqItem.answer

        // Set visibility based on isExpanded state
        holder.answerTextView.visibility = if (faqItem.isExpanded) View.VISIBLE else View.GONE
        holder.arrowImageView.rotation = if (faqItem.isExpanded) 180f else 0f

        holder.questionLayout.setOnClickListener {
            faqItem.isExpanded = !faqItem.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = faqList.size
}