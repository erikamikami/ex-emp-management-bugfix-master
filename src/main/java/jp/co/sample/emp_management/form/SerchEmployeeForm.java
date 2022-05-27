package jp.co.sample.emp_management.form;

/**
 * 従業員を検索するときのForm
 * 
 * @author erika
 *
 */

public class SerchEmployeeForm {
	/** 名前をあいまい検索 **/
	private String partOfName;

	@Override
	public String toString() {
		return "SerchEmployeeForm [partOfName=" + partOfName + "]";
	}

	public String getPartOfName() {
		return partOfName;
	}

	public void setPartOfName(String partOfName) {
		this.partOfName = partOfName;
	}
}
