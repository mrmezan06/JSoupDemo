package com.mezan.jsoupdemo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button getBtn;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        getBtn = (Button) findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWebsite();
            }
        });
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("http://www.ssaurel.com/blog").get();
                    String title = doc.title();
                    //Elements links = doc.select("a[href]");
                    Elements links = doc.select("div[class]");

                    builder.append(title).append("\n");

                    for (Element link : links) {
                       /* builder.append("\n").append("Link : ").append(link.attr("href"))
                                .append("\n").append("Text : ").append(link.text()).append("\n");*/
                        builder.append("\n").append("Class : ").append(link.attr("class"))
                                .append("\n").append("Text : ").append(link.text()).append("\n");
                    }
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                    }
                });
            }
        }).start();
    }
}
