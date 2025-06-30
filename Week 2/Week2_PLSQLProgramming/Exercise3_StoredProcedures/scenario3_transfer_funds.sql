DROP SEQUENCE fund_transfer_seq;
CREATE SEQUENCE fund_transfer_seq 
START WITH 3
INCREMENT BY 1;

CREATE OR REPLACE PROCEDURE ExecuteMoneyTransfer (
    source_acc_id IN NUMBER,
    target_acc_id   IN NUMBER,
    amount_to_transfer IN NUMBER
) IS
    balance_insufficient EXCEPTION;
    current_balance NUMBER;
    sender_customer_id NUMBER;
    receiver_customer_id NUMBER;
BEGIN
   
    SELECT Balance, customerID INTO current_balance, sender_customer_id
    FROM Accounts
    WHERE AccountID = source_acc_id
    FOR UPDATE;
    
    SELECT customerID INTO receiver_customer_id
    FROM Accounts
    WHERE AccountID = target_acc_id
    FOR UPDATE;

    IF current_balance < amount_to_transfer THEN
        RAISE balance_insufficient;
    END IF;

    UPDATE Accounts
    SET Balance = Balance - amount_to_transfer, LastModified = SYSDATE
    WHERE AccountID = source_acc_id;

    UPDATE Accounts
    SET Balance = Balance + amount_to_transfer, LastModified = SYSDATE
    WHERE AccountID = target_acc_id;
    
    UPDATE Customers
    SET Balance = Balance - amount_to_transfer, LastModified = SYSDATE
    WHERE customerID = sender_customer_id;
    
    UPDATE Customers
    SET Balance = Balance + amount_to_transfer, LastModified = SYSDATE
    WHERE customerID = receiver_customer_id;

    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (fund_transfer_seq.NEXTVAL, source_acc_id, SYSDATE, amount_to_transfer, 'Withdrawal');

    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (fund_transfer_seq.NEXTVAL, target_acc_id, SYSDATE, amount_to_transfer, 'Credit');
    
    DBMS_OUTPUT.PUT_LINE('Successfully transferred Rs.'|| amount_to_transfer || ' from account '|| source_acc_id || ' to account ' || target_acc_id);
    DBMS_OUTPUT.PUT_LINE('Remaining balance in account '|| source_acc_id || ' is: '|| (current_balance - amount_to_transfer));

    COMMIT;

EXCEPTION
    WHEN balance_insufficient THEN
        DBMS_OUTPUT.PUT_LINE('Transaction failed: Insufficient funds available.');
        ROLLBACK;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Transaction failed: ' || SQLERRM);
        ROLLBACK;
END;
/

DECLARE
	from_account_num NUMBER;
	to_account_num NUMBER;
	transfer_sum NUMBER;
BEGIN
	from_account_num := &source_acc_id;
	to_account_num := &destination_acc_id;
	transfer_sum := &amount;
	
	ExecuteMoneyTransfer(from_account_num, to_account_num, transfer_sum);
END;
/
