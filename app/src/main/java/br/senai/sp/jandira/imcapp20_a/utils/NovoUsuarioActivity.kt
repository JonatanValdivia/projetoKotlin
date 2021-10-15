package br.senai.sp.jandira.imcapp20_a.utils

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.senai.sp.jandira.imcapp20_a.R
import br.senai.sp.jandira.imcapp20_a.dao.UsuarioDao
import br.senai.sp.jandira.imcapp20_a.model.Usuario

//import responsável por usarmos os elementos (do layout) sem declaração
import kotlinx.android.synthetic.main.activity_novo_usuario.*
import java.util.*

//Constantes são ciradas antes da declaração da class
const val CODE_IMAGE = 100

class NovoUsuarioActivity : AppCompatActivity() {

    var imageBitMap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_usuario)
        //MENU
        supportActionBar!!.title = "Novo usuário" //Titulo
        supportActionBar!!.subtitle = "Cadastre os seus dados" //Subtitulo
        supportActionBar!!
            .setBackgroundDrawable(
                getDrawable(R.drawable.toolbar_background)) //Adicinar os icones

        supportActionBar!!.elevation = 0.0f

        //Continuamos por aqui 27/08/2021
        //detectar o click no texto "Trocar foto"
        tv_trocar_foto.setOnClickListener {
            abrirGaleria()
        }


        //Criar um calendário:
        val calendario = Calendar.getInstance() //Instancioams o Calendar da classe Java.util
        val ano = calendario.get(Calendar.YEAR) //Pegamos dessa instância de Calendar o ano
        val mes = calendario.get(Calendar.MONTH) //Pagamos o mês desse calendar
        val dia = calendario.get(Calendar.DAY_OF_MONTH) //Pegamos o dia do mês

        //Abrir o componente DatePicker
        et_data_nascimento.setOnClickListener {
            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener {view, _ano, _mes, _dia ->
                    var diaZero = _dia.toString()
                    var mesZero = _mes.toString()
                    if(_dia < 10){
                        diaZero = "0$_dia"
                    }

                    if(_mes < 10){
                        mesZero = "0${_mes + 1}"
                    }
                et_data_nascimento.setText("$diaZero/$mesZero/$_ano")
            }, ano, mes, dia)
            dpd.show()
        }

//        bt_gravar.setOnClickListener {
//            // *** Criar o sharedPreferences
////            val dados = getSharedPreferences("dados_usuario", Context.MODE_PRIVATE)
////
////            val editor = dados.edit()
////            editor.putString("nome", et_nome.text.toString())
////            editor.putString("profissao", et_profissao.text.toString())
////            editor.putInt("peso", et_peso.text.toString().toInt())
////            editor.putInt("idade", et_data_nascimento.text.toString().toInt())
////            editor.putString("email", et_email.text.toString())
////            editor.putString("senha", et_senha.text.toString())
////            editor.apply()
//
//            //Gravar o novo usuário no banco de dados
//
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_novo_usuario, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save ->{
                salvar()
//                Toast.makeText(this, "Salvar", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.menu_cancelar ->{
                //salvar()
//                Toast.makeText(this, "Cancelar", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.menu_help ->{
                //salvar()
//                Toast.makeText(this, "Ajudar", Toast.LENGTH_SHORT).show()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun salvar(){
        val usuario = Usuario(
            0,
            et_email.text.toString(),
            et_senha.text.toString(),
            et_nome.text.toString(),
            et_profissao.text.toString(),
            et_altura.text.toString().toDouble(),
            et_data_nascimento.text.toString(),
            'M',
            imageBitMap

        )

        val usuarioDao = UsuarioDao(this, usuario)
        usuarioDao.gravar()


        Toast.makeText(this, "Dados gravados com sucesso!!", Toast.LENGTH_SHORT).show()

        finish()
    }

    private fun abrirGaleria() {
        //Podemos chamar outras aplicações (por exemplo a galeria), através da nossa aplicação.
        //Chamando a galeria de imagens
        //Queremos uma ação que pegue um contexto
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        //Definir qual o tipo de conteúdo que queremos obter
        intent.type = "image/*" //Qualquer imagem (extensão)

        //Iremos inicializar a activity, mas neste caso queremos
        //que a activity nos retorne algo (no caso a imagem)
        startActivityForResult(
            Intent.createChooser(intent,"Escolha uma foto"),
            CODE_IMAGE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i("xptw", "RESULT_CODE = $resultCode REQUEST_CODE = $resultCode")

        if(requestCode == CODE_IMAGE && resultCode == -1){
            //Guardará todos os bits da imagem
            //Recuperar a imagem no stream              //Ambos podem retornar nulos (!!)
            val stream = contentResolver.openInputStream(data!!.data!!) //uri qualquer path

            //Transformar o stream em um BitMap
                          //Uma classe  //Decodificar o stream
            imageBitMap = BitmapFactory.decodeStream(stream)

            //Colocar a imagem no image view
            img_profile.setImageBitmap(imageBitMap)

        }
    }
}