package com.gene.interviewexercise

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.gene.interviewexercise.networkservice.UrlItem

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val screenShot: MutableMap<Int, Bitmap> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewpager = findViewById<ViewPager2>(R.id.viewpager)

        viewpager.adapter = ViewPagerAdapter(screenShot)

        viewModel.urls.observe(this) {
            Log.d("MainActivity", it.toString())
            (viewpager.adapter as ViewPagerAdapter).submitList(it)
        }
    }

    class ViewPagerAdapter(private val map: MutableMap<Int, Bitmap>) : ListAdapter<UrlItem, WebViewViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebViewViewHolder {
            return WebViewViewHolder(parent, map)
        }

        override fun onBindViewHolder(holder: WebViewViewHolder, position: Int) {
            holder.bind(getItem(position))
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<UrlItem>() {
        override fun areItemsTheSame(oldItem: UrlItem, newItem: UrlItem): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: UrlItem, newItem: UrlItem): Boolean {
            return oldItem == newItem
        }
    }

    class WebViewViewHolder(
        parent: ViewGroup,
        map: MutableMap<Int, Bitmap>
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.webview_item, parent, false)
    ) {
        val webview: WebView = itemView.findViewById(R.id.webview)

        val progressbar: ProgressBar = itemView.findViewById(R.id.progressbar)

        val imageView: ImageView = itemView.findViewById(R.id.imageView)

        init {
            webview.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    val bitmap = map[adapterPosition]
                    if (bitmap == null ) {
                        progressbar.visibility = View.VISIBLE
                    } else {
                        imageView.setImageBitmap(bitmap)
                    }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progressbar.visibility = View.GONE
                    if (webview.width > 0 && webview.height > 0) {
                        val bitmap = Bitmap.createBitmap(
                            webview.width,
                            webview.height,
                            Bitmap.Config.ARGB_8888
                        )
                        val canvas = Canvas(bitmap)
                        webview.draw(canvas)
                        map[adapterPosition] = bitmap
                    }
                }
            }
        }

        fun bind(item: UrlItem) {
            webview.loadUrl(item.url)
        }
    }


}