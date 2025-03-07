package com.lukasgoulois.tp6goulois

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lukasgoulois.tp6goulois.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Binding
    private lateinit var binding: ActivityMainBinding

    // BDD
    private lateinit var deptDAO: DeptDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)

        // binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // BDD
        deptDAO = DeptDAO()
        deptDAO.open(this)

        // Initialisation
        refreshNbDepts()
        var compteurId = deptDAO.nbDepts()

        // Listener Afficher
        binding.buttonAfficher.setOnClickListener {
            // Les départements sont affichés sous forme de Toast successifs
            val cursor = deptDAO.getDepts()
            if (cursor.moveToFirst()) {
                do {
                    val dept = Dept(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nom")),
                        cursor.getString(cursor.getColumnIndexOrThrow("chef")),
                        cursor.getString(cursor.getColumnIndexOrThrow("technicien"))
                    )
                    Toast.makeText(this, dept.toString(), Toast.LENGTH_SHORT).show()
                } while (cursor.moveToNext())

            } else {
                Toast.makeText(this, "Aucun département", Toast.LENGTH_SHORT).show()
            }

            cursor.close()
        }

        // Listener Ajouter
        binding.buttonAjouter.setOnClickListener {
            if (checkFields()) {
                deptDAO.addDept(
                    Dept(
                        compteurId++,
                        binding.editTextNom.text.toString(),
                        binding.editTextChef.text.toString(),
                        binding.editTextTechnicien.text.toString()
                    )
                )
                Toast.makeText(this, "Département ajouté", Toast.LENGTH_SHORT).show()
                clearFields()
                refreshNbDepts()
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Verifie si les champs sont remplis
    private fun checkFields(): Boolean {
        return binding.editTextNom.text.isNotEmpty() && binding.editTextChef.text.isNotEmpty() && binding.editTextTechnicien.text.isNotEmpty()
    }

    // Nettoie les champs
    private fun clearFields() {
        binding.editTextNom.text.clear()
        binding.editTextChef.text.clear()
        binding.editTextTechnicien.text.clear()
    }

    // Fonction pour rafraichir le nombre de départements
    private fun refreshNbDepts() {
        binding.textViewVarNbDepts.text = "${deptDAO.nbDepts()}"
    }
}