package zm.com.self.zmlife.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import zm.com.self.zmlife.R;

public class HomeItemActivity extends AppCompatActivity {

    @InjectView(R.id.image_back)
    ImageButton imageBack;
    @InjectView(R.id.webview_item)
    WebView webviewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_item);
        ButterKnife.inject(this);
        String url= getIntent().getStringExtra("website");
        webviewItem.loadUrl(url);
    }

    @OnClick(R.id.image_back)
    public void onViewClicked() {
        finish();
    }
}
