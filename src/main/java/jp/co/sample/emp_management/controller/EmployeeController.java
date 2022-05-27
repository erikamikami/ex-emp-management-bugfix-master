package jp.co.sample.emp_management.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.form.SerchEmployeeForm;
import jp.co.sample.emp_management.form.UpdateEmployeeForm;
import jp.co.sample.emp_management.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 未入力のStringをnullに設定する
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
	}

	@ModelAttribute
	public SerchEmployeeForm serchForm() {
		return new SerchEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧画面を出力します.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		// 現在のページがnullの場合（初期表示の場合）、現在のページを1とする。
		String nowpage = (String) model.getAttribute("nowpage");
		if (nowpage == null) {
			nowpage = "1";
		}
		model.addAttribute("nowpage", nowpage);
		List<Employee> employeeList = employeeService.showList(1);
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id    リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 * 
	 * @param form 従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@RequestMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = new Employee();
		employee.setId(form.getIntId());
		// 扶養人数はnullでも許容するので、nullだった場合は0を登録する。
		try {
			employee.setDependentsCount(form.getIntDependentsCount());
		} catch (NumberFormatException e) {
			employee.setDependentsCount(0);
		}
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員を従業員名であいまい検索する
	/////////////////////////////////////////////////////
	/**
	 * 従業員を従業員名であいまい検索します
	 * 
	 * @param partOfName
	 * @param model
	 * @return
	 */
	@RequestMapping("/serchByName")
	public String serchByName(SerchEmployeeForm serchEmployeeForm, RedirectAttributes redirectAttributes, Model model) {
		String partOfName = serchEmployeeForm.getPartOfName();
		// 検索フォームに入力された文字列が空であった場合は、全件検索結果を表示させる
		try {
			partOfName.isEmpty();
		} catch (NullPointerException e) {
			return "forward:/employee/paging";
		}

		List<Employee> serchByNameResult = employeeService.serchByName(partOfName);

		// 指定した文字列が存在しなかった場合、「１件もありませんでした」というメッセージと共に全件検索結果を表示させる
		if (serchByNameResult.size() == 0) {
			String notResultMessage = "「" + partOfName + "」" + "の検索結果は１件もありませんでした";
			redirectAttributes.addFlashAttribute("notResultMessage", notResultMessage);
			return "redirect:/employee/paging";
		}

		model.addAttribute("serchByNameResult", serchByNameResult);
		return "employee/list";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧画面のページング処理
	/////////////////////////////////////////////////////
	/**
	 * ページング処理を行う.
	 * 
	 * @param nowpage   今いるページ番号
	 * @param nextbox   次に行きたいページ番号（押下されなければnull）
	 * @param beforeFlg 「前へ」が押下されたかどうかの判定フラグ（押下されなければnull）
	 * @param nextFlg   「次へ」が押下されたかどうかの判定フラグ（押下されなければnull）
	 * @param model
	 * @return String
	 */
	@RequestMapping("/paging")
	public String paging(String nowpage, String nextbox, String beforeFlg, String nextFlg, Model model) {
		
		// いくつのページボックスが必要なのかを一番最初に把握する
		List<Integer> pagingBoxesList = new LinkedList<>();
		List<Integer> temporary = (List<Integer>) model.getAttribute("pagingBoxesList");
		if (temporary == null) {
			List<Integer> pagingBoxesSizeList = employeeService.countPagingBox();
			Integer pagingBoxesSize = pagingBoxesSizeList.get(0);
			Integer pagingBoxes = pagingBoxesSize / 10;
			if ((pagingBoxesSize / 10) != 0) {
				pagingBoxes++;
			}
			for (int i = 1; i <= pagingBoxes; i++) {
				pagingBoxesList.add(i);
			}
		}
		// 最大のページボックスの数字を得る
		Integer maxPagingBox = pagingBoxesList.get(pagingBoxesList.size() - 1);
		model.addAttribute("maxPagingBox", maxPagingBox);
		model.addAttribute("pagingBoxesList", pagingBoxesList);

		// もしnowpageがnullだったら（はじめて従業員一覧ページを開いた時だったら）、1をセットする
		if (nowpage == null) {
			nowpage = "1";
			model.addAttribute("nowpage", nowpage);
		}

		// ページ番号（nextbox）が押下されたら、そのページ番号でSQLのオフセットを実行する。nowページも更新する。
		if (!(nextbox == null)) {
			Integer nextboxInteger = Integer.parseInt(nextbox);
			List<Employee> employeeList = employeeService.showList(nextboxInteger);
			model.addAttribute("employeeList", employeeList);
			nowpage = nextbox;
			model.addAttribute("nowpage", nowpage);
			return "employee/list";
		}

		// 「前へ」が押下されたら、今のページ番号-1でSQLのオフセットを実行する。nowページも更新する。
		if (!(beforeFlg == null)) {
			Integer nowpageInteger = Integer.parseInt(nowpage);
			nowpageInteger--;
			List<Employee> employeeList = employeeService.showList(nowpageInteger);
			model.addAttribute("employeeList", employeeList);
			model.addAttribute("nowpage", nowpageInteger);
			return "employee/list";
		}

		// 「次へ」が押下されたら、今のページ番号+1でSQLのオフセットを実行する。nowページも更新する。
		if (!(nextFlg == null)) {
			Integer nowpageInteger = Integer.parseInt(nowpage);
			nowpageInteger++;
			List<Employee> employeeList = employeeService.showList(nowpageInteger);
			model.addAttribute("employeeList", employeeList);
			model.addAttribute("nowpage", nowpageInteger);
			return "employee/list";
		}

		return showList(model);
	}
}
