package br.senai.sp.jandira.imcapp20_a.utils

import android.util.Log
import java.text.DateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

fun obterDiferencaEntreDatasEmAnos(dataInicio: String): String{
    var hoje: LocalDate = LocalDate.now()

    var dataIni = LocalDate.parse(
        dataInicio,
        DateTimeFormatter.ofPattern("dd/MM/yyyy"))

//    var ano = dataInicio.substring(0, 4).toInt() //Convertemos para integer para usar no cálculo
//    var mes = dataInicio.substring(5, 7).toInt()
//    var dia = dataInicio.substring(8, 10).toInt()
//            Duration -> ver depois
//            var teste2: Period
//    var nascimento = LocalDate.of(1974, 5,  21)
    var idade = Period.between(dataIni, hoje)

    return idade.years.toString()

}