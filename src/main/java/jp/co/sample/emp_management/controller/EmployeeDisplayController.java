package jp.co.sample.emp_management.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sample.emp_management.domain.Employee;

public class EmployeeDisplayController {

	static SimpleDateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");

	/**
	 * 入社日の表記を「●●●●年●●月●●日」というフォーマットに変えるメソッド
	 * 
	 * @param employees
	 * @return List<Employee>
	 */
	public static List<Employee> changeHireDateFmt(List<Employee> employees) {
		List<Employee> employeeList = new ArrayList<>();
		for (Employee insideEmployee : employees) {
			String hireDateString = fmt.format(insideEmployee.getHireDate());
			insideEmployee.setHireDateString(hireDateString);
			employeeList.add(insideEmployee);
		}

		return employeeList;
	}

	/**
	 * 入社日の表記を「●●●●年●●月●●日」というフォーマットに変えるメソッド
	 * 
	 * @param employees
	 * @return Employee
	 */
	public static Employee changeHireDateFmt(Employee employees) {
		String hireDateString = fmt.format(employees.getHireDate());
		employees.setHireDateString(hireDateString);

		return employees;
	}

}
