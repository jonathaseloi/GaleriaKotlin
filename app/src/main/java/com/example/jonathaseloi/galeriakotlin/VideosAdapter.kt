package com.example.jonathaseloi.galeriakotlin

import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import java.util.ArrayList

//import com.squareup.picasso.Picasso;

class VideosAdapter : RecyclerView.Adapter<VideosAdapter.MyViewHolder>() {

    private var videopath: MutableList<String> = ArrayList()

    init {
        videopath = ArrayList<String>()
    }

    fun setVideos(videopath: MutableList<String>) {
        this.videopath = videopath
    }

    fun addVideos(imagespath: List<String>) {
        for (path in imagespath) {
            this.videopath.add(path)
            notifyItemInserted(itemCount)
        }
    }

    fun addVideo(path: String) {
        this.videopath.add(path)
        notifyItemInserted(itemCount)
    }

    inner class MyViewHolder(//public ImageView video;
            val context: Context, view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {


        var video: ImageView

        init {
            video = view.findViewById(R.id.video_path) as ImageView
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.e("TAG", "click")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.content_item_video_galeria, null)
        return MyViewHolder(parent.context, itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val videopat = videopath[position]

        holder.video.setImageBitmap(createVideoThumbNail(videopat))

        //        VideoRequestHandlerUtils videoRequestHandlerUtils;
        //        Picasso picassoInstance;
        //
        //        videoRequestHandlerUtils = new VideoRequestHandlerUtils();
        //        picassoInstance = new Picasso.Builder(holder.context.getApplicationContext())
        //                .addRequestHandler(videoRequestHandlerUtils)
        //                .build();
        //
        //        picassoInstance.load(videoRequestHandlerUtils.SCHEME_VIDEO+":"+videopat).resize(120, 120).centerCrop().into(holder.video);
    }

    fun createVideoThumbNail(path: String): Bitmap {
        return ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND)
    }

    override fun getItemCount(): Int {
        return videopath.size
    }

    fun clear() {
        videopath.clear()
        notifyDataSetChanged()
    }
}
