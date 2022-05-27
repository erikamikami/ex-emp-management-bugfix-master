package jp.co.sample.emp_management.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 管理者情報登録時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class InsertAdministratorForm {
	/** 名前 */
	@NotBlank(message = "氏名は必須です")
	private String name;

	/** メールアドレス */
	@NotBlank(message = "メールアドレスは必須です")
	@Email(message = "メールアドレスの形式で入力してください")
	private String mailAddress;

	/** パスワード */
	@NotBlank(message = "パスワードは必須です")
	@Size(min = 8, max = 64, message = "パスワードは8文字以上64文字以下で設定してください")
	@Pattern(regexp = "^((?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])|(?=.*[a-z])(?=.*[A-Z])(?=.*[-!\"#$%&\'()=~|^\\\\])|(?=.*[A-Z])(?=.*[0-9])(?=.*[-!\"#$%&'()=~|^\\\\])|(?=.*[a-z])(?=.*[0-9])(?=.*[-!\"#$%&\'()=~|^\\\\]))([a-zA-Z0-9!\"#$%&\'()=~|^\\\\]){0,}$", message = "パスワードは ①大文字英字 ②小文字英字 ③半角数字 ④記号（-!\"#$%&'()=~|^\\）のうち3種類以上を組み合わせて設定してください。")
	private String password;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * @param mailAddress the mailAddress to set
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ "]";
	}
	
}
