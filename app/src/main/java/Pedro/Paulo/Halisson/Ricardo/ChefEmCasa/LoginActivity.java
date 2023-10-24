package Pedro.Paulo.Halisson.Ricardo.ChefEmCasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogar = findViewById(R.id.btnLogar);
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pega o edit text
                EditText etEmail = findViewById(R.id.etEmailLogin);
                //Texto dentro do edit text
                String textoEmail = etEmail.getText().toString();
                EditText etSenha = findViewById(R.id.etSenha);
                String textoSenha = etSenha.getText().toString();
                //Conexão entre as duas telas
                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                //Passa o texto para a próxima tela
                intent.putExtra("textoEmail",textoEmail);
                intent.putExtra("textoSenha",textoSenha);
                //Vai para a próxima tela
                startActivity(intent);
            }
        });
    }
}