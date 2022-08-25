package com.example.symmetric_enctyption

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.symmetric_enctyption.Asymmetric.Companion.decryptMessage
import com.example.symmetric_enctyption.Asymmetric.Companion.encryptMessage
import com.example.symmetric_enctyption.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        testAsymmetric()
        binding.apply {
            bEncrypt.setOnClickListener {
                if (etEncrypt.text.isNotEmpty()) {
                    val encryptedText = Symmetric.encrypt(etEncrypt.text.toString())
                    val decryptedText = Symmetric.decrypt(encryptedText)
                    tvEncrypt.text = "Encrypted text: $encryptedText \n" +
                            "Decrypted text: $decryptedText"

                    etEncrypt.text.clear()
                }else {
                    Toast.makeText(this@MainActivity, "Write something", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun testAsymmetric() {
        val secretText = binding.etEncrypt2.text.toString()
        val keyPairGenerator = Asymmetric()
        val privateKey = Base64.getEncoder().encodeToString(keyPairGenerator.privateKey.encoded)
        val publicKey = Base64.getEncoder().encodeToString(keyPairGenerator.publicKey.encoded)
        binding.tvKeys.text = "PrivateKey: $privateKey \n\n PublicKey: $publicKey"


        val encryptedText = encryptMessage(secretText,publicKey)
        val decryptedText = decryptMessage(encryptedText,privateKey)

        binding.bEncrypt2.setOnClickListener {
            if (binding.etEncrypt2.text.isNotEmpty()) {
                binding.tvEncrypt2.text = "Encrypted text: $encryptedText \n\n Decrypted text: $decryptedText"
                binding.etEncrypt2.text.clear()
            }else {
                Toast.makeText(this@MainActivity, "Write something", Toast.LENGTH_SHORT).show()
            }
        }
    }
}