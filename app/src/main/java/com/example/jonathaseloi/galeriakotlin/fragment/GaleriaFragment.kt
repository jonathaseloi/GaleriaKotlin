package com.example.jonathaseloi.galeriakotlin.fragment

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jonathaseloi.galeriakotlin.adapter.ImagensAdapter
import com.example.jonathaseloi.galeriakotlin.adapter.OutrosAdapter
import com.example.jonathaseloi.galeriakotlin.adapter.VideosAdapter

import java.io.File

class GaleriaFragment : Fragment() {

    internal var rootView: View? = null

    internal var recyclerView: RecyclerView? = null
    internal var type: String = ""
    internal var path: Array<String>? = null
    internal var layout: Int = 0
    internal var recycleview: Int = 0

    internal var imagensAdapter = ImagensAdapter()

    internal var videosAdapter = VideosAdapter()

    internal var outrosAdapter = OutrosAdapter()

    private fun readBundle(bundle: Bundle?) {
        if (bundle != null) {
            type = bundle.getString("type")
            path = bundle.getStringArray("path")
            layout = bundle.getInt("layout")
            recycleview = bundle.getInt("recycleview")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readBundle(arguments)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater?.inflate(layout, container, false)

        recyclerView = rootView?.findViewById(recycleview) as RecyclerView?
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mNoOfColumns = calculateNoOfColumns(activity)
        val mLayoutManager = GridLayoutManager(activity, mNoOfColumns)

        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()

        when (type) {
            "Imagens" -> {
                recyclerView?.adapter = imagensAdapter

                if (!permissaoAcessoArquivo(activity)) {
                    requestPermission(activity, 0)
                }
            }

            "Videos" ->
                recyclerView?.adapter = videosAdapter

            else ->
                recyclerView?.adapter = outrosAdapter
        }

        if (permissaoAcessoArquivo(activity)) {
            createDirIfNotExists("AppGallery")
            createDirIfNotExists("AppGallery/Outros")
            createDirIfNotExists("AppGallery/Imagens")
            createDirIfNotExists("AppGallery/Videos")
            searchfilestype(path, type, imagensAdapter, videosAdapter, outrosAdapter)
        }

    }

    override fun onResume() {
        super.onResume()
    }

    //Buscar arquivos Imagens, Videos e Outros
    fun searchfilestype(path: Array<String>?, type: String, iadapter: ImagensAdapter?, vadapter: VideosAdapter?, oadapter: OutrosAdapter?) {

        val projection: Array<String>
        var columnIndex = 0

        when (type) {
            "Imagens" -> {
                projection = arrayOf(MediaStore.Images.Media.DATA)

                val cursor = activity.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        MediaStore.Images.Media.DATA + " like ? ",
                        path, null)

                if (cursor != null) {
                    columnIndex = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA)
                    if (cursor.count > 0)
                        for (i in 0..cursor.count - 1) {
                            cursor.moveToPosition(i)
                            Log.e("TAGGG", cursor.getString(columnIndex))
                            iadapter?.addImagem(cursor.getString(columnIndex))
                        }
                    cursor.close()
                }
            }

            "Videos" -> {
                projection = arrayOf(MediaStore.Video.Media.DATA)

                val cursor2 = activity.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        MediaStore.Video.Media.DATA + " like ? ",
                        path, null)

                if (cursor2 != null) {
                    columnIndex = cursor2.getColumnIndex(MediaStore.Video.Thumbnails.DATA)

                    if (cursor2.count > 0)
                        for (i in 0..cursor2.count - 1) {
                            cursor2.moveToPosition(i)
                            Log.e("TAGGG", cursor2.getString(columnIndex))
                            vadapter?.addVideo(cursor2.getString(columnIndex))
                        }
                    cursor2.close()
                }
            }

            else -> {
                val cr = activity.contentResolver
                val uri = MediaStore.Files.getContentUri("external")

                val selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_NONE.toString() + " AND " + MediaStore.Files.FileColumns.DATA + " like ? "

                val allNonMediaFiles = cr.query(uri, null, selection, path, null)

                if (allNonMediaFiles != null) {
                    if (allNonMediaFiles.count > 0)
                        for (i in 0..allNonMediaFiles.count - 1) {
                            allNonMediaFiles.moveToPosition(i)
                            Log.e("TAGGG", allNonMediaFiles.getString(columnIndex))
                            oadapter?.addOutro(allNonMediaFiles.getString(allNonMediaFiles.getColumnIndex(MediaStore.Files.FileColumns.DATA)))
                        }
                    allNonMediaFiles.close()
                }
            }
        }
    }

    //Calcular quantos itens serao exibidos por linha
    fun calculateNoOfColumns(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return (dpWidth / 120).toInt()
    }

    companion object {

        fun newInstance(type: String, path: Array<String>, layout: Int, recycleview: Int): GaleriaFragment {
            val bundle = Bundle()
            bundle.putString("type", type)
            bundle.putStringArray("path", path)
            bundle.putInt("layout", layout)
            bundle.putInt("recycleview", recycleview)

            val fragment = GaleriaFragment()
            fragment.arguments = bundle

            return fragment
        }

    }

    //Criar diretorios se nao existirem, ou se foram deletados
    fun createDirIfNotExists(path: String): Boolean {
        var ret = true

        val file = File(Environment.getExternalStorageDirectory(), path)
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating folder + " + path)
                ret = false
            }
        }
        return ret
    }

    //Permissoes
    fun requestPermission(activity: Activity, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0]) || ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[1])) {
                ActivityCompat.requestPermissions(activity, permissions, requestCode)
            } else {
                ActivityCompat.requestPermissions(activity, permissions, requestCode)
            }
        }
    }

    //Permissoes
    fun permissaoAcessoArquivo(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            val writePermission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val readPermission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            return writePermission == PackageManager.PERMISSION_GRANTED && readPermission == PackageManager.PERMISSION_GRANTED
        }
        return true
    }
}

