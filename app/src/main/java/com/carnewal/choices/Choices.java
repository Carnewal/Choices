package com.carnewal.choices;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Choices extends AppCompatActivity {

    @Bind(R.id.etURL)
    public EditText etURL;

    @Bind(R.id.etSearch)
    public EditText etSearch;

    private static String TAG = Choices.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);
        ButterKnife.bind(this);
        etURL = (EditText) findViewById(R.id.etURL);
    }

    public void actionSearch(View view) {
        String query = etSearch.getText().toString();
        Uri uri = Uri.parse("https://www.google.be/search?q=" + query);
        startIntent(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void actionUrl(View view) {
        String url = etURL.getText().toString().replace(" ", "");

        if (!url.isEmpty() && url.contains(".")) {
            if(!url.startsWith("http://")) {
                url = "http://" + url;
            }
            Uri uri = Uri.parse(url);
           startIntent(new Intent(Intent.ACTION_VIEW, uri));
        } else {
            etURL.setError("Please enter a non-empty string with at least one dot.");
        }
    }

    public void actionContacts(View view) {
        startIntent(new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI));
    }

    public void actionDialer(View view) {
        startIntent(new Intent(Intent.ACTION_DIAL));
    }

    public void actionVoice(View view) {
        startIntent(new Intent(Intent.ACTION_VOICE_COMMAND));
    }

    private void startIntent(Intent intent) {

        if(intent != null && checkIntent(intent) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(Choices.this, "Couldn't find that intent.", Toast.LENGTH_LONG).show();
        }

    }


    private Intent checkIntent(Intent i) {
        PackageManager packageManager = getPackageManager();
        ComponentName cn = i.resolveActivity(packageManager);
        if(cn != null) {
            return i;
        }
        return null;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choices, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
