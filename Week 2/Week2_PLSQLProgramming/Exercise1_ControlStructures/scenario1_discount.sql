-- Scenario 1: Apply 1% discount to loan interest rates for customers above 60 years old
-- Question: Write a PL/SQL block that loops through all customers, checks their age, 
-- and if they are above 60, apply a 1% discount to their current loan interest rates.

DECLARE
  v_discount_rate CONSTANT NUMBER := 0.01; -- 1% discount
  v_age_threshold CONSTANT NUMBER := 60;
  v_customers_processed NUMBER := 0;
  v_loans_updated NUMBER := 0;
  v_current_age NUMBER;
  v_old_rate NUMBER;
  v_new_rate NUMBER;
BEGIN
  DBMS_OUTPUT.PUT_LINE('Starting loan interest rate discount process for senior customers...');
  DBMS_OUTPUT.PUT_LINE('Age threshold: ' || v_age_threshold || ' years');
  DBMS_OUTPUT.PUT_LINE('Discount rate: ' || (v_discount_rate * 100) || '%');
  DBMS_OUTPUT.PUT_LINE('----------------------------------------');
  
  -- Loop through all customers and check their age
  FOR customer_record IN (
    SELECT 
      c.customer_id, 
      c.name,
      FLOOR(MONTHS_BETWEEN(SYSDATE, c.date_of_birth) / 12) AS age
    FROM customers c
    WHERE c.date_of_birth IS NOT NULL
    ORDER BY c.customer_id
  ) 
  LOOP
    v_customers_processed := v_customers_processed + 1;
    v_current_age := customer_record.age;
    
    -- Check if customer is above 60 years old
    IF v_current_age > v_age_threshold THEN
      DBMS_OUTPUT.PUT_LINE('Processing Customer ID: ' || customer_record.customer_id || 
                           ' (' || customer_record.name || ') - Age: ' || v_current_age);
      
      -- Apply discount to all loans for this customer
      FOR loan_record IN (
        SELECT loan_id, interest_rate
        FROM loans
        WHERE customer_id = customer_record.customer_id
        AND loan_status = 'ACTIVE'
      )
      LOOP
        v_old_rate := loan_record.interest_rate;
        v_new_rate := v_old_rate - (v_old_rate * v_discount_rate);
        
        -- Update the loan interest rate
        UPDATE loans
        SET interest_rate = v_new_rate,
            last_modified = SYSDATE
        WHERE loan_id = loan_record.loan_id;
        
        v_loans_updated := v_loans_updated + 1;
        
        DBMS_OUTPUT.PUT_LINE('  Loan ID: ' || loan_record.loan_id || 
                           ' - Rate changed from ' || ROUND(v_old_rate, 4) || 
                           '% to ' || ROUND(v_new_rate, 4) || '%');
      END LOOP;
      
    ELSE
      DBMS_OUTPUT.PUT_LINE('Customer ID: ' || customer_record.customer_id || 
                           ' (' || customer_record.name || ') - Age: ' || v_current_age || 
                           ' (No discount - under ' || v_age_threshold || ')');
    END IF;
  END LOOP;
  
  -- Commit the changes
  COMMIT;
  
  DBMS_OUTPUT.PUT_LINE('----------------------------------------');
  DBMS_OUTPUT.PUT_LINE('Discount process completed successfully!');
  DBMS_OUTPUT.PUT_LINE('Total customers processed: ' || v_customers_processed);
  DBMS_OUTPUT.PUT_LINE('Total loans updated: ' || v_loans_updated);
  
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    DBMS_OUTPUT.PUT_LINE('No customers found in the database');
    ROLLBACK;
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error occurred during discount processing: ' || SQLERRM);
    ROLLBACK;
END;
/
