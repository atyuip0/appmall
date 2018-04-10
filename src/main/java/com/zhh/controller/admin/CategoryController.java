package com.zhh.controller.admin;

import com.zhh.configuration.AdminUserSessionMap;
import com.zhh.dto.BaseResp;
import com.zhh.repository.CategoryRepository;
import com.zhh.repository.entity.AdminUser;
import com.zhh.repository.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController("AdminCategoryController")
public class CategoryController extends BaseAdminCtl {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/category/list")
    public Object categoryList() {
        List<Category> categories = categoryRepository.findByParentId(-1L,new Sort(Sort.Direction.DESC, "paixu"));
        if(categories!=null && !categories.isEmpty()){
            for (Category category : categories){
                setCategoryChild(category);
            }
        }
        Category category = new Category();
        category.setId(-1L);
        category.setChildren(categories);
        return BaseResp.SUCCESSRESP.setData(category);
    }

    @PostMapping("/category/create")
    public Object create(@RequestBody Category category, HttpServletRequest request) {
        AdminUser adminUser = AdminUserSessionMap.getAdminUserSession(request).getAdminUser();
        category.setAddBy(adminUser.getUserName());
        category.setAddTime(LocalDateTime.now());
        Category category_ = categoryRepository.save(category);
        return BaseResp.SUCCESSRESP;
    }

    @PostMapping("/category/edit")
    public Object edit(@RequestBody Category category, HttpServletRequest request) {
        AdminUser adminUser = AdminUserSessionMap.getAdminUserSession(request).getAdminUser();
        category.setUpBy(adminUser.getUserName());
        category.setUpTime(LocalDateTime.now());
        Category category_ = categoryRepository.save(category);
        return BaseResp.SUCCESSRESP;
    }

    @GetMapping("/category/modifyStatus")
    public Object modifyStatus(@RequestParam Long id,@RequestParam Integer status,
                               HttpServletRequest request) {
        AdminUser adminUser = AdminUserSessionMap.getAdminUserSession(request).getAdminUser();
        int i = categoryRepository.updateStatusById(status.equals(0)?status:1,id,adminUser.getUserName(), LocalDateTime.now());
        if(i>0){
            return BaseResp.SUCCESSRESP;
        }
        return BaseResp.ERRORRESP;
    }

    private void setCategoryChild(Category category){
        List<Category> categoryChildList = categoryRepository.findByParentId(category.getId(),new Sort(Sort.Direction.DESC, "paixu"));
        category.setChildren(categoryChildList);
        if(categoryChildList!=null && !categoryChildList.isEmpty()){
            for (Category categoryChild : categoryChildList){
                setCategoryChild(categoryChild);
            }
        }
    }
}
