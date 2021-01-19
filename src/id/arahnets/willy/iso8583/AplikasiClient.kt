package id.arahnets.willy.iso8583

import java.math.BigInteger
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.LinkedHashMap

class AplikasiClient {
    fun main (){

        val formatterBit7  = SimpleDateFormat("MMddHHmmss")

        println("========================= REQUEST =========================")

        val   logonRequest :MutableMap<Int, String>  = LinkedHashMap()
        logonRequest[7] = formatterBit7.format(Date())
        logonRequest[11] = "834624"
        logonRequest[70] = "001"

        //get bitmap request
        val client = AplikasiClient()
        val bitmapRequest = client.countBitmap(logonRequest)
        println("Bitmap : $bitmapRequest")

        //convert bitmap to binary
        val strBitToBinRequest = bitmapRequest.toString(2)
        println("Convert Binary : $strBitToBinRequest")

        //convert bitmap to hexa
        val strBitToHexRequest = bitmapRequest.toString(16)
        println("Convert Hexa : $strBitToHexRequest")

        println("========================= RESPONSE =========================")

        val   logonResponse :MutableMap<Int, String>  = LinkedHashMap()
        logonResponse[7] = formatterBit7.format(Date())
        logonResponse[11] = "834624"
        logonResponse[39] = "00"
        logonResponse[70] = "001"

        //get bitmap request
        val bitmapResponse = client.countBitmap(logonResponse)
        println("Bitmap : $bitmapRequest")

        //convert bitmap to binary
        val strBitToBinResponse = bitmapResponse.toString(2)
        println("Convert Binary : $strBitToBinResponse")

        //convert bitmap to hexa
        val strBitToHexResponse = bitmapResponse.toString(16)
        println("Convert Hexa : $strBitToHexResponse")
    }

    fun countBitmap(message: MutableMap <Int, String> ):BigInteger{

        var bitmap = BigInteger.ZERO

        for (dataElement in message.keys){
            if (dataElement > 64){
                bitmap = bitmap.setBit(128-1)
            }
            bitmap = bitmap.setBit(128 - dataElement)
        }

        return bitmap

    }

}