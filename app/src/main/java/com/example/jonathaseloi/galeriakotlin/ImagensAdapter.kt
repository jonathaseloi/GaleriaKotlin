package com.example.jonathaseloi.galeriakotlin

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import java.io.File
import java.util.ArrayList

class ImagensAdapter : RecyclerView.Adapter<ImagensAdapter.MyViewHolder>() {

    private var imagespath: MutableList<String>? = null

    init {
        imagespath = ArrayList<String>()
    }

    fun setImagens(imagespath: MutableList<String>) {
        this.imagespath = imagespath
    }

    fun addImagens(imagespath: List<String>) {
        for (path in imagespath) {
            this.imagespath!!.add(path)
            notifyItemInserted(itemCount)
        }
    }

    fun addImagem(path: String) {
        this.imagespath!!.add(path)
        notifyItemInserted(itemCount)
    }

    inner class MyViewHolder(//public ImageView image;
            val context: Context, view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        var image: ImageView

        init {
            image = view.findViewById(R.id.image_path) as ImageView

            itemView.setOnClickListener(this)
        }

        //Abre a imagem em tela cheia
        override fun onClick(v: View) {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.setDataAndType(Uri.fromFile(File(imagespath!![adapterPosition])), "image/*")
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.content_item_imagem_galeria, null)
        return MyViewHolder(parent.context, itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val imagepath = imagespath!![position]
        val myBitmap = BitmapFactory.decodeFile(imagepath)
        holder.image.setImageBitmap(myBitmap)

        //Picasso.with(holder.image.getContext()).load(new File(imagepath)).resize(120, 120).centerCrop().into(holder.image);
    }

    override fun getItemCount(): Int {
        return imagespath!!.size
    }

    fun clear() {
        imagespath!!.clear()
        notifyDataSetChanged()
    }
}
