package id.arahnets.willy.iso8583

import java.math.BigInteger
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.LinkedHashMap

class AplikasiClient {
    fun main (){

        val formatterBit7  = SimpleDateFormat("MMddHHmmss")

        val   logonRequest :MutableMap<Int, String>  = LinkedHashMap()
        logonRequest[7] = formatterBit7.format(Date())
        logonRequest[11] = "834624"
        logonRequest[70] = "001"

        val client = AplikasiClient()
        val bitmap = client.countBitmap(logonRequest)

        val strBitToBin = bitmap.toString(2)
        println("Convert Binary : $strBitToBin")

        val strBitToHex = bitmap.toString(16)
        println("Convert Hexa : $strBitToHex")
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