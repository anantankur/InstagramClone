package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnKeyListener {

    TextView turn;
    Button logButton;
    EditText username, password;
    String userInput, passInput;

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            loginHandler(view);
        }
        return false;
    }

    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setTitle("Instagram Clone");

    turn = findViewById(R.id.turn);
    logButton = findViewById(R.id.logButton);
    username = findViewById(R.id.username);
    password = findViewById(R.id.pass);

    password.setOnKeyListener(this);

    if (ParseUser.getCurrentUser() != null) {
        showUserList();
    }
    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  public void bSwitch(View view) {

      String currText = turn.getText().toString();
      if(currText.equals("or, LogIn")) {
          logButton.setText("LogIn");
          turn.setText("or, SignUp");
      } else {
          logButton.setText("SignUp");
          turn.setText("or, LogIn");
      }

  }

  public void showUserList() {
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
        finish();
  }

  public void loginHandler(View view) {
      String currText = logButton.getText().toString();
      if(currText.equals("LogIn")) {
          login(view);
      } else {
          signup(view);
      }
  }

  private void login(View view) {
      userInput = username.getText().toString();
      passInput = password.getText().toString();

      if (userInput.equals("") || passInput.equals("")) {
          Toast.makeText(this, "Field cant be empty", Toast.LENGTH_SHORT).show();
      } else {
          ParseUser.logInInBackground(userInput, passInput, new LogInCallback() {
              @Override
              public void done(ParseUser parseUser, ParseException e) {
                  if(parseUser != null) {
                      Toast.makeText(MainActivity.this, parseUser.getUsername(), Toast.LENGTH_SHORT).show();
                      showUserList();
                  } else {
                      Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                  }
              }
          });
      }
  }

  private void signup(View view) {

      userInput = username.getText().toString();
      passInput = password.getText().toString();

      if (userInput.equals("") || passInput.equals("")) {
          Toast.makeText(this, "Field cant be empty", Toast.LENGTH_SHORT).show();
      } else {
          ParseUser user = new ParseUser();
          user.setUsername(userInput);
          user.setPassword(passInput);

          user.signUpInBackground(new SignUpCallback() {
              @Override
              public void done(ParseException e) {
                  if (e==null) {
                      Toast.makeText(MainActivity.this, "Successfully signed up", Toast.LENGTH_SHORT).show();
                      showUserList();
                  } else {
                      Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                  }
              }
          });
      }

  }

}