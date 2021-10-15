package br.senai.sp.jandira.imcapp20_a.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.util.*


                            //Podemos receber um nulo e podemos retornar um nulo também.
fun converterBitmapParaByteArray(imagem: Bitmap?): ByteArray?{
    //Vamos serealizar
    val stream = ByteArrayOutputStream()
    if(imagem != null){
        val imageArray = imagem.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
    //Agora vamos comprimir, mas essa compressão é o contrário

    //.compress (formatação de compressão de um bitmap para PNG, a qualidade (não queremos perdê-la, então deixamos-a com zero) )

   return null


//        .decodeByteArray(imagem, 0, )

//    decodeByteArray(imagem, compressão min 0 max 100,  )
}

fun converterByteArrayEmBitMap (imagem : ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(
        imagem, 0, imagem.size
    )
}

fun converterBitMapEmBase64  (bitmap: Bitmap): String {
    val imagemArray = converterBitmapParaByteArray(bitmap)
    return Base64.encodeToString(imagemArray, Base64.DEFAULT)
}


fun converterBase64EmBitMap(imagem64: String?): Bitmap?{
    var imageArray = Base64.decode(imagem64, Base64.DEFAULT)
    return converterByteArrayEmBitMap(imageArray)
}
