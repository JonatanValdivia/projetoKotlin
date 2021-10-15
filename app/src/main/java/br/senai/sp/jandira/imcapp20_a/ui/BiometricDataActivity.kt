package br.senai.sp.jandira.imcapp20_a.ui

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import br.senai.sp.jandira.imcapp20_a.R
import br.senai.sp.jandira.imcapp20_a.dao.ImcDataBase
import br.senai.sp.jandira.imcapp20_a.model.Biometria
import br.senai.sp.jandira.imcapp20_a.model.Usuario
import br.senai.sp.jandira.imcapp20_a.utils.converterBitmapParaByteArray
import kotlinx.android.synthetic.main.activity_biometric.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.SimpleFormatter

class BiometricDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biometric)

        val date = Calendar.getInstance().time
        var dateTimeFormat = SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.getDefault())


        data_pesagem.setText(dateTimeFormat.format(date), TextView.BufferType.EDITABLE)

        button_salvar_dados_biometricos.setOnClickListener {
            val biometria = Biometria(
                0,
                edit_text_peso.text.toString().toDouble(),
                spinner_atividades.selectedItemPosition.toString().toInt(),
                data_pesagem.text.toString(),
                Usuario = 0

            )

            Log.i("AAAAAAa", biometria.toString())

            val dbHelper = ImcDataBase.getDatabase(this)
            val db = dbHelper.writableDatabase
            // *** Criar os valores que serão inseridos no banco
            val dados = ContentValues()
            dados.put("peso", biometria!!.peso)
            dados.put("nivel_atividade", biometria.nivelAtiviade)
            dados.put("data_pesagem", biometria.dataPesagem)
            dados.put("id_usario", biometria.Usuario)

            // *** Executar o comando de gravação
            db.insert("tb_biometria", null, dados)

            db.close()
        }
    }
}