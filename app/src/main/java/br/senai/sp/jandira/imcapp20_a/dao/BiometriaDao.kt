package br.senai.sp.jandira.imcapp20_a.dao

import android.content.ContentValues
import android.content.Context
import br.senai.sp.jandira.imcapp20_a.model.Biometria


class BiometriaDao (val context: Context, val biometria: Biometria?){
    val dbHelper = ImcDataBase.getDatabase(context) //chamando o banco de dados onde serão armazenados os dados de biometria (peso, nível de atividade)

//    fun gravar(){
//        val db = dbHelper.writableDatabase //Obtendo uma instância do banco de dados em modo de escrita
//
//        val dados = ContentValues()//Obtendo todos os dados que serão criados
//        dados.put("peso", biometria!!.peso) //Esses dois "!!" indicam que o valor inserido pode ser nulo. OBS: não precisamos passa-lo em todos os campos, apenas em um.
//        dados.put("nivel_atividade", biometria.nivelAtiviade)
//        dados.put("data_pesagem", biometria.dataPesagem)
//    }
}