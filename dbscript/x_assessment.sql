CREATE TABLE CRPROFL.X_ASSESSMENT (
    CUSTOMER_ID BIGINT,
    CREDIT_PROFILE_ID BIGINT,
    ASSESSMENT_MESSAGE_CD VARCHAR,
    CREDIT_ASSESSMENT_ID VARCHAR,
    CREDIT_ASSESSMENT_TS TIMESTAMP,
    CREDIT_ASSESSMENT_TYP_CD VARCHAR,
    CREDIT_ASSESSMENT_SUBTYP_CD VARCHAR,
    CREDIT_ASSESSMENT_RESULT_CD VARCHAR,
    CREDIT_ASSESSMENT_RESULT_REASON_CD VARCHAR,
    NEED_TO_SYNC BOOLEAN  
);

CREATE INDEX I_X_ASSESSMENT_1 ON CRPROFL.X_ASSESSMENT (CUSTOMER_ID);