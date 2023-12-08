package com.rag.foodMeMia.activity;

import static com.rag.foodMeMia.util.firebaseUtil.UniqueNameGenerationFirebase.getUniqueImageName;
import static com.rag.foodMeMia.util.firebaseUtil.UniqueNameGenerationFirebase.observeUniqueName;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.rag.foodMeMia.R;
import com.rag.foodMeMia.databinding.ActivityUploadPfpBinding;
import com.rag.foodMeMia.domain.FoodDomain;
import com.rag.foodMeMia.domain.UserPfp;
import com.rag.foodMeMia.helper.TinyDB;
import com.rag.foodMeMia.util.Constants;
import com.rag.foodMeMia.util.ListenerUtil;
import com.rag.foodMeMia.util.StringUtils;
import com.rag.foodMeMia.util.firebaseUtil.Delete;
import com.rag.foodMeMia.util.firebaseUtil.UpdateUserPfp;
import com.rag.foodMeMia.util.firebaseUtil.UploadFoodItem;
import com.rag.foodMeMia.util.firebaseUtil.UploadImageFirebase;
import com.rag.foodMeMia.util.firebaseUtil.UploadUserPfp;

import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class UploadPfpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ActivityUploadPfpBinding binding;

    String uniqueImageName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadPfpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(UploadPfpActivity.this, AuthActivity.class));
        }

       setPfpImage();
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: " + uri);
                binding.pfpChosenImage.setImageURI(uri);
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });

        binding.pfpchooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View view) {
                // Launch the photo picker and let the user choose only images.
                pickMedia.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
                getUniqueImageName(Constants.PFP_IMAGE_FOLDER_PATH).subscribe(uniqueName -> {
                    uniqueImageName = uniqueName;
                    System.out.println("uniqueName is " + uniqueName);
                    binding.updatePfpBtn.setEnabled(true);
                }, throwable -> {
                    throwable.printStackTrace();
                });

            }
        });

        binding.updatePfpBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View view) {


                if (!uniqueImageName.equals("")) {


                    UploadImageFirebase.observePfpImageUploading(binding.pfpChosenImage, uniqueImageName)
                            .subscribe(
                                    uploadedData -> {
                                        Map<String, Object> uploadedDataMap = (Map<String, Object>) uploadedData;

                                        String uploadedDataMapMessage = uploadedDataMap.get(Constants.UPLOADED_STATUS_KEY).toString();
                                        System.out.println("uploaded image status is " + uploadedDataMapMessage);
                                        if (uploadedDataMapMessage.equals("success")) {

                                            String userPfpUrl = uploadedData.get("url").toString();
                                            System.out.println("came here inside");

                                            String email = currentUser.getEmail();
                                            UpdateUserPfp.checkUserHasPfp(email).subscribe(
                                                    status -> {
                                                        System.out.println("pfp user status is checked ");
                                                        String foundStatus = status.get(Constants.FOUND_STATUS).toString();

                                                        if (foundStatus.equals("true")) {
                                                            QueryDocumentSnapshot userPfpSnapShot = (QueryDocumentSnapshot) status.get("userPfp");


                                                            UpdateUserPfp.observeUserPfpImageUpdating(userPfpUrl, userPfpSnapShot).subscribe(
                                                                    updateStatus -> {
                                                                        String updateStatusMessage = updateStatus.get(Constants.UPDATE_STATUS).toString();
                                                                        if (updateStatusMessage.equals("success")) {

                                                                            String olderImageUrl = status.get("oldPfpUrl").toString();
                                                                            String oldImageName = StringUtils.getImageNameFromUrl(olderImageUrl);

                                                                            Delete.deletePfpImage(oldImageName).observeOn(AndroidSchedulers.mainThread()).subscribe(

                                                                                    deletedStatus -> {
                                                                                        String deleteStatusMessage = deletedStatus.get(Constants.DELETE_STATUS).toString();

                                                                                        if (deleteStatusMessage.equals("success")) {
                                                                                            binding.pfpChosenImage.setImageDrawable(null);
                                                                                            startActivity(new Intent(UploadPfpActivity.this, UserHomeActivity.class));
                                                                                        }
                                                                                    },
                                                                                    throwable -> {
                                                                                        throwable.printStackTrace();
                                                                                    }

                                                                            );


                                                                        }
                                                                    }, Throwable::printStackTrace
                                                            );
                                                        } else {
                                                            UserPfp userPfp = new UserPfp(userPfpUrl.toString(), email);
                                                            UploadUserPfp.observeUserPfpImageUploading(userPfp).subscribe(
                                                                    uploadedDataStatus -> {
                                                                        System.out.println("success");
                                                                        if (uploadedDataStatus.get(Constants.UPLOADED_STATUS_KEY).equals("success")) {

                                                                            binding.pfpChosenImage.setImageDrawable(null);
                                                                            startActivity(new Intent(UploadPfpActivity.this, UserHomeActivity.class));
                                                                        }
                                                                    },
                                                                    throwable -> {
                                                                        System.out.println("error");
                                                                        throwable.printStackTrace();
                                                                    }
                                                            );
                                                        }

                                                    }
                                                    ,
                                                    throwable -> {
                                                        throwable.printStackTrace();
                                                    }
                                            );

                                        }
                                    },
                                    throwable -> {
                                        throwable.printStackTrace();
                                    }
                            );

                }


            }
        });
    }



    @SuppressLint("CheckResult")
    public void setPfpImage(){
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String email = currentUser.getEmail();

            UpdateUserPfp.checkUserHasPfp(email).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(

                            data->{

                                Map<String,Object> userPfpData =(Map<String, Object>) data;

                                String imageFoundState = userPfpData.get(Constants.FOUND_STATUS).toString();


                                if(imageFoundState.equals("true")){
                                    String imageUrl = userPfpData.get("oldPfpUrl").toString();

                                    Glide.with(this).load(Uri.parse(imageUrl))

                                            .into(binding.pfpChosenImage);

                                    binding.updatePfpBtn.setEnabled(false);


                                }
                            },
                            throwable -> {

                            }

                    );

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(UploadPfpActivity.this, AuthActivity.class));
        }

    }

}