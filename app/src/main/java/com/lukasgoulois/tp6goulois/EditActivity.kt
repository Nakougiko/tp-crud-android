package com.lukasgoulois.tp6goulois

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lukasgoulois.tp6goulois.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    // BDD
    private lateinit var deptDAO: DeptDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_edit)

        // Binding
        val binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // BDD
        deptDAO = DeptDAO()
        deptDAO.open(this)

        // Initialiser le spinners avec les ids des éléments de la bdd
        val ids = ArrayList<Int>()
        val cursor = deptDAO.getIds()

        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getInt(cursor.getColumnIndexOrThrow("id")))
            } while (cursor.moveToNext())
        }
        cursor.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ids)
        binding.spinnerList.adapter = adapter

        // Listener pour le spinner
        binding.spinnerList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val id = ids[position]
                val cursor = deptDAO.getDept(id)

                if (cursor.moveToFirst()) {
                    binding.editTextEditNom.setText(cursor.getString(cursor.getColumnIndexOrThrow("nom")))
                    binding.editTextEditChef.setText(cursor.getString(cursor.getColumnIndexOrThrow("chef")))
                    binding.editTextEditTechnicien.setText(cursor.getString(cursor.getColumnIndexOrThrow("technicien")))
                }
                cursor.close()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ne rien faire
            }
        }

        // Listener pour le bouton valider
        binding.buttonValider.setOnClickListener {
            val id = binding.spinnerList.selectedItem as Int
            val nom = binding.editTextEditNom.text.toString()
            val chef = binding.editTextEditChef.text.toString()
            val technicien = binding.editTextEditTechnicien.text.toString()

            deptDAO.editDept(id, Dept(id, nom, chef, technicien))
            Toast.makeText(this, "Département modifié", Toast.LENGTH_SHORT).show()

            // ClearFields
            binding.editTextEditNom.text.clear()
            binding.editTextEditChef.text.clear()
            binding.editTextEditTechnicien.text.clear()

            // Retour sur MainActivity
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}