package com.sud.laundry.presentation.newsContent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.grocery.R;
import com.grocery.bases.BaseActivity;
import com.grocery.presentation.cart.CartActivity;
import com.grocery.presentation.search.SearchActivity;
import com.grocery.utils.KeyData;
import com.grocery.utils.Utils;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsOfferActivity extends BaseActivity {


    @BindView(R.id.tv_body)
    TextView tv_body;

    @BindView(R.id.toolbar_center_title)
    TextView toolbar_center_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_offer;
    }

    @Override
    public void setView() {
        super.setView();

        String stTitle = getIntent().getStringExtra(KeyData.KEY_TITLE);
        String stBody = getIntent().getStringExtra(KeyData.KEY_BODY);

        toolbar_center_title.setText(stTitle + "");
        WebView webView = (WebView) findViewById(R.id.webView1);
        String pdf = "";
        if (stBody.contains(".pdf") || stBody.contains(".PDF")) {
            pdf = stBody;
            webView.setVisibility(View.VISIBLE);
            tv_body.setVisibility(View.GONE);
        } else {
            webView.setVisibility(View.GONE);
            tv_body.setVisibility(View.VISIBLE);
            tv_body.setText(stBody + "");
        }


        Utils.showProfressBarActivity(this);


        String doc = "<iframe src='http://docs.google.com/gview?embedded=true&url=" + pdf + "' width='100%' height='100%' style='border: none;'></iframe>";



        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setPluginsEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.loadData(doc, "text/html", "UTF-8");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                //change your progress bar
                if (newProgress == 0) {
                    Utils.showProfressBarActivity(NewsOfferActivity.this);
                } else if (newProgress == 100) {
                    Utils.dissmissActiviity();
                }
                super.onProgressChanged(view, newProgress);
            }
        });


    }


    @OnClick({R.id.im_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_back:
                finish();
                break;

        }
    }


}