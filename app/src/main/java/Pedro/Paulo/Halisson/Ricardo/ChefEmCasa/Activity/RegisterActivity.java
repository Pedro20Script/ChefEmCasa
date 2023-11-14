package Pedro.Paulo.Halisson.Ricardo.ChefEmCasa.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Pedro.Paulo.Halisson.Ricardo.ChefEmCasa.Model.LoginViewModel;
import Pedro.Paulo.Halisson.Ricardo.ChefEmCasa.Model.RegisterViewModel;
import Pedro.Paulo.Halisson.Ricardo.ChefEmCasa.R;
import Pedro.Paulo.Halisson.Ricardo.ChefEmCasa.Util.Config;

public class RegisterActivity extends AppCompatActivity {

    static int RESULT_REQUEST_PERMISSION = 2;
    RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Button btnRegister = findViewById(R.id.btnCadastro);


        // A função que entra em contato com o servidor web está definida dentro da ViewModel
        // referente a essa Activity
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pega o edit text
                EditText etUser = findViewById(R.id.etUser);
                final String textoUser = etUser.getText().toString();
                if(textoUser.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Campo de login não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }
                //Pega o edit text
                EditText etEmail = findViewById(R.id.etEmail);
                //Texto dentro do edit text
                final String textoEmail = etEmail.getText().toString();
                if(textoEmail.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Campo de email não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etSenha = findViewById(R.id.etSenha);
                final String textoSenha = etSenha.getText().toString();
                if(textoSenha.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Campo de senha não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etConfirmSenha = findViewById(R.id.etConfirmSenha);
                final String textoConfirmSenha = etConfirmSenha.getText().toString();
                if(textoConfirmSenha.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Campo de checagem de senha não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!textoSenha.equals(textoConfirmSenha)) {
                    Toast.makeText(RegisterActivity.this, "Senha não confere", Toast.LENGTH_LONG).show();
                    return;
                }
                //Conexão entre as duas telas
                LiveData<Boolean> resultLD = registerViewModel.register(textoEmail, textoSenha, textoUser);
                // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado indicando
                // se o login deu certo ou não será guardado dentro do LiveData. Neste momento o
                // LiveData avisa que o resultado chegou chamando o método onChanged abaixo.
                resultLD.observe(RegisterActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        // aBoolean contém o resultado do login. Se aBoolean for true, significa
                        // que as infos de login e senha enviadas ao servidor estão certas. Neste
                        // caso, guardamos as infos de login e senha dentro da app através da classe
                        // Config. Essas infos de login e senha precisam ser guardadas dentro da app
                        // para que possam ser usadas quando a app pedir dados ao servidor web que só
                        // podem ser obtidos se o usuário enviar o login e senha.
                        if(aBoolean) {
                            Toast.makeText(RegisterActivity.this, "Novo usuario registrado com sucesso", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            // Se o cadastro não deu certo, apenas continuamos na tela de cadastro e
                            // indicamos com uma mensagem ao usuário que o cadastro não deu certo.
                            Toast.makeText(RegisterActivity.this, "Erro ao registrar novo usuário", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

}