DECLARE
  v_reminder_count NUMBER := 0;
  v_days_ahead CONSTANT NUMBER := 30;
BEGIN
  FOR loan_info IN (
    SELECT loan_id, customer_id, due_date
    FROM loans
    WHERE due_date <= SYSDATE + v_days_ahead
    ORDER BY due_date ASC
  ) 
  LOOP
    DBMS_OUTPUT.PUT_LINE('Payment Alert: Customer ' || loan_info.customer_id ||
                         ' - Loan Account ' || loan_info.loan_id ||
                         ' payment due: ' || TO_CHAR(loan_info.due_date, 'DD-MON-YYYY'));
    
    v_reminder_count := v_reminder_count + 1;
  END LOOP;
  
  IF v_reminder_count = 0 THEN
    DBMS_OUTPUT.PUT_LINE('No upcoming loan payments within next ' || v_days_ahead || ' days');
  ELSE
    DBMS_OUTPUT.PUT_LINE('Total reminders sent: ' || v_reminder_count);
  END IF;
  
END;
/