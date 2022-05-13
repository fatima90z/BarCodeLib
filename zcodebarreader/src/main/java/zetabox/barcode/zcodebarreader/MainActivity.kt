package zetabox.barcode.zcodebarreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import zetabox.barcode.zcodebarreader.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.barCodeReader.setActivity(this)

        binding.barCodeReader.setEnable(true)
          binding.barCodeReader.scannerType =  ScannerTypeEnum.QR_CODE
        //binding.barCodeReader.scannerType =  ScannerTypeEnum.BARE_CODE

        binding.barCodeReader.listener  = object  : ScannerReaderListener{
            override fun onGetResult(code: String) {
                Log.e("result","onGetResult $code")
            }
            override fun onCopyResult(code: String) {
                    Log.e("result","onCopyResult $code")
            }
            override fun onError(error: String) {
                Log.e("result","onError $error")
            }
        }

    }
}