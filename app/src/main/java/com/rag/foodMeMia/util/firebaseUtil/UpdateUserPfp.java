package com.rag.foodMeMia.util.firebaseUtil;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;
import com.rag.foodMeMia.domain.UserPfp;
import com.rag.foodMeMia.util.Constants;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpdateUserPfp {
    public static final Single<Map<String, Object>> checkUserHasPfp(String email) {
        return Single.<Map<String, Object>>create(emitter -> {
                    Map<String, Object> userPfpDataMap = new HashMap<>();
                    System.out.println("came inside the check user pfp method ");
                    final boolean[] isPfpFound = {false};

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection(Constants.USER_PFP_COLLECTION_NAME)
                            .whereEqualTo("email", email).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            System.out.println("iterating over here ");
                                            if (document.get("email").equals(email)) {
                                                isPfpFound[0] = true;
                                                userPfpDataMap.put(Constants.FOUND_STATUS, "true");
                                                userPfpDataMap.put("userPfp", document);
                                                userPfpDataMap.put("oldPfpUrl", document.get("pfpUrl"));

                                                break;
                                            }

                                        }
                                        if (!isPfpFound[0]) {
                                            userPfpDataMap.put(Constants.FOUND_STATUS, "false");
                                        }
                                        emitter.onSuccess(userPfpDataMap);
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                        emitter.onError(task.getException());
                                    }
                                }
                            });

                    ;


                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static final Single<Map<String, Object>> observeUserPfpImageUpdating(String newPfpUrl, QueryDocumentSnapshot userPfpDocument) {
        return Single.<Map<String, Object>>create(emitter -> {
                    Map<String, Object> updatePfpImageStatus = new HashMap<>();

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String docId = userPfpDocument.getId();

                    db.collection(Constants.USER_PFP_COLLECTION_NAME).document(docId)
                            .update("pfpUrl", newPfpUrl)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    updatePfpImageStatus.put(Constants.UPDATE_STATUS, "success");
                                    emitter.onSuccess(updatePfpImageStatus);
                                    Log.d(TAG, "Image is updated successfully");
                                }

                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    emitter.onError(e);
                                }
                            })
                    ;


                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
