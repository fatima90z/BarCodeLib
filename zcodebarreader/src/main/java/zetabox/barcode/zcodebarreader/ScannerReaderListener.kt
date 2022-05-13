package zetabox.barcode.zcodebarreader

/**
 *Created by fatma on 12/04/2022.

 **/
interface ScannerReaderListener {

    fun onGetResult(code: String)
    fun onCopyResult(code: String)
    fun onError(error: String)
}