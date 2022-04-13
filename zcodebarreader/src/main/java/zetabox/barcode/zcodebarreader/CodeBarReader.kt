package zetabox.barcode.zcodebarreader

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import zetabox.barcode.zcodebarreader.databinding.CodeBarReaderBinding

/**
 *Created by fatma on 13/04/2022.

 **/
class CodeBarReader @JvmOverloads constructor(
    context: Context,
    var attributeSet: AttributeSet? = null,
    var defStyle: Int = 0,
    var defStyleRes: Int = 0
) : LinearLayout(
    context, attributeSet,
    defStyle,
    defStyleRes
) {
    var binding: CodeBarReaderBinding =
        CodeBarReaderBinding.inflate(LayoutInflater.from(context), this);
    private var activity: AppCompatActivity? = null
    private var registerForActivityResult: ActivityResultLauncher<ScanOptions>? = null
    var listener: ScannerReaderListener? = null
    var scannerType = ScannerTypeEnum.PRODUCT_CODE_TYPES
    private lateinit var typedArray: TypedArray

    init {

        binding.errorLayout.visibility = View.GONE
        binding.layoutResult.visibility = View.GONE
        typedArray = context.obtainStyledAttributes(attributeSet,R.styleable.code_bar_reader_attributes)
        iniAttributes()
        handleCLickEvents()
    }

    private fun iniAttributes() {
        try {
            binding.description.text =
                typedArray.getString(R.styleable.code_bar_reader_attributes_code_bar_description)
            binding.description.setTextColor(
                typedArray.getColor(R.styleable.code_bar_reader_attributes_code_bar_description_color,
                    Color.BLUE)

            )
        } catch (e: Exception) {
            Log.e("qsdsqqs", "${e.message}")
        }
    }


    fun setActivity(activity: AppCompatActivity) {
        this.activity = activity
        initResultForActivity()

    }


    private fun handleCLickEvents() {
        binding.root.setOnClickListener {
            startScannerBarreCode()
        }

        binding.btnCopy.setOnClickListener {

            val result = binding.txtQrCodeResult.text.toString()
            if (result.isNotBlank()) {
                val clipBoard =
                    context.getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("", result)
                clipBoard.setPrimaryClip(clip)
                Toast.makeText(context, R.string.text_copied, Toast.LENGTH_SHORT).show()
                listener?.onCopyResult(result)

            }


        }
    }

    // Start the QR Scanner
    private fun startScannerBarreCode() {
        when (scannerType) {
            ScannerTypeEnum.PRODUCT_CODE_TYPES -> {
                if (activity == null) {
                    Toast.makeText(context, "Please initiate the activity attr", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                var qrScanner = ScanOptions()
                qrScanner.setDesiredBarcodeFormats(ScanOptions.PRODUCT_CODE_TYPES)
                qrScanner.setPrompt(context.getString(R.string.scan_barre_code))
                qrScanner.setCameraId(0)
                qrScanner.setOrientationLocked(false)
                qrScanner.setBeepEnabled(true)
                qrScanner.captureActivity = CaptureActivity::class.java
                registerForActivityResult?.launch(qrScanner)
            }
        }

    }

    private fun showResult(result: String) {
        if (result.isNotBlank()) {
            binding.txtQrCodeResult.text = result
            binding.layoutResult.visibility = View.VISIBLE
            binding.errorLayout.visibility = View.GONE
        } else {
            binding.layoutResult.visibility = View.GONE
            binding.errorLayout.visibility = View.VISIBLE
        }

    }

    private fun initResultForActivity() {
        registerForActivityResult =
            activity?.registerForActivityResult(
                ScanContract()
            ) { result ->
                if (result.contents == null) {
                    listener?.onError("empty")
                    showResult("")
                } else {
                    listener?.onGetResult(result.contents)
                    showResult(result.contents)
                }
            }
    }

}