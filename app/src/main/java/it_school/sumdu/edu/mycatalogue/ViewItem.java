package it_school.sumdu.edu.mycatalogue;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewItem extends AppCompatActivity {
    private ImageView mItemImage;
    private TextView mItemName;
    private TextView mItemAmount;
    private TextView mItemPrice;
    private TextView mItemDescription;
    private Button mEditButton;
    private Button mDeleteButton;
    private DbHelper dbHelper;
    private CatalogueItem currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        mItemImage = findViewById(R.id.item_image);
        mItemName = findViewById(R.id.item_name);
        mItemAmount = findViewById(R.id.item_amount);
        mItemPrice = findViewById(R.id.item_price);
        mItemDescription = findViewById(R.id.item_description);
        mEditButton = findViewById(R.id.edit_button);
        mDeleteButton = findViewById(R.id.delete_button);
        dbHelper = new DbHelper(this);
        int itemId = getIntent().getIntExtra("id", -1);
        if (itemId != -1) {
            currentItem = dbHelper.getItemById(itemId);
            byte[] imageByteArray = currentItem.getImage();
            Bitmap bmp = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            mItemImage.setImageBitmap(bmp);
            mItemName.setText(currentItem.getName());
            mItemAmount.setText(currentItem.getAmount());
            mItemPrice.setText(currentItem.getPrice());
            mItemDescription.setText(currentItem.getItemDescription());
        }

        mEditButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewItem.this, EditItem.class);
                intent.putExtra("id", currentItem.getNumber());
                startActivity(intent);
                finish();
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ViewItem.this)
                        .setMessage(getString(R.string.delete_yes_no))
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dbHelper.deleteItem(currentItem);
                                finish();
                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)
                        .show();
            }
        });
    }
}