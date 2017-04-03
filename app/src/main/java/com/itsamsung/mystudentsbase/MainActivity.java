package com.itsamsung.mystudentsbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(getApplicationContext());
        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

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

    public void click(View view) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sil[] = new String[] {"cha", "ka", "na", "sa"};
        StringBuilder name = new StringBuilder();
        StringBuilder surname = new StringBuilder();;
        for (int i = 0; i < 3; i++) {
            name.append(sil[(int) (Math.random() * sil.length)]);
            surname.append(sil[(int) (Math.random() * sil.length)]);
        }
        cv.put("name", name.toString());
        cv.put("surname", surname.toString());
        cv.put("age", (int) (Math.random() * 5 + 12));

        long id = db.insert("students", null, cv);

        Cursor cursor = db.query("students", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");
            int nameColIndex = cursor.getColumnIndex("name");
            int surnameColIndex = cursor.getColumnIndex("surname");
            int ageColIndex = cursor.getColumnIndex("age");
            textView.setText("");
            do {
                textView.setText(textView.getText() + "\n"
                        + " " +  cursor.getInt(idColIndex)
                        + " " + cursor.getString(nameColIndex)
                        + " " + cursor.getString(surnameColIndex)
                        + " " + cursor.getInt(ageColIndex));
            } while (cursor.moveToNext());
            cursor.close();
        }

        dbHelper.close();
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "school.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table students(id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(20), surname varchar(20), age tinyint);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
