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
import Pedro.Paulo.Halisson.Ricardo.ChefEmCasa.R;
import Pedro.Paulo.Halisson.Ricardo.ChefEmCasa.Util.Config;

public class LoginActivity extends AppCompatActivity {
    static int RESULT_REQUEST_PERMISSION = 2;
    LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogar = findViewById(R.id.btnLogar);
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        checkForPermissions(permissions);

        // A função que entra em contato com o servidor web está definida dentro da ViewModel
        // referente a essa Activity
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pega o edit text
                EditText etEmail = findViewById(R.id.etEmailLogin);
                //Texto dentro do edit text
                final String textoEmail = etEmail.getText().toString();
                EditText etSenha = findViewById(R.id.etSenhaLogin);
                final String textoSenha = etSenha.getText().toString();
                //Conexão entre as duas telas
                LiveData<Boolean> resultLD = loginViewModel.login(textoEmail, textoSenha);

                // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado indicando
                // se o login deu certo ou não será guardado dentro do LiveData. Neste momento o
                // LiveData avisa que o resultado chegou chamando o método onChanged abaixo.
                resultLD.observe(LoginActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        // aBoolean contém o resultado do login. Se aBoolean for true, significa
                        // que as infos de login e senha enviadas ao servidor estão certas. Neste
                        // caso, guardamos as infos de login e senha dentro da app através da classe
                        // Config. Essas infos de login e senha precisam ser guardadas dentro da app
                        // para que possam ser usadas quando a app pedir dados ao servidor web que só
                        // podem ser obtidos se o usuário enviar o login e senha.
                        if(aBoolean) {
                            // guarda os dados de login e senha dentro da app
                            Config.setLogin(LoginActivity.this, textoEmail);
                            Config.setPassword(LoginActivity.this, textoSenha);

                            // exibe uma mensagem indicando que o login deu certo
                            Toast.makeText(LoginActivity.this, "Login realizado com sucesso", Toast.LENGTH_LONG).show();

                            // Navega para tela principal
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);
                        }
                        else {

                            // Se o login não deu certo, apenas continuamos na tela de login e
                            // indicamos com uma mensagem ao usuário que o login não deu certo.
                            Toast.makeText(LoginActivity.this, "Não foi possível realizar o login da aplicação", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pega o edit text

                //Conexão entre as duas telas
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                //Passa o texto para a próxima tela
                //Vai para a próxima tela
                startActivity(intent);
            }
        });
    }
        private void checkForPermissions(List<String> permissions) {
            List<String> permissionsNotGranted = new ArrayList<>();

            for(String permission : permissions) {
                if( !hasPermission(permission)) {
                    permissionsNotGranted.add(permission);
                }
            }

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(permissionsNotGranted.size() > 0) {
                    requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]),RESULT_REQUEST_PERMISSION);
                }
            }
        }

        /**
         * Verifica se uma permissão já foi concedida
         * @param permission
         * @return true caso sim, false caso não.
         */
        private boolean hasPermission(String permission) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return ActivityCompat.checkSelfPermission(LoginActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
            }
            return false;
        }

        /**
         * Método chamado depois que o usuário já escolheu as permissões que quer conceder. Esse método
         * indica o resultado das escolhas do usuário.
         */
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            final List<String> permissionsRejected = new ArrayList<>();
            if(requestCode == RESULT_REQUEST_PERMISSION) {

                for(String permission : permissions) {
                    if(!hasPermission(permission)) {
                        permissionsRejected.add(permission);
                    }
                }
            }

            if(permissionsRejected.size() > 0) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                        new AlertDialog.Builder(LoginActivity.this).
                                setMessage("Para usar essa app é preciso conceder essas permissões").
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), RESULT_REQUEST_PERMISSION);
                                    }
                                }).create().show();
                    }
                }
            }
        }
}