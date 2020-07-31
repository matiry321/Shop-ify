package com.example.theonlinegrocerystore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddReviewDialog extends DialogFragment {

    public interface  AddReview
    {
        void onAddReviewResult(Review review);
    }
    private AddReview addReview;//instance of this interface
    private TextView txtWarning,txtItemName;
    private EditText edtTxtUserName,edtTxtReview;
    private Button btnAddReview;
    private static final String GROCERY_ITEM_KEY = "incoming_item";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_review,null);
        initViews(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);
        Bundle bundle=getArguments();//To pass the data here the grocer item in the alert dialog box i.e here we have received the grocery item
        if(null!=bundle)
        {
            final Groceryitem item=bundle.getParcelable(GROCERY_ITEM_KEY);//here we r passing the grocery item
            if(null!=item)
            {
                txtItemName.setText(item.getName());
                btnAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userName=edtTxtUserName.getText().toString();
                        String review = edtTxtReview.getText().toString();
                        String date=getCurrentDate();
                        //Now the data is received we will add it to shared preferences but before check the fields
                        if(userName.equals("") || review.equals(""))
                        {
                            txtWarning.setText("Fill all the blanks");
                            txtWarning.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            txtWarning.setVisibility(View.GONE);
                            //Now we can add the review to the shared preferences so to send something from fragmnet to sp we create call backs
                            try {
                                addReview=(AddReview)getActivity();
                                addReview.onAddReviewResult(new Review(item.getId(),userName,review,date));
                                dismiss();
                            }catch (ClassCastException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
        return builder.create();
    }

    private String getCurrentDate()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YYYY");
        return sdf.format(calendar.getTime());
    }
    private void initViews(View view)
    {
        txtWarning= view.findViewById(R.id.txtWarning);
        txtItemName=view.findViewById(R.id.txtItemName);
        edtTxtUserName=view.findViewById(R.id.edtTxtUserName);
        edtTxtReview=view.findViewById(R.id.edtTxtReview);
        btnAddReview=view.findViewById(R.id.btnAddReview);
    }
}
