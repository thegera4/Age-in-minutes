package com.app.kot_age_to_minutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.kot_age_to_minutes.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    //agregar para nuevo metodo de UI binding 2021
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //agregar para nuevo metodo de UI binding 2021
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // aqui se modifica para nuevo metodo tmb

        //forma antigua y parecida a java
        //val button = findViewById<Button>(R.id.button)
        //forma nueva 2021
        //para utilizar esta forma se debe de agregar en gradle(app) debajo del block kotlinOptions
        //buildFeatures{
        //viewBinding true
        //}
        binding.button.setOnClickListener {view ->
            clickButton(view)
        }
    }

    private fun clickButton(view: View){

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        //abrir el calendario para seleccionar fecha
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                view, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(this, "The chosen year is $selectedYear, " +
                    "the month is $selectedMonth and the day is $selectedDayOfMonth"
                , Toast.LENGTH_SHORT).show()

            //se debe sumar 1 a month porque en el calendario 0 = Enero; 11=Diciembre
            val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
            binding.tvDATE.text = selectedDate

            //usado para dar formato a fechas
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

            val theDate = sdf.parse(selectedDate)

            val selectedDateInMinutes = theDate!!.time / 60000 //1000 para milisegundos

            val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

            val currentDateInMinutes = currentDate!!.time / 60000

            val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

            binding.tvMINS.text = differenceInMinutes.toString()

            val currentDateInDays = differenceInMinutes / (60 *24)

            binding.tvDAYS.text = currentDateInDays.toString()

        }, year, month, day)

        //esto permite limitar la seleccion del DatePicker
        dpd.datePicker.maxDate = Date().time - 86400000 //86400000 miliseconds for a day
        dpd.show()

    }
}

