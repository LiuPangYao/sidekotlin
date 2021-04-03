package com.slideproject.sidekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import com.slideproject.sidekotlin.databinding.ActivityCsvBinding
import java.io.*

class CsvActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCsvBinding

    private val fileDirName = "csvfile"
    private var completeExternalPath: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCsvBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonSaveCsv.setOnClickListener(View.OnClickListener {
            completeExternalPath = File(getExternalFilesDir(fileDirName), "asset2021.csv")
            try {
                val fileOutPutStream = FileOutputStream(completeExternalPath)

                fileOutPutStream.write(
                    ("rank, name, asset, company\n" +
                            "1, Elon Musk, 1.28兆, 特斯拉\n" +
                            "2, Jeff Bezos, 1.22兆, 亞馬遜\n" +
                            "3, Bernard Arnault, 7380億, 酩悅·軒尼詩－路易·威登集團\n" +
                            "4, Bill Gates, 7120億, 微軟 \n" +
                            "5, Mark Zuckerberg, 6530億, Facebook\n").toByteArray()
                )

                /*fileOutPutStream.write(
                   ("rank, name, asset, company\n" +
                           "1, " + binding.editTextPersonName.text + ", "
                           + binding.editTextPersonAsset.text + ", "
                           + binding.editTextPersonAssetCompany.text).toByteArray()
               )*/

                fileOutPutStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Toast.makeText(applicationContext, "Data Save", Toast.LENGTH_SHORT).show()
        })

        binding.buttonReadCsv.setOnClickListener(View.OnClickListener {

            completeExternalPath = File(getExternalFilesDir(fileDirName), "asset2021.csv")

            var fileInputStream = FileInputStream(completeExternalPath)
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null

            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            fileInputStream.close()

            //Displaying data on TextView
            Toast.makeText(applicationContext, "Read Data", Toast.LENGTH_SHORT).show()
            binding.textViewContent.setText(stringBuilder.toString())
        })

        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            binding.buttonSaveCsv.isEnabled = false
        }
    }

    private val isExternalStorageReadOnly: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
                return true
            } else {
                return false
            }
        }

    private val isExternalStorageAvailable: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
                return true
            } else {
                return false
            }
        }
}