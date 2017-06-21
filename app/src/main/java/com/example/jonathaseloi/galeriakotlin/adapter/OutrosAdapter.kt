package com.example.jonathaseloi.galeriakotlin.adapter

class OutrosAdapter : android.support.v7.widget.RecyclerView.Adapter<OutrosAdapter.MyViewHolder>() {

    private var outropath: MutableList<String> = java.util.ArrayList()

    init {
        outropath = java.util.ArrayList<String>()
    }

    fun setOutros(videopath: MutableList<String>) {
        this.outropath = videopath
    }

    fun addOutros(imagespath: List<String>) {
        for (path in imagespath) {
            this.outropath.add(path)
            notifyItemInserted(itemCount)
        }
    }

    fun addOutro(path: String) {
        this.outropath.add(path)
        notifyItemInserted(itemCount)
    }

    inner class MyViewHolder(val context: android.content.Context, view: android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(view), android.view.View.OnClickListener {


        var icone: android.widget.ImageView


        var text: android.widget.TextView

        init {
            icone = view.findViewById(com.example.jonathaseloi.galeriakotlin.R.id.outro_path) as android.widget.ImageView
            text = view.findViewById(com.example.jonathaseloi.galeriakotlin.R.id.file_name) as android.widget.TextView

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: android.view.View) {
            android.util.Log.e("TAG", "click " + outropath[adapterPosition])

            val filename = outropath[adapterPosition].substring(outropath[adapterPosition].lastIndexOf(".") + 1)

            if (filename == "pdf") {
                val file = java.io.File(outropath[adapterPosition])
                val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
                intent.setDataAndType(android.net.Uri.fromFile(file), "application/pdf")
                intent.flags = android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
                val intent1 = android.content.Intent.createChooser(intent, "Open With")
                try {
                    context.startActivity(intent1)
                } catch (e: android.content.ActivityNotFoundException) {
                    // Instruct the user to install a PDF reader here
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): com.example.jonathaseloi.galeriakotlin.adapter.OutrosAdapter.MyViewHolder {
        val itemView = android.view.LayoutInflater.from(parent.context).inflate(com.example.jonathaseloi.galeriakotlin.R.layout.content_item_outro_arquivo_galeria, null)
        return MyViewHolder(parent.context, itemView)
    }

    override fun onBindViewHolder(holder: com.example.jonathaseloi.galeriakotlin.adapter.OutrosAdapter.MyViewHolder, position: Int) {
        val outropat = outropath[position]
        val filename = outropat.substring(outropat.lastIndexOf("/") + 1)

        holder.icone.setImageDrawable(holder.context.resources.getDrawable(com.example.jonathaseloi.galeriakotlin.R.drawable.ic_insert_file))
        if (filename.length > 24)
            holder.text.text = filename.substring(0, 21) + " ... " + filename.substring(filename.length - 4)
        else {
            holder.text.text = filename
        }
    }

    override fun getItemCount(): Int {
        return outropath.size
    }

    fun clear() {
        outropath.clear()
        notifyDataSetChanged()
    }
}
