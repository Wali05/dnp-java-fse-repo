DECLARE
  v_count NUMBER := 0;
BEGIN
  FOR customer_record IN (
    SELECT customer_id 
    FROM customers 
    WHERE balance > 10000
  ) 
  LOOP
    UPDATE customers
    SET is_vip = 'Y'
    WHERE customer_id = customer_record.customer_id;
    
    v_count := v_count + 1;
    
    DBMS_OUTPUT.PUT_LINE('Customer ' || customer_record.customer_id || 
                         ' has been upgraded to VIP status');
  END LOOP;

  COMMIT;
  
  DBMS_OUTPUT.PUT_LINE('Total customers upgraded: ' || v_count);
  
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
END;
/