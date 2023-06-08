package it_school.sumdu.edu.mycatalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class AddItem extends AppCompatActivity {
    private ImageView mItemImage;
    private EditText mName;
    private EditText mAmount;
    private EditText mPrice;
    private EditText mItemDescription;
    private Button mAddImageButton;
    private Button mSaveButton;
    private DbHelper dbHelper;
    private static final int REQUEST_CODE = 1;
    private Bitmap selectedImage;
    private final int REQUEST_READ_STORAGE_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        mItemImage = findViewById(R.id.item_image);
        mName = findViewById(R.id.name_input);
        mAmount = findViewById(R.id.amount_input);
        mPrice = findViewById(R.id.price_input);
        mItemDescription = findViewById(R.id.item_description_input);
        mAddImageButton = findViewById(R.id.add_image_button);
        mSaveButton = findViewById(R.id.save_button);
        selectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.no_image_available);

        dbHelper = new DbHelper(this);
        mAddImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(AddItem.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddItem.this, new String[]{READ_EXTERNAL_STORAGE},
                            REQUEST_READ_STORAGE_PERMISSION);
                } else {
                    loadImage();
                }
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int desiredHeight = 700;
                int originalHeight = selectedImage.getHeight();
                int originalWidth = selectedImage.getWidth();
                float scaleRatio = (float) desiredHeight / (float) originalHeight;
                int desiredWidth = Math.round(originalWidth * scaleRatio);
                selectedImage = Bitmap.createScaledBitmap(selectedImage, desiredWidth, desiredHeight, true);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                byte[] imageByteArray = stream.toByteArray();
                String name = mName.getText().toString();
                String amount = mAmount.getText().toString();
                String price = mPrice.getText().toString();
                if (name.equals("") || amount.equals("") || price.equals("")) {
                    String mToast = "";
                    if (name.equals("")) { mToast = mToast + getString(R.string.name_required);}
                    if (amount.equals("")) {mToast = mToast + getString(R.string.amount_required);}
                    if (price.equals("")) {mToast = mToast + getString(R.string.price_required);}
                    Toast.makeText(getApplicationContext(), mToast, Toast.LENGTH_SHORT).show();
                    return;
                }
                String itemDescription = mItemDescription.getText().toString();
                if (itemDescription.equals("")) {itemDescription = getString(R.string.no_description);}
                dbHelper.addNewItem(new CatalogueItem(0, name, amount, price, itemDescription, imageByteArray));
                finish();
            }
        });
    }

    private void loadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                mItemImage.setImageBitmap(selectedImage);
            }
            catch (Exception e) { e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_STORAGE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImage();
            }
        }
    }
}