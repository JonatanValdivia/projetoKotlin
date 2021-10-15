package br.senai.sp.jandira.imcapp20_a.dao

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import br.senai.sp.jandira.imcapp20_a.model.Usuario
import br.senai.sp.jandira.imcapp20_a.ui.DashBoardActivity
import br.senai.sp.jandira.imcapp20_a.utils.converterBitMapEmBase64
import br.senai.sp.jandira.imcapp20_a.utils.converterBitmapParaByteArray
import br.senai.sp.jandira.imcapp20_a.utils.converterByteArrayEmBitMap
import br.senai.sp.jandira.imcapp20_a.utils.obterDiferencaEntreDatasEmAnos
import java.time.Duration
import java.time.LocalDate
import java.time.Period

class UsuarioDao(val context: Context, val usuario: Usuario?) {

    val dbHelper = ImcDataBase.getDatabase(context) //Chamando em si o banco de dados, onde será armazenado os dados

    public fun gravar() { //Onde é realizada a inserção de dados

        // *** obter uma instância do banco para escrita
        val db = dbHelper.writableDatabase
        // *** Criar os valores que serão inseridos no banco
        val dados = ContentValues()
        dados.put("nome", usuario!!.nome)
        dados.put("profissao", usuario.profissao)
        dados.put("email", usuario.email)
        dados.put("senha", usuario.senha)
        dados.put("altura", usuario.altura)
        dados.put("data_nascimento", usuario.dataNascimento)
        dados.put("sexo", usuario.sexo.toString())
        dados.put("foto", converterBitmapParaByteArray(usuario.foto))

        // *** Executar o comando de gravação
        db.insert("tb_usuario", null, dados)

        db.close()
    }

    public fun autenticar(email: String, senha: String): Boolean {


        //Obter uma instância para leitura do banco de dados
        val db = dbHelper.readableDatabase

        //Precisamos determinar e definir quais são as colunas da tabela do banco que nós queremos no resultado.
        //Criaremos uma coisa que nós chamamos de projeção (uma array que tem os resultados que necessitamos)
        val campos = arrayOf("email",
                            "senha",
                            "nome",
                            "profissao",
                            "data_nascimento",
                            "foto") //Acrescentar o campo foto

        //Definiremos agora o filtro. (seria como o where -> retorna apenas o que queremos)
        //O que estamos fazendo é construir o filtro
        //"WHERE email = ? AND senha = ?"

        val filtro = "email = ? AND senha = ?"

        //Criaremos agora os argumentos do filtro
        //Vamos dizer ao Kotlin quais são os valores
        //Que deverão ser substituídos pelos "?" no filtro

        val argumentos = arrayOf(email, senha)//seria similar ao bindValue() do php

        //Executar a consulta e obter o resultado em um cursor (similar ao fetch)
        //Recebe o resultado do select

        val cursor = db.query(
            "tb_usuario",
            campos,
            filtro,
            argumentos,
            null, //Não vamos fazer agrupamento
            null, //Não estamos fazendo filtro por agrupamento
            null //Não teremos "order by"
        )

        Log.i("XPTY", "Linhas: ${cursor.count.toString()}")

        //Alteramos a classe usuarioDao, também mudamos no LoginActivity

        //Lendo determinados valores

        //Guardar a quantidade de linhas obtidas na consulta
        val linhas = cursor.count
        var autenticado = false

        if(linhas > 0){
            autenticado = true

            //Movendo/obtendo para/a primeira linha - Obtendo os índices atuais
            cursor.moveToFirst()
            val dados = context.getSharedPreferences("dados_usuario", Context.MODE_PRIVATE)
            val editor = dados.edit()

            val emailIndex = cursor.getColumnIndex("email")
            val nomeIndex = cursor.getColumnIndex("nome")
            val profissaoIndex = cursor.getColumnIndex("profissao")
            val dataNascimento = cursor.getColumnIndex("data_nascimento")
            val fotoIndex = cursor.getColumnIndex("foto")

            //CRIAÇÃO/ATUALIZAÇÃO do sharedPreferences que será utilizado no restante da aplicação
            editor.putString("nome", cursor.getString(nomeIndex))
            editor.putString("email", cursor.getString(emailIndex))
            editor.putString("profissao", cursor.getString(profissaoIndex))
            editor.putString("idade", obterDiferencaEntreDatasEmAnos(cursor.getString(dataNascimento)))
            editor.putInt("peso", 0)
            //Converter o byteArray do banco em Bitmap
            var bitmap = converterByteArrayEmBitMap(cursor.getBlob(fotoIndex))

            editor.putString("foto", converterBitMapEmBase64(bitmap))

            editor.apply()
            Log.i("XPTY", cursor.getString(emailIndex))

        }
        db.close()
        return autenticado //Muda-se o retorno da função

    }

}