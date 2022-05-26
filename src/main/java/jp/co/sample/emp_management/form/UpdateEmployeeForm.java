package jp.co.sample.emp_management.form;

import javax.validation.constraints.Pattern;

/**
 * 従業員情報更新時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class UpdateEmployeeForm {
	/** id */
	private String id;
	/** 扶養人数 */
	// 入力値チェック 要件
	// ・数字であれば受け付ける（半角・全角問わない）
	// ・空欄でも許容
	@Pattern(regexp = "^[0-9０-９]+$", message = "扶養人数は数値で入力してください")
	private String dependentsCount;

	/**
	 * IDを数値として返します.
	 * 
	 * @return 数値のID
	 */
	public Integer getIntId() {
		return Integer.parseInt(id);
	}

	/**
	 * 扶養人数を数値として返します.
	 * 
	 * @return 数値の扶養人数
	 */
	public Integer getIntDependentsCount() {
		if (dependentsCount == null) {
			dependentsCount = "0";
		}
		return Integer.parseInt(dependentsCount);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the dependentsCount
	 */
	public String getDependentsCount() {
		return dependentsCount;
	}

	/**
	 * @param dependentsCount the dependentsCount to set
	 */
	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

	@Override
	public String toString() {
		return "UpdateEmployeeForm [id=" + id + ", dependentsCount=" + dependentsCount + "]";
	}

}
