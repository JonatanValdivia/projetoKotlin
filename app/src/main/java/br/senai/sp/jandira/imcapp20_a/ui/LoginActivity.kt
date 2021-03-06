package br.senai.sp.jandira.imcapp20_a.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import br.senai.sp.jandira.imcapp20_a.R
import br.senai.sp.jandira.imcapp20_a.dao.UsuarioDao
import br.senai.sp.jandira.imcapp20_a.utils.NovoUsuarioActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var editUser: EditText
    lateinit var editPassword: EditText
    lateinit var btnLogin: Button
    lateinit var tvMensagemErro: TextView
    lateinit var tvCrieSuaConta: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //24/09/2021
        //Remover a AppBar
        supportActionBar!!.hide()


        //Testando o método obterIdade
//        obterDiferencaEntreDatasEmAnos("10/10/1997")
//        obterDiferencaEntreDatasEmAnos("10/11/1997")
//        obterDiferencaEntreDatasEmAnos("15/08/1997")
//        obterDiferencaEntreDatasEmAnos("10/08/1954")
//        obterDiferencaEntreDatasEmAnos("1/2/1934")
//        obterDiferencaEntreDatasEmAnos("10/8/1944")
//        obterDiferencaEntreDatasEmAnos("1/01/1974")

        val dados = getSharedPreferences("dados_usuario", Context.MODE_PRIVATE)
        val lembrar = dados.getBoolean("lembrar", false)

        if (lembrar == true){
            abrirDashBoard()
        }

        editUser = findViewById(R.id.ed_user)
        editPassword = findViewById(R.id.ed_password)
        btnLogin = findViewById(R.id.btn_login)
        tvMensagemErro = findViewById(R.id.tv_mensagem_erro)
        tvCrieSuaConta = findViewById(R.id.tv_crie_sua_conta)

        tvCrieSuaConta.setOnClickListener {
            val intent = Intent(this, NovoUsuarioActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            login()
        }

    }

    private fun abrirDashBoard() {
        val intent = Intent(this, DashBoardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun login() {
        // email: user@email.com
        // senha: 123

        val user = editUser.text.toString()
        val pass = editPassword.text.toString()

//        val dados = getSharedPreferences("dados_usuario", Context.MODE_PRIVATE)
//
//        val userPreferences = dados.getString("email", "Não encontrado")
//        val passPreferences = dados.getString("senha", "Não encontrado")
//
//        if (user == userPreferences && pass == passPreferences){
//
//            // Gravar o lembrar no sharedPreferences
//            val editor = dados.edit()
//            editor.putBoolean("lembrar", check_lembrar.isChecked)
//            editor.apply()
//
//            abrirDashBoard()
//
//        } else {
//            tvMensagemErro.text = "Usuário ou senha incorretos!"
//        }

        val usuarioDao = UsuarioDao(this,   null)
        if(usuarioDao.autenticar(user, pass)){
            abrirDashBoard()
        }

    }
}