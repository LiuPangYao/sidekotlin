package com.slideproject.sidekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import com.slideproject.sidekotlin.asset.AssetData
import com.slideproject.sidekotlin.databinding.ActivityCsvBinding
import java.io.*
import java.util.*

class CsvActivity : AppCompatActivity() {

    private val fileDirName = "csvfile"
    private var completeExternalPath: File? = null
    val CSV_HEADER = "rank, name, asset, company\n"
    val assetArray = Arrays.asList(
        AssetData("1", "Elon Musk", "1.28兆", "特斯拉"),
        AssetData("2", "Jeff Bezos", "1.22兆", "亞馬遜"),
        AssetData("3", "Bernard Arnault", "7380億", "酩悅·軒尼詩－路易·威登集團"),
        AssetData("4", "Bill Gates", "7120億", "微軟"),
        AssetData("5", "Mark Zuckerberg", "6530億", "Facebook"),
        AssetData("6", "Warren Buffett", "5890億", "波克夏"),
        AssetData("7", "Zhong Shanshan", "5500億", "農夫山泉"),
        AssetData("8", "穆凱什·阿姆巴尼", "5370億", "信實工業"),
        AssetData("9", "貝特朗‧皮埃奇家族", "5180億", "愛馬仕"),
        AssetData("10", "Steve Ballmer", "5180億", "微軟")
    )

    private lateinit var binding: ActivityCsvBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCsvBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonSaveCsv.setOnClickListener(View.OnClickListener {
            completeExternalPath = File(getExternalFilesDir(fileDirName), "asset2021.csv")
            try {
                val fileOutPutStream = FileOutputStream(completeExternalPath)
                val stringBuilderWrite: StringBuilder = StringBuilder()

                stringBuilderWrite.append(CSV_HEADER)
                for (assets in assetArray) {
                    stringBuilderWrite.append(assets.rank)
                    stringBuilderWrite.append(',')
                    stringBuilderWrite.append(assets.name)
                    stringBuilderWrite.append(',')
                    stringBuilderWrite.append(assets.asset)
                    stringBuilderWrite.append(',')
                    stringBuilderWrite.append(assets.company)
                    stringBuilderWrite.append('\n')
                }

                fileOutPutStream.write(stringBuilderWrite.toString().toByteArray())

                /*fileOutPutStream.write(
                    ("rank, name, asset, company\n" +
                            "1, Elon Musk, 1.28兆, 特斯拉\n" +
                            "2, Jeff Bezos, 1.22兆, 亞馬遜\n" +
                            "3, Bernard Arnault, 7380億, 酩悅·軒尼詩－路易·威登集團\n" +
                            "4, Bill Gates, 7120億, 微軟 \n" +
                            "5, Mark Zuckerberg, 6530億, Facebook\n").toByteArray()
                )*/

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
                stringBuilder.append(text + "\n")
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