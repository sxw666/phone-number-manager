package www.action;

import annotation.RefreshCsrfToken;
import annotation.SystemUserAuth;
import annotation.VerifyCSRFToken;
import exception.BusinessException;
import exception.JsonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.CsrfTokenUtil;
import www.entity.Subdistrict;
import www.service.SubdistrictService;
import www.validator.SubdistrictInputValidator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 街道控制器
 *
 * @author 廿二月的天
 */
@Controller
@SystemUserAuth
@RequestMapping("/subdistrict")
public class SubdistrictAction {
    @Resource
    private SubdistrictService subdistrictService;
    private final HttpServletRequest request;

    @Autowired
    public SubdistrictAction(HttpServletRequest request) {
        this.request = request;
    }

    @InitBinder
    public void initBinder(DataBinder binder) {
        String validFunction = (String) request.getSession().getAttribute("validFunction");
        if ("subdistrictCreateOrEditHandle".equals(validFunction)) {
            binder.replaceValidators(new SubdistrictInputValidator(subdistrictService, request));
        }
    }

    /**
     * 街道列表
     *
     * @param model 前台模型
     * @param page  分页对象
     * @return 视图页面
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RefreshCsrfToken
    public String subdistrictList(Model model, Integer page) {
        try {
            Map<String, Object> subdistricts = subdistrictService.findObjects(page, null);
            model.addAttribute("subdistricts", subdistricts.get("data"));
            model.addAttribute("pageInfo", subdistricts.get("pageInfo"));
            return "subdistrict/list";
        } catch (Exception e) {
            throw new BusinessException("系统异常！找不到数据，请稍后再试！", e);
        }
    }

    /**
     * 添加街道
     *
     * @return 视图页面
     */
    @RefreshCsrfToken
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createSubdistrict() {
        return "subdistrict/edit";
    }

    /**
     * 编辑街道
     *
     * @param model 前台模型
     * @param id    编辑的对应编号
     * @return 视图页面
     */
    @RefreshCsrfToken
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editSubdistrict(Model model, Long id) {
        try {
            Subdistrict subdistrict = subdistrictService.findObject(id);
            model.addAttribute("subdistrict", subdistrict);
            return "subdistrict/edit";
        } catch (Exception e) {
            throw new BusinessException("系统异常！找不到数据，请稍后再试！", e);
        }
    }

    /**
     * 添加、修改街道处理
     *
     * @param model         前台模型
     * @param subdistrict   街道对象
     * @param bindingResult 错误信息对象
     * @return 视图页面
     */
    @RequestMapping(value = "/handle", method = {RequestMethod.POST, RequestMethod.PUT})
    @VerifyCSRFToken
    public String subdistrictCreateOrEditHandle(Model model, @Validated Subdistrict subdistrict, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 输出错误信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            model.addAttribute("messageErrors", allErrors);
            return "subdistrict/edit";
        }
        if (subdistrict.getSubdistrictId() == null) {
            try {
                subdistrictService.createObject(subdistrict);
            } catch (Exception e) {
                throw new BusinessException("添加街道失败！", e);
            }
        } else {
            try {
                subdistrictService.updateObject(subdistrict);
            } catch (Exception e) {
                throw new BusinessException("修改街道失败！", e);
            }
        }
        return "redirect:/subdistrict/list";
    }

    /**
     * 使用AJAX技术通过街道编号删除
     *
     * @param session session对象
     * @param id 对应编号
     * @return Ajax信息
     */
    @RequestMapping(value = "/ajax_delete", method = RequestMethod.DELETE)
    @VerifyCSRFToken
    @ResponseBody
    public Map<String, Object> deleteSubdistrictForAjax(HttpSession session, Long id) {
        Map<String, Object> map = new HashMap<>(4);
        try {
            subdistrictService.deleteObjectById(id);
            map.put("state", 1);
            map.put("message", "删除街道成功！");
            map.put("_token", CsrfTokenUtil.getTokenForSession(session, null));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("删除街道失败！", e);
        }
    }

    /**
     * 通过Ajax技术获取街道信息
     *
     * @param session session对象
     * @return 街道信息
     */
    @RequestMapping(value = "/ajax_load", method = RequestMethod.GET)
    @VerifyCSRFToken
    @ResponseBody
    public Map<String, Object> getSubdistrictForAjax(HttpSession session) {
        Map<String, Object> map = new HashMap<>(4);
        try {
            List<Subdistrict> subdistricts = subdistrictService.findObjects();
            map.put("state", 1);
            map.put("subdistricts", subdistricts);
            map.put("_token", CsrfTokenUtil.getTokenForSession(session, null));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new JsonException("获取街道失败！", e);
        }
    }
}
