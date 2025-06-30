SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE CalculateInterestAllocation IS
    updated_amount NUMBER;
BEGIN
    FOR savings_rec IN (
        SELECT AccountID, Balance, customerID
        FROM Accounts
        WHERE AccountType = 'Savings'
    ) LOOP
        updated_amount := savings_rec.Balance * 1.01;
        
        DBMS_OUTPUT.PUT_LINE('Account ID: ' || savings_rec.AccountID);
        DBMS_OUTPUT.PUT_LINE('Previous Balance: ' || savings_rec.Balance);
        DBMS_OUTPUT.PUT_LINE('Updated Balance: ' || updated_amount);

        UPDATE Accounts
        SET balance = updated_amount, lastModified = SYSDATE
        WHERE accountID = savings_rec.accountID;
        
        UPDATE Customers
        SET balance = updated_amount, lastmodified = SYSDATE
        WHERE customerID = savings_rec.customerID;
    END LOOP;
    
    COMMIT;
END;
/

DECLARE
BEGIN
    CalculateInterestAllocation;
END;
/