package com.example.jonathaseloi.galeriakotlin.adapter

class VideosAdapter : android.support.v7.widget.RecyclerView.Adapter<VideosAdapter.MyViewHolder>() {

    private var videopath: MutableList<String> = java.util.ArrayList()

    init {
        videopath = java.util.ArrayList<String>()
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

    inner class MyViewHolder(val context: android.content.Context, view: android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(view), android.view.View.OnClickListener {


        var video: android.widget.ImageView

        init {
            video = view.findViewById(com.example.jonathaseloi.galeriakotlin.R.id.video_path) as android.widget.ImageView
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: android.view.View) {
            android.util.Log.e("TAG", "click")
        }
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): com.example.jonathaseloi.galeriakotlin.adapter.VideosAdapter.MyViewHolder {
        val itemView = android.view.LayoutInflater.from(parent.context).inflate(com.example.jonathaseloi.galeriakotlin.R.layout.content_item_video_galeria, null)
        return MyViewHolder(parent.context, itemView)
    }

    override fun onBindViewHolder(holder: com.example.jonathaseloi.galeriakotlin.adapter.VideosAdapter.MyViewHolder, position: Int) {

        val videopat = videopath[position]

        holder.video.setImageBitmap(createVideoThumbNail(videopat))

    }

    fun createVideoThumbNail(path: String): android.graphics.Bitmap {
        return android.media.ThumbnailUtils.createVideoThumbnail(path, android.provider.MediaStore.Video.Thumbnails.MICRO_KIND)
    }

    override fun getItemCount(): Int {
        return videopath.size
    }

    fun clear() {
        videopath.clear()
        notifyDataSetChanged()
    }
}
