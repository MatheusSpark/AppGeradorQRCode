package devandroid.matheus.appgeradorqecode;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class MainActivity extends AppCompatActivity {
    TextView txTitle;
    EditText etLink;
    Button btnCancelar;
    Button btnGerar;
    ImageView imgQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        listennerClick();
    }
    private void initComponent() {
        etLink = findViewById(R.id.etLink);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnGerar = findViewById(R.id.btnGerar);
        imgQrCode = findViewById(R.id.imgQRCode);
    }

    private void listennerClick() {
        btnGerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etLink.getText().toString())) {
                    etLink.setError("Campo obrigatório");
                    etLink.requestFocus();
                }else{
                    gerarQRCode(etLink.getText().toString());
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etLink.setText("");
                imgQrCode.setImageBitmap(null);
            }
        });
    }

    private void gerarQRCode(String conteudoDoQRCode) {
        QRCodeWriter qrCode = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCode.encode(conteudoDoQRCode, BarcodeFormat.QR_CODE, 200, 200);
            Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);
            for (int x = 0; x < 200; x++) {
                for (int y = 0; y < 200; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imgQrCode.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }
    }



}