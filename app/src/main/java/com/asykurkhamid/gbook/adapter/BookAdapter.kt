package com.asykurkhamid.gbook.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asykurkhamid.gbook.R
import com.asykurkhamid.gbook.model.BookModel
import com.asykurkhamid.gbook.model.Item
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.book_item.view.*

class BookAdapter: RecyclerView.Adapter<BookAdapter.VH>() {
    private var books = ArrayList<Item>()
    private lateinit var ctx : Context

    fun setBookList(bookList: List<Item>){
        books.clear()
        books.addAll(bookList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        ctx = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item,parent,false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(books[position], ctx)
    }

    class VH(itemView: View): RecyclerView.ViewHolder(itemView){

        fun onBind(book: Item,context: Context){
            if (!TextUtils.isEmpty(book.volumeInfo?.title)) {
                itemView.tvTitle.text = book.volumeInfo?.title
            }
            if (book.volumeInfo?.authors != null && book.volumeInfo.authors.isNotEmpty()){
                for (author in book.volumeInfo.authors){
                    itemView.tvAuthor.text = author
                }
            }

            if(book.volumeInfo?.imageLinks?.thumbnail != null){
                Glide.with(context)
                    .load(book.volumeInfo.imageLinks.thumbnail)
                    .into(itemView.imgBook)
            }

            if (book.volumeInfo?.averageRating != null){
                itemView.ratingBar.rating = book.volumeInfo?.averageRating
            }else{
                itemView.ratingBar.rating = 0.0f
            }

        }
    }
}