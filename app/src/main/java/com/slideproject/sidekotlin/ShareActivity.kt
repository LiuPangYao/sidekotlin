package com.slideproject.sidekotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.slideproject.sidekotlin.databinding.ActivityShareBinding
import java.io.*

class ShareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShareBinding;
    private var completeExternalPath: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        completeExternalPath = File(this.cacheDir, "share.txt")

        try {
            val fileWriter = FileWriter(completeExternalPath)
            fileWriter.append("share file txt sample")
            fileWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        binding.imageViewShare.setOnClickListener(View.OnClickListener {
            val path: Uri = FileProvider.getUriForFile(
                this.applicationContext,
                "${this.packageName}.fileprovider",
                completeExternalPath!!
            )

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, "分享檔案")
                putExtra(Intent.EXTRA_TEXT, "分享 Share.txt 檔案")
                putExtra(Intent.EXTRA_STREAM, path)
                type = "text/plain"
            }

            startActivity(Intent.createChooser(sendIntent, "Send mail"))
        })
    }
}