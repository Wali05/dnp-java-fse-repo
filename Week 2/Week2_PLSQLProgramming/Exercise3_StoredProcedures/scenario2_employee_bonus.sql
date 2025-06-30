CREATE OR REPLACE PROCEDURE ApplySalaryIncrement (
    department_name IN VARCHAR2,
    increment_rate IN NUMBER
) IS
	current_pay NUMBER;
	revised_pay NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Processing salary adjustment for department: '|| department_name || ' with rate: '|| increment_rate || '%');
    
    SELECT salary INTO current_pay 
    FROM employees 
    WHERE department = department_name;
    
    revised_pay := current_pay + (current_pay * increment_rate / 100);
    
    DBMS_OUTPUT.PUT_LINE('Current Salary= '|| current_pay);
    DBMS_OUTPUT.PUT_LINE('Revised Salary= '|| revised_pay);
    
    UPDATE employees
    SET salary = revised_pay
    WHERE department = department_name;
    
    COMMIT;
END;
/

DECLARE
	dept_input VARCHAR2(50);
	rate_input NUMBER;
BEGIN
	dept_input := '&department';
	rate_input := &bonus;
	ApplySalaryIncrement(dept_input, rate_input);
END;
/