
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.SwipeLayout.SwipeListener
import com.daimajia.swipe.adapters.BaseSwipeAdapter
import com.example.noted.R
import com.example.noted.model.notes.noteStructure

/**
 * Created by cuneyt on 1.7.2015.
 */
class ProductsListAdapter(var mContext: Context, var listOfNotes : List<noteStructure>) : BaseSwipeAdapter() {

    var lineIsClose = true

    override fun getSwipeLayoutResourceId(i: Int): Int {
        return R.id.note_swipe_layout
    }

    override fun generateView(position: Int, viewGroup: ViewGroup): View {
        return LayoutInflater.from(mContext).inflate(R.layout.notes_list_row, null)
    }

    override fun fillValues(position: Int, convertView: View) {

        val tvKeyField = convertView.findViewById<TextView>(R.id.list_row_tv_key)
        val tvValueField = convertView.findViewById<TextView>(R.id.list_row_tv_value)

        val btnImageDelete = convertView.findViewById<ImageView>(R.id.edit_query)
        val btnImageEdit = convertView.findViewById<ImageView>(R.id.delete)

        val swipeLayout = convertView.findViewById<View>(getSwipeLayoutResourceId(position)) as SwipeLayout

        tvKeyField.text = listOfNotes[position].getKey()
        tvValueField.text = listOfNotes[position].getValue()

        swipeLayout.dragEdgeMap.clear()
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.bottom_wrapper))

        swipeLayout.addSwipeListener(object : SwipeListener {
            override fun onStartOpen(swipeLayout: SwipeLayout) {}

            override fun onOpen(swipeLayout: SwipeLayout) {}

            override fun onStartClose(swipeLayout: SwipeLayout) {}

            override fun onClose(swipeLayout: SwipeLayout) {}

            override fun onUpdate(swipeLayout: SwipeLayout, i: Int, i1: Int) {}
            override fun onHandRelease(swipeLayout: SwipeLayout, v: Float, v1: Float) {}
        })

        btnImageDelete.setOnClickListener {
            //Стираем в базе значение
        }

        btnImageEdit.setOnClickListener {
            //Стартуем фрагмент для добавления
        }

    }

    override fun getCount(): Int {
        return listOfNotes.size
    }

    override fun getItem(position: Int): Any {
        return listOfNotes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


}