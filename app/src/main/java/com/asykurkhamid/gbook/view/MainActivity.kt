package com.asykurkhamid.gbook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.asykurkhamid.gbook.R
import com.asykurkhamid.gbook.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asykurkhamid.gbook.adapter.BookAdapter


class MainActivity : AppCompatActivity() {
    private var viewModel = BookViewModel()
    private var bookAdapter = BookAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        rvBook.adapter = bookAdapter

        rvBook.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        viewModel.isLoading().observe(this, Observer { isLoading ->
            if (isLoading) {
                pgBar.visibility = View.VISIBLE
                rvBook.visibility = View.GONE
                emptyState.visibility = View.GONE
            } else {
                pgBar.visibility = View.GONE
                rvBook.visibility = View.VISIBLE
            }
        })

        viewModel.getBook()?.observe(this, Observer { book ->
            if (book?.items != null && book.items.isNotEmpty()) {
                emptyState.visibility = View.GONE
                bookAdapter.setBookList(book.items)
                bookAdapter.notifyDataSetChanged()
            }else{
                rvBook.visibility = View.GONE
                emptyState.visibility = View.VISIBLE
                tvEmptyTitle.text = resources.getString(R.string.empty_title2)
                tvEmptySubTitle.text = resources.getString(R.string.empty_subtitle2)
            }
        })

        scView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null && query.length >= 3) {
                    viewModel.callBookData(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }
}