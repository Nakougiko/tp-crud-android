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
import com.lukasgoulois.tp6goulois.databinding.ActivityRemoveBinding

class RemoveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_remove)

        // Binding
        val binding = ActivityRemoveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // BDD
        val deptDAO = DeptDAO()
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
        binding.spinnerListRem.adapter = adapter

        // Listener pour le spinner
        binding.spinnerListRem.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val id = ids[position]
                val cursor = deptDAO.getDept(id)

                if (cursor.moveToFirst()) {
                    binding.textViewRemNom.text = (cursor.getString(cursor.getColumnIndexOrThrow("nom")))
                    binding.textViewRemChef.text = (cursor.getString(cursor.getColumnIndexOrThrow("chef")))
                    binding.textViewRemTechnicien.text = (cursor.getString(cursor.getColumnIndexOrThrow("technicien")))
                }
                cursor.close()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // Listener pour le bouton supprimer
        binding.buttonRemValider.setOnClickListener {
            val id = binding.spinnerListRem.selectedItem as Int
            deptDAO.deleteDept(id)
            Toast.makeText(this, "Département supprimé", Toast.LENGTH_SHORT).show()
            finish()
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}