package com.example.jonathaseloi.galeriakotlin

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import java.io.File
import java.util.ArrayList

//import com.squareup.picasso.Picasso;

class OutrosAdapter : RecyclerView.Adapter<OutrosAdapter.MyViewHolder>() {

    private var outropath: MutableList<String> = ArrayList()

    init {
        outropath = ArrayList<String>()
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

    inner class MyViewHolder(//public ImageView icone;
            //public TextView text;
            val context: Context, view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {


        var icone: ImageView


        var text: TextView

        init {
            icone = view.findViewById(R.id.outro_path) as ImageView
            text = view.findViewById(R.id.file_name) as TextView

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.e("TAG", "click " + outropath[adapterPosition])

            val filename = outropath[adapterPosition].substring(outropath[adapterPosition].lastIndexOf(".") + 1)

            if (filename == "pdf") {
                val file = File(outropath[adapterPosition])
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(Uri.fromFile(file), "application/pdf")
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                val intent1 = Intent.createChooser(intent, "Open With")
                try {
                    context.startActivity(intent1)
                } catch (e: ActivityNotFoundException) {
                    // Instruct the user to install a PDF reader here
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.content_item_outro_arquivo_galeria, null)
        return MyViewHolder(parent.context, itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val outropat = outropath[position]
        val filename = outropat.substring(outropat.lastIndexOf("/") + 1)

        //Picasso.with(holder.icone.getContext()).load(R.drawable.ic_file).resize(120, 120).centerCrop().into(holder.icone);
        holder.icone.setImageDrawable(holder.context.resources.getDrawable(R.drawable.ic_insert_file))
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
