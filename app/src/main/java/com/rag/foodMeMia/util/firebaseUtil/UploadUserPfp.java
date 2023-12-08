package com.rag.foodMeMia.util.firebaseUtil;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rag.foodMeMia.domain.FoodDomain;
import com.rag.foodMeMia.domain.UserPfp;
import com.rag.foodMeMia.util.Constants;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UploadUserPfp {
    public static final Single<Map<String, Object>> observeUserPfpImageUploading(UserPfp userPfp) {
        return Single.<Map<String, Object>>create(emitter -> {
                    Map<String, Object> uploadPfpImageStatus = new HashMap<>();

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection(Constants.USER_PFP_COLLECTION_NAME)
                            .document().set(userPfp)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    uploadPfpImageStatus.put(Constants.UPLOADED_STATUS_KEY,"success");
                                    emitter.onSuccess(uploadPfpImageStatus);
                                    Log.d(TAG, "Image is uploaded successfully");
                                }

                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    emitter.onError(e);
                                }
                            } )
                    ;


                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
