package br.senai.sp.jandira.imcapp20_a.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.senai.sp.jandira.imcapp20_a.R
import br.senai.sp.jandira.imcapp20_a.utils.converterBase64EmBitMap
import kotlinx.android.synthetic.main.activity_dash_board.*


class DashBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        preencherDashBoard()

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(R.string.tittle_dialog)
        alertDialog.setMessage("Complete o seu cadastro para obter uma melhor experiência no App, informando seus dados biométricos para utilizar todo o recurso da aplicação.")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Completar", {
            dialogInterface, i ->
            var intent = Intent(this, BiometricDataActivity::class.java)
            startActivity(intent)
        })
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Depois", {
            dialogInterface, i ->
            Toast.makeText(this, "Assim que possível, complete o seu cadastro!", Toast.LENGTH_LONG).show()
        })
        alertDialog.show()

        tv_logout.setOnClickListener {
            val dados = getSharedPreferences("dados_usuario", Context.MODE_PRIVATE)
            val editor = dados.edit()
            editor.putBoolean("lembrar", false)
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun preencherDashBoard() {
        val dados = getSharedPreferences("dados_usuario", Context.MODE_PRIVATE)

        tv_profile_name.text = dados.getString("nome", "")
        tv_profile_occupation.text = dados.getString("profissao", "")
        tv_weight.text = dados.getInt("peso", 0).toString()
        tv_age.text = dados.getString("idade", "")

        val imagemBase64 = dados.getString("foto", "")

        val imagemBitmap = converterBase64EmBitMap(imagemBase64)

        iv_profile.setImageBitmap(imagemBitmap)

        // *** Colocar foto do Github no ImageView
//        val url = "https://avatars.githubusercontent.com/u/14265058?v=4"
//        Glide.with(this).load(url).into(iv_profile)

    }


}
