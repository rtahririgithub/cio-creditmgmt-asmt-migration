package com.telus.api.credit.sync.firestore.data;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.telus.api.credit.sync.firestore.model.CreditAssessment;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class CreditAssessmentRepository {

    public static final String COLLECTION_NAME_PROPERTY_KEY = "${creditmgmt.firestore.collectionName}";
    public static final String CUSTOMER_ID_FIELD = "customerId";

    private final Firestore firestore;
    private final String collectionName;

    public CreditAssessmentRepository(Firestore firestore, @Value(COLLECTION_NAME_PROPERTY_KEY) String collectionName) {
        this.firestore = firestore;
        this.collectionName = collectionName;
    }

    public CreditAssessment findByCustomerId(String customerId) throws ExecutionException, InterruptedException {
        CreditAssessment creditAssessment = null;
        CollectionReference collection = firestore.collection(collectionName);
        QuerySnapshot result = collection.whereEqualTo(CUSTOMER_ID_FIELD, customerId).limit(1).get().get();
        for (QueryDocumentSnapshot document : result.getDocuments()) {
            creditAssessment = document.toObject(CreditAssessment.class);
        }
        return creditAssessment;
    }

    public String findDocumentIdByCustomerId(String customerId) throws ExecutionException, InterruptedException {
        String documentId = null;
        CollectionReference collection = firestore.collection(collectionName);
        QuerySnapshot result = collection.whereEqualTo(CUSTOMER_ID_FIELD, customerId).select(FieldPath.documentId()).limit(1).get().get();
        for (QueryDocumentSnapshot document : result.getDocuments()) {
             documentId = document.getId();
        }
        return documentId;
    }

    public Timestamp save(CreditAssessment creditAssessment) throws ExecutionException, InterruptedException {
        String documentId = findDocumentIdByCustomerId(creditAssessment.getCustomerId());
        CreditAssessment newDoc = creditAssessment;
        Timestamp updatedAt = null;
        if (Objects.isNull(documentId)) {
            updatedAt = firestore.collection(collectionName).add(newDoc).get().get().get().getUpdateTime();
        } else {
            newDoc.setId(documentId);
            updatedAt = firestore.collection(collectionName).document(documentId).set(newDoc).get().getUpdateTime();
        }
        return updatedAt;
    }
}
