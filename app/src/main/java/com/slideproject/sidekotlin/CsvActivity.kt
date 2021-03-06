package com.slideproject.sidekotlin

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slideproject.sidekotlin.adapter.AssetAdapter
import com.slideproject.sidekotlin.asset.AssetData
import com.slideproject.sidekotlin.databinding.ActivityCsvBinding
import com.slideproject.sidekotlin.databinding.AlertDialogCustomBinding
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

class CsvActivity : AppCompatActivity() {

    private fun showSimpleAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Simple Alert Dialog")
            .setMessage("Show Simple Button")
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, which ->
                Toast.makeText(applicationContext, "OK Button", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                Toast.makeText(applicationContext, "Cancel Button", Toast.LENGTH_LONG).show()
            }
            .setNeutralButton("Neutral") { dialog, which ->
                Toast.makeText(applicationContext, "Neutral Button", Toast.LENGTH_LONG).show()
            }
            .show()
    }

    private fun showListAlertDialog() {
        val languageList = arrayOf("中文", "英文", "日文", "德文", "法文")
        AlertDialog.Builder(this)
            .setTitle("請選擇應用程式語言")
            .setItems(languageList) { dialog, which ->
                Toast.makeText(
                    applicationContext,
                    "選擇程式語言 -> ${languageList[which]}",
                    Toast.LENGTH_LONG
                ).show()
            }
            .setNegativeButton("取消") { dialog, which ->
                Toast.makeText(applicationContext, "取消選擇語言完成", Toast.LENGTH_LONG).show()
            }
            .show()
    }

    private fun showCustomViewAlertDialog() {
        val bindingAlertDialog: AlertDialogCustomBinding =
            AlertDialogCustomBinding.inflate(LayoutInflater.from(applicationContext))
        createAlertDialog(
            applicationContext,
            bindingAlertDialog.getRoot(),
            true
        )

        bindingAlertDialog.addButton.setOnClickListener(View.OnClickListener {
            Log.d("TAG", "showCustomViewAlertDialog: " + bindingAlertDialog.editTextPersonName.text +
                    "," + bindingAlertDialog.editTextPersonAssetCompany.text +
                    "," + bindingAlertDialog.editTextPersonAsset.text)
        })

    }

    private fun createAlertDialog(context: Context?, view: View?, isCancelable: Boolean): AlertDialog? {
        val builder = AlertDialog.Builder(this@CsvActivity)
        builder.setView(view)
        val customDialog = builder.show()
        customDialog.setCancelable(isCancelable)
        return customDialog
    }

    private lateinit var binding: ActivityCsvBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCsvBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.alertButton.setOnClickListener(View.OnClickListener {
            showCustomViewAlertDialog()
        })

        for (assets in assetArray) {
            mAssetList.add(assets)
        }

        assetAdapterＣontainer = AssetAdapter(mAssetList)
        assetAdapterＣontainer.notifyDataSetChanged()
        binding.recyclerViewCsv.adapter = assetAdapterＣontainer
        layoutManager = LinearLayoutManager(binding.recyclerViewCsv.context)
        binding.recyclerViewCsv.layoutManager = layoutManager

        binding.buttonSaveCsv.setOnClickListener(View.OnClickListener {
            completeExternalPath = File(getExternalFilesDir(fileDirName), "asset2021.csv")
            try {
                //val fileOutPutStream = FileOutputStream(completeExternalPath)
                //val stringBuilderWrite: StringBuilder = StringBuilder()

                // fileOutPutStream vs fileWriter
                val fileWriter = FileWriter(completeExternalPath)

                fileWriter.append(CSV_HEADER)
                for (assets in assetArray) {
                    fileWriter.append(assets.rank)
                    fileWriter.append(',')
                    fileWriter.append(assets.name)
                    fileWriter.append(',')
                    fileWriter.append(assets.asset)
                    fileWriter.append(',')
                    fileWriter.append(assets.company)
                    fileWriter.append('\n')
                }
                fileWriter.close()

                //fileOutPutStream.write(stringBuilderWrite.toString().toByteArray())
                //fileOutPutStream.close()
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

    private val fileDirName = "csvfile"
    private var completeExternalPath: File? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var assetAdapterＣontainer: AssetAdapter

    private val CSV_HEADER = "rank, name, asset, company\n"
    private val assetArray = Arrays.asList(
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

    val mAssetList: ArrayList<AssetData> = ArrayList<AssetData>()

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

    private fun saveCSV() {
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
    }
}