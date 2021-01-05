package nl.wesselbarten.museumapp.util.pagination

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationOnScrollListener(
    private val getNextPage: () -> Unit,
    private val isLoading: () -> Boolean
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

        val totalItemCount = linearLayoutManager.itemCount
        val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

        if (dy > 0 && !isLoading() && lastVisibleItem == totalItemCount - 1) {
            getNextPage()
        }
    }
}